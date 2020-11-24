package Simulador;

import Excepciones.InexistenteException;
import Excepciones.DenegadoException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauro
 */
public class AdministradorRecursos {
    private List<Recurso> recursos;

    public AdministradorRecursos(List<Recurso> pRecursos) {
        recursos = new ArrayList();
        
        for (int x = 0; x < pRecursos.size(); x++) {
            // genera nuevo recurso para reinicar estado
            recursos.add(pRecursos.get(x).nuevo());
        }
    }
    
    private Recurso obtenerRecurso(String id) {
        Recurso recurso = null;
        
        for (int x = 0; x < recursos.size(); x++) {
            Recurso actual = recursos.get(x);
            
            if (actual.idRecurso.equals(id)) {
                return actual;
            }
        }
        
        return recurso;
    }

    public boolean solicitar (String idRecurso, Proceso proceso, Usuario logueado) throws InexistenteException, DenegadoException {
        String id = idRecurso.replaceFirst("RS", "");
        
        Recurso recurso = obtenerRecurso(id);
        if(recurso != null){
            if (!Permisos.tieneAcceso(logueado, recurso)) {
                throw new DenegadoException("Error - permisos insuficientes");
            }

            // si ya tengo el recurso, no hago nada
            if (recurso.obtenerSiguienteProceso() == proceso) {
                return true;
            }

            // si no lo tengo, lo encolo en el recurso
            boolean estaDisponible = recurso.estaDisponible();
            recurso.encolarProceso(proceso);
            return estaDisponible;
        }
        else {
            throw new InexistenteException("Error - recurso inexistente");
        }
    }

    public Proceso devolver (String idRecurso, Proceso proceso, Usuario logueado) throws InexistenteException, DenegadoException{
        String id = idRecurso.replaceFirst("RD", "");
        
        Recurso recurso = obtenerRecurso(id);
        if(recurso != null){
            if (!Permisos.tieneAcceso(logueado, recurso)) {
                throw new DenegadoException("Error - permisos insuficientes");
            }
            
            if(recurso.obtenerSiguienteProceso() == proceso){
                recurso.desencolarProceso();
                return recurso.obtenerSiguienteProceso();
            }

            return null;
        }
        else {
            throw new InexistenteException("Error - recurso inexistente");
        }
    }
    
    public void devolverRecursosProceso(Proceso proceso, Transicionable sistema) {
        for (int x = 0; x < recursos.size(); x += 1) {
            Recurso recurso = recursos.get(x);
            Proceso primero = recurso.obtenerSiguienteProceso();
            
            if (primero != null) {
                recurso.eliminarDeCola(proceso);
                
                // si es el primero de la lista, despierto al siguiente en la lista de espera
                if (primero.equals(proceso)) {
                    Proceso siguienteProceso = recurso.desencolarProceso();

                    if (siguienteProceso != null) {
                        sistema.transicion(Transicion.despertar, siguienteProceso);
                    }
                }
            }
        }
    }
}
