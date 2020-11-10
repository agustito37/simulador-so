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
public class SistemaOperativo implements Transicionable {
    private final int CANT_NUCLEOS = 1;
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
        transicion(Transicion.comenzar, proceso1);
        
        Proceso proceso2 = new Proceso(administradorRecursos);
        Programa programa2 = new Programa("RS1 P");
        proceso2.setearPrograma(programa2);
        transicion(Transicion.comenzar, proceso2);
    }
    
    @Override
    public void transicion(Transicion transicion, Proceso proceso) {
        String output = "Proceso " + proceso + ": ";
        switch (transicion) {
            case comenzar:
                listo.agregarProceso(proceso);
                output += "comenzado";
                break;
            case terminar:
                ejecutando.quitarProceso(proceso);
                output += "terminado";
                break;
            case bloquear:
                ejecutando.quitarProceso(proceso);
                bloqueado.agregarProceso(proceso);
                output += "bloqueado";
                break;
            case despertar:
                bloqueado.quitarProceso(proceso);
                listo.agregarProceso(proceso);
                output += "despertado";
                break;
            case timeout:
                ejecutando.quitarProceso(proceso);
                listo.agregarProceso(proceso);
                output += "timeout";
                break;
            case despachar:
                listo.quitarProceso(proceso);
                ejecutando.agregarProceso(proceso);
                output += "despachado";
                break;
            default:
                break;
        }
        System.out.println(output);
    }
    
    public void ejecutar() throws InterruptedException {
        // mientras tenga procesos que ejecutar
        while(true) {
            // despacho procesos según cantidad de núcleos
            Iterator<Proceso> procesosListos = listo.obtenerProcesos();
            int despachados = 0;
            while (procesosListos.hasNext() && despachados < CANT_NUCLEOS) {
                transicion(Transicion.despachar, procesosListos.next());
                despachados++;
            }
            
            // ejecuto procesos en la lista de ejecucion
            Iterator<Proceso> procesosAEjecutar = ejecutando.obtenerProcesos();
            while (procesosAEjecutar.hasNext()) {
                Proceso proceso = procesosAEjecutar.next();
                proceso.ejecutarPrograma(this);
            }
            
            // chequeo estado del sistema operativo
            if (listo.estaVacio()) {
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
