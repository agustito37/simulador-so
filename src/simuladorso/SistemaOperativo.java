package simuladorso;
import java.util.Iterator;

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
    private Estado bloqueado;
    private Estado listo;
    private AdministradorRecursos administradorRecursos;
    
    public SistemaOperativo() {
        ejecutando = new Ejecutando();
        bloqueado = new Bloqueado();
        listo = new Listo();
        administradorRecursos = new AdministradorRecursos();
    }
    
    public void iniciar() {
        Proceso proceso1 = new Proceso(administradorRecursos);
        Programa programa = new Programa("RS1 T J K RD1");
        proceso1.setearPrograma(programa);
        ejecutando.agregarProceso(proceso1);
        
        Proceso proceso2 = new Proceso(administradorRecursos);
        Programa programa2 = new Programa("RS1 P..");
        proceso2.setearPrograma(programa2);
        ejecutando.agregarProceso(proceso2);
    }
    
    public void recibeEventoProcesador(EventoProcesador estado, Proceso proceso) {
        switch (estado) {
            case finalizado:
                ejecutando.quitarProceso(proceso);
                break;
            case bloqueado:
                ejecutando.quitarProceso(proceso);
                bloqueado.agregarProceso(proceso);
                break;
            case desbloquedo:
                bloqueado.quitarProceso(proceso);
                listo.agregarProceso(proceso);
                break;
            default:
                break;
        }
    }
    
    public void ejecutar() throws InterruptedException {
        // mientras tenga procesos que ejecutar
        while(true) {
            
            // ejecuto procesos en la lista de ejecucion
            Iterator<Proceso> procesos = ejecutando.obtenerProcesos();
            while (procesos.hasNext()) {
                Proceso proceso = procesos.next();
                proceso.ejecutarPrograma(this);
            }
            
            // agrego a la lista de ejecucion procesos listos
            Iterator<Proceso> procesosListos = listo.obtenerProcesos();
            while (procesosListos.hasNext()) {
                ejecutando.agregarProceso(procesosListos.next());
            }
            listo.vaciar();
            
            // chequeo estado del sistema operativo
            if (ejecutando.estaVacio()) {
                if (bloqueado.estaVacio()) {
                    System.out.println("Nada más que ejecutar");
                    break;
                } else {
                    System.out.println("Quedan procesos bloqueados");
                    Thread.sleep(1000);
                }
            }
        }
    }
}
