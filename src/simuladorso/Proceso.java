package simuladorso;

public class Proceso {
    private final int CANT_OPERACIONES_CICLO = 4;
    private final int DELAY_OPERACIONES = 500;
    private static int ultimoId = 0;
    private int id;
    private int linea;
    private Programa programa;
    private AdministradorRecursos administradorRecursos;
    
    public Proceso(AdministradorRecursos administrador) {
        Proceso.ultimoId += 1;
        administradorRecursos = administrador;
        id = Proceso.ultimoId;
        linea = 0;
    }
    
    public void setearPrograma(Programa programa) {
        this.programa = programa;
    }
    
    public void ejecutarPrograma(Transicionable sistema) throws InterruptedException {
        int hasta = linea + CANT_OPERACIONES_CICLO;
        
        // ejecuto x Cantidad de operaciones del programa
        while (linea < hasta) {
            // si ya no tiene líneas que ejecutar
            if (!programa.hasNext()) {
                sistema.transicion(Transicion.terminar, this);
                return;
            }
            
            linea += 1;
            String instruccion = programa.next();
            System.out.println(instruccion);
            
            if(esRecursoSolicitar(instruccion)){
                // si estoy solicitando un recurso y no está disponible, lo bloqueo
                if(!administradorRecursos.solicitar(instruccion, this)){
                    sistema.transicion(Transicion.bloquear, this);
                    return;
                }
            }
            // si estoy devolviendo un recurso, desbloqueo el siguiente en la cola de espera
            else if(esRecursoDevolver(instruccion)){
                Proceso siguienteProceso = administradorRecursos.devolver(instruccion, this);
                
                if (siguienteProceso != null) {
                    sistema.transicion(Transicion.despertar, siguienteProceso);
                }
            }
            
            // simulo delay en las operaciones
            Thread.sleep(DELAY_OPERACIONES);
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
}
