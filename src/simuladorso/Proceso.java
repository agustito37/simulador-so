package simuladorso;

public class Proceso {
    private final int CANTIDAD_OPERACIONES = 4;
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
    
    // devuelve false si el programa no tiene mas lineas que ejecutar
    public void ejecutarPrograma(SistemaOperativo sistema) {
        int hasta = linea + CANTIDAD_OPERACIONES;
        
        while (linea < hasta) {
            
            
            
            if (!programa.hasNext()) {
                sistema.recibeEvento(TipoEstado.finalizado, this);
                return;
            }
            
            linea += 1;
            String instruccion = programa.next();
            System.out.println(instruccion);
            if(esRecurso(instruccion) && esRecursoSolicitar(instruccion)){
                if(!administradorRecursos.solicitar(instruccion, this)){
                    sistema.recibeEvento(TipoEstado.bloqueado, this);
                }
                
            }
            else if(esRecurso(instruccion) && esRecursoDevolver(instruccion)){
                administradorRecursos.devolver(instruccion, this);
                sistema.recibeEvento(TipoEstado.desbloquear, this);
            }
        
        }
        
    }
    
    public boolean esRecurso(String instruccion){
        return instruccion.contains("R");
    }
    
     public boolean esRecursoSolicitar(String instruccion){
        return instruccion.contains("S");
    }
     
      public boolean esRecursoDevolver(String instruccion){
        return instruccion.contains("D");
    }
    
    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
