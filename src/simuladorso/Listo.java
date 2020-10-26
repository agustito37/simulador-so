

package simuladorso;
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
    public void eliminarProceso(Proceso proceso) {
        procesos.remove(proceso);
    }
    
    @Override
    public Iterator<Proceso> obtenerProcesos() {
        return procesos.iterator();
    }
}
