package Simulador;

import java.util.HashMap;

/**
 *
 * @author Mauro
 */
public class AdministradorRecursos {
    private HashMap<String, Recurso> recursos;

    public AdministradorRecursos(){
        recursos = new HashMap();
    }

    public boolean solicitar (String idRecurso, Proceso proceso) {
        String id = idRecurso.replaceFirst("S", "");

        if(recursos.containsKey(id)){
            Recurso recurso = recursos.get(id);

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
            Recurso recurso = new Recurso(id, proceso);
            recursos.put(id, recurso);
            return true;
        }
    }

    public Proceso devolver (String idRecurso, Proceso proceso){
        String id = idRecurso.replaceFirst("D", "");

        Recurso recurso = recursos.get(id);
        if(recurso.obtenerSiguienteProceso() == proceso){
            recurso.desencolarProceso();
            return recurso.obtenerSiguienteProceso();
        }

        return null;
    }
}
