package simuladorso;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author agustin
 */
public class SistemaOperativo {
    private Estado ejecutando;
    private Estado finalizado;
    private AdministradorRecursos administradorRecursos;
    
    public SistemaOperativo() {
        ejecutando = new Ejecutando();
        finalizado = new Finalizado();
        administradorRecursos = new AdministradorRecursos();
    }
    
    public void iniciar() {
        Proceso proceso1 = new Proceso(administradorRecursos);
        Programa programa = new Programa("A T D Y E RS1 RS2 RD2");
        proceso1.setearPrograma(programa);
        ejecutando.agregarProceso(proceso1);
        
        Proceso proceso2 = new Proceso(administradorRecursos);
        Programa programa2 = new Programa("A I O G P Z RS1 RS2");
        proceso2.setearPrograma(programa2);
        ejecutando.agregarProceso(proceso2);
    }
    
    public void recibeEvento(TipoEstado estado, Proceso proceso) {
        
        if(estado == TipoEstado.finalizado){
            
        }
       
    }
    
    public void ejecutar() {
        Iterator<Proceso> procesos = ejecutando.obtenerProcesos();
        while(procesos.hasNext()) {
            
            while (procesos.hasNext()) {
                Proceso proceso = procesos.next();
                
                proceso.ejecutarPrograma(this);
            }
            
            procesos = ejecutando.obtenerProcesos();
        }
    }
    
    public void finalizarProcesos(Proceso proceso){
        ejecutando.eliminarProceso(proceso);
        finalizado.agregarProceso(proceso);
    }
}
