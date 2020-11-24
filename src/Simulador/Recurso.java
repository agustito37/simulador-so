package Simulador;

import java.io.Serializable;
import java.util.Queue;
import java.util.LinkedList;

/**
 *
 * @author Mauro
 */
public class Recurso implements Serializable, Permisible {
    public String idRecurso;
    private Queue<Proceso> procesos;
    private Permisos permiso;
    
    public Recurso(String unRecurso, Proceso proceso, Permisos pPermiso) {
        procesos = new LinkedList();
        procesos.add(proceso);
        idRecurso = unRecurso;
        permiso = pPermiso;
    }
    
    public Recurso(String unRecurso, Permisos pPermiso) {
        procesos = new LinkedList();
        idRecurso = unRecurso;
        permiso = pPermiso;
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
    
    @Override
    public Permisos obtenerPermiso() {
        return permiso;
    }
    
    public void eliminarDeCola(Proceso proceso) {
        procesos.remove(proceso);
    }
    
    public Recurso nuevo() {
        return new Recurso(this.idRecurso, this.permiso);
    }
}
