package simuladorso;

import gui.GUIInterface;
import java.io.Serializable;

public class Proceso implements Serializable {
    private static int ultimoId = 0;
    public int id;
    private int linea;
    public Programa programa;
    
    public Proceso() {
        Proceso.ultimoId += 1;
        id = Proceso.ultimoId;
        linea = 0;
    }
    
    public void setearPrograma(Programa programa) {
        this.programa = programa;
    }
    
    public void ejecutarPrograma(Transicionable sistema, AdministradorRecursos administrador, int operacionesCiclo, int operacionesDelay) throws InterruptedException {
        int hasta = linea + operacionesCiclo;
        
        // ejecuto x Cantidad de operaciones del programa
        while (linea < hasta) {
            // si ya no tiene líneas que ejecutar
            if (!programa.hasNext()) {
                sistema.transicion(Transicion.terminar, this);
                return;
            }
            
            Thread.sleep(operacionesDelay);
            linea += 1;
            String instruccion = programa.next();
            String output = "Proceso " + this.id + ": " + instruccion;
            
            if(esRecursoSolicitar(instruccion)){
                output += " solicitado";
                
                // si estoy solicitando un recurso y no está disponible, lo bloqueo
                if(!administrador.solicitar(instruccion, this)){
                    output += " (en uso)";
                    GUIInterface.write(output);
                    
                    programa.back();
                    sistema.transicion(Transicion.bloquear, this);
                    
                    return;
                } 
            }
            // si estoy devolviendo un recurso, desbloqueo el siguiente en la cola de espera
            else if(esRecursoDevolver(instruccion)){
                Proceso siguienteProceso = administrador.devolver(instruccion, this);
                
                output += " devuelto";
                GUIInterface.write(output);
                
                if (siguienteProceso != null) {
                    sistema.transicion(Transicion.despertar, siguienteProceso);
                }
                
                continue;
            }
            
            GUIInterface.write(output);
        }
        
        // si termina de ejecutar, es timeout, envío a listos
        sistema.transicion(Transicion.timeout, this);
    }
    
    public boolean esRecurso(String instruccion){
        return instruccion.contains("R");
    }
    
    public boolean esRecursoSolicitar(String instruccion){
        return esRecurso(instruccion) && instruccion.contains("S");
    }
     
    public boolean esRecursoDevolver(String instruccion){
        return esRecurso(instruccion) && instruccion.contains("D");
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
