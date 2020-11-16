package Simulador;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mauro
 */
public class AdministradorRecursos {
    private HashMap<String, Recurso> recursos;

    public AdministradorRecursos(List<Recurso> pRecursos){
        recursos = new HashMap();
        for (int x = 0; x < pRecursos.size(); x += 1) {
            Recurso recurso = pRecursos.get(x);
            recursos.put(recurso.idRecurso, recurso);
        }
    }

    public boolean solicitar (String idRecurso, Proceso proceso, Usuario logueado) throws InexistenteException, DenegadoException {
        String id = idRecurso.replaceFirst("RS", "");

        if(recursos.containsKey(id)){
            Recurso recurso = recursos.get(id);
            
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
        
        if(recursos.containsKey(id)){
            Recurso recurso = recursos.get(id);
            
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
}
