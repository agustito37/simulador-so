package Simulador;

import Excepciones.InexistenteException;
import Excepciones.DenegadoException;
import Interfaz.GUIInterface;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Proceso implements Serializable {
    public static int ultimoId = 0;
    public int id;
    private int linea;
    public Programa programa;
    public Prioridad prioridad;
    
    public Proceso() {
        Proceso.ultimoId += 1;
        id = Proceso.ultimoId;
        linea = 0;
    }
    
    public void setearPrograma(Programa programa) {
        this.programa = programa;
    }
    
    public synchronized void ejecutarPrograma(
            Transicionable sistema, AdministradorRecursos administradorRecursos, int quantum, List<Operacion> operaciones, 
            int operacionesDelay, Usuario logueado
    ) throws InterruptedException {
        int suma = 0;
        
        while (programa.hasNext() && suma < quantum) {
            sistema.wait(operacionesDelay);
            linea += 1;
            
            // obtengo siguiente instrucción
            String instruccion = programa.next();
            suma += obtenerPeso(operaciones, instruccion, quantum);
            String output = "Proceso " + this + ": " + instruccion + " (q " + suma + "/" + quantum + ")";
            
            // evalúo tipo de instruccion
            if(esRecursoSolicitar(instruccion)){
                boolean disponible;
                output += " solicitado";
                
                try {
                    disponible = administradorRecursos.solicitar(instruccion, this, logueado);
                } catch(InexistenteException | DenegadoException ex) {
                    GUIInterface.write(output);
                    GUIInterface.write("Proceso " + this + ": " + ex.getMessage());
                    sistema.transicion(Transicion.terminar, this);
                    return;
                }
                
                // si estoy solicitando un recurso y no está disponible, lo bloqueo
                if (!disponible) {
                    output += " (en uso)";
                    GUIInterface.write(output);

                    programa.back();
                    sistema.transicion(Transicion.bloquear, this);

                    return;
                }
            }
            // si estoy devolviendo un recurso, desbloqueo el siguiente en la cola de espera
            else if(esRecursoDevolver(instruccion)){
                Proceso procesoADespertar;
                        
                try {
                    procesoADespertar = administradorRecursos.devolver(instruccion, this, logueado);
                } catch(InexistenteException | DenegadoException ex) {
                    GUIInterface.write(output);
                    GUIInterface.write("Proceso " + this + ": " + ex.getMessage());
                    sistema.transicion(Transicion.terminar, this);
                    return;
                }
                
                output += " devuelto";
                GUIInterface.write(output);
                
                if (procesoADespertar != null) {
                    sistema.transicion(Transicion.despertar, procesoADespertar);
                }
                
                continue;
            }
            
            GUIInterface.write(output);
        }
        
        // si ya no tiene líneas que ejecutar
        if (!programa.hasNext()) {
            sistema.transicion(Transicion.terminar, this);
            return;
        } 
        
        // si termina de ejecutar, es timeout, envío a listos
        sistema.transicion(Transicion.timeout, this);
    }
    
    private boolean esRecursoSolicitar(String instruccion){
        return instruccion.contains("RS");
    }
     
    private boolean esRecursoDevolver(String instruccion){
        return instruccion.contains("RD");
    }
    
    private int obtenerPeso(List<Operacion> operaciones, String operacion, int quantum) {
        for (int x = 0; x < operaciones.size(); x += 1) {
            Operacion actual = operaciones.get(x);
            
            if (actual.operacion.equals(operacion)) {
                return actual.peso;
            }
        }
        
        Random random = new Random();
        return random.nextInt(quantum) + 1;
    }
    
    @Override
    public String toString() {
        return Integer.toString(id);
    }
    
    public Proceso nuevo() {
        Proceso proceso = new Proceso();
        proceso.id = this.id;
        proceso.setearPrograma(this.programa);
        return proceso;
    }
}
