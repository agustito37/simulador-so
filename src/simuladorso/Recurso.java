package simuladorso;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 *
 * @author Mauro
 */
public class Recurso {
    private String idRecurso;
    private Queue<Proceso> procesos;
    private String permiso;

    public String getPermiso() {
        return permiso;
    }

    public void setPermisos(String permiso) {
        this.permiso = permiso;
    }

    
    public Recurso(String unRecurso, Proceso proceso, String permiso) {
        procesos = new LinkedList();
        procesos.add(proceso);
        idRecurso = unRecurso;
        permiso = permiso;
    }
    
    public Recurso(String unRecurso) {
        procesos = new LinkedList();
        idRecurso = unRecurso;
    }

    public Proceso obtenerSiguienteProceso() {
        return procesos.peek();
    }

    public void encolarProceso(Proceso proceso) {
        this.procesos.add(proceso);
    }
    
    public Proceso desencolarProceso() {
        return this.procesos.poll();
    }

    public boolean estaDisponible() {
        return this.procesos.isEmpty();
    }

    public String obtenerId() {
        return idRecurso;
    }

    public void setearId(String id) {
        this.idRecurso = id;
    }
}
