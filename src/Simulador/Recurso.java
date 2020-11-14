package Simulador;

import java.util.Queue;
import java.util.LinkedList;

/**
 *
 * @author Mauro
 */
public class Recurso {
    private String idRecurso;
    private Queue<Proceso> procesos;
    
    public Recurso(String unRecurso, Proceso proceso) {
        procesos = new LinkedList();
        procesos.add(proceso);
        idRecurso = unRecurso;
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