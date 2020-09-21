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
    
    public SistemaOperativo() {
        ejecutando = new Ejecutando();
        finalizado = new Finalizado();
    }
    
    public void iniciar() {
        Proceso proceso1 = new Proceso();
        Programa programa = new Programa("A T D Y E");
        proceso1.setearPrograma(programa);
        ejecutando.agregarProceso(proceso1);
        
        Proceso proceso2 = new Proceso();
        Programa programa2 = new Programa("I O G P Z");
        proceso2.setearPrograma(programa2);
        ejecutando.agregarProceso(proceso2);
    }
    
    public void ejecutar() {
        Iterator<Proceso> procesos = ejecutando.obtenerProcesos();
        while(procesos.hasNext()) {
            List<Proceso> terminados = new ArrayList();
            
            while (procesos.hasNext()) {
                Proceso proceso = procesos.next();
                
                if (!proceso.ejecutarPrograma()) {
                    terminados.add(proceso);
                }
            }
            
            terminados.stream().forEach((proceso) -> {
                ejecutando.eliminarProceso(proceso);
                finalizado.agregarProceso(proceso);
            });
            
            procesos = ejecutando.obtenerProcesos();
        }
    }
}
