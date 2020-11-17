

package Simulador;
import java.util.ArrayList;
import java.util.Iterator;

public class Listo implements Estado {
    private ArrayList<Proceso> procesos;
    
    public Listo() {
        procesos = new ArrayList();
    }
    
    @Override
    public void agregarProceso(Proceso proceso) {
        procesos.add(proceso);
    }
    
    @Override
    public void quitarProceso(Proceso proceso) {
        procesos.remove(proceso);
    }

    @Override
    public void vaciar() {
        procesos.clear();
    }
    
    @Override
    public boolean estaVacio() {
        return procesos.isEmpty();
    }
    
    @Override
    public Iterator<Proceso> obtenerProcesos() {
        // clonar para evitar modificación concurrente del iterador
        return (new ArrayList(procesos)).iterator();
    }
    
    public Iterator<Proceso> obtenerProcesosPriorizados(int ciclo, int schedulingFilaBaja) {
        // cola de prioridad de 2 niveles
        if (ciclo % schedulingFilaBaja == 0) {
            return obtenerIteradorDePrioridad(Prioridad.baja);
        } else {
            return obtenerIteradorDePrioridad(Prioridad.alta);
        }
    }
    
    public Iterator<Proceso> obtenerIteradorDePrioridad(Prioridad prioridad) {
        ArrayList<Proceso> priorizada = new ArrayList();
        
        for (int x = 0; x < procesos.size(); x += 1) {
            Proceso actual = procesos.get(x);
            
            if (actual.prioridad == prioridad) {
                priorizada.add(actual);
            }
        }
        
        // si no tiene elementos, retorno todos los procesos para que agarre el siguiente
        if (priorizada.isEmpty()) {
            return obtenerProcesos();
        }
        
        return priorizada.iterator();
    }
}
