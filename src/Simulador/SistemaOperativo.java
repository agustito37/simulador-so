package Simulador;
import Interfaz.GUIInterface;
import java.util.Iterator;
import java.util.List;

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
    private int quantum;
    private int operacionesCiclo;
    private int operacionesDelay;
    private Usuario logueado;
    private Estado ejecutando;
    private Estado bloqueado;
    private Estado listo;
    public AdministradorRecursos administradorRecursos;
    private List<Proceso> procesos;
    private List<Operacion> operaciones;
    private List<Recurso> recursos;
    
    public SistemaOperativo(Usuario pLogueado, List<Proceso> pProcesos, List<Operacion> pOperaciones, List<Recurso> pRecursos, int pQuantum, int pOperacionesCiclo, int pOperacionesDelay) {
        logueado = pLogueado;
        quantum = pQuantum;
        operacionesCiclo = pOperacionesCiclo;
        operacionesDelay = pOperacionesDelay;
        ejecutando = new Ejecutando();
        bloqueado = new Bloqueado();
        listo = new Listo();
        administradorRecursos = new AdministradorRecursos();
        procesos = pProcesos;
        operaciones = pOperaciones;
        recursos = pRecursos;
    }
    
    public void iniciar() {
        for (int x = 0; x < procesos.size(); x++) {
            // clono proceso y programa para no reutilizarlo cada vez que se inicia el sistema operativo
            Proceso proceso = procesos.get(x).nuevo();
            proceso.setearPrograma(proceso.programa.nuevo());
            
            transicion(Transicion.comenzar, proceso);
        }
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
        GUIInterface.write(output);
    }
    
    public synchronized void ejecutar() throws InterruptedException {
        // mientras tenga procesos que ejecutar
        while(true) {
            // despacho procesos según cantidad de núcleos
            Iterator<Proceso> procesosListos = listo.obtenerProcesos();

            if (procesosListos.hasNext()) {
                transicion(Transicion.despachar, procesosListos.next());
            }
            
            // ejecuto procesos en la lista de ejecucion
            Iterator<Proceso> procesosAEjecutar = ejecutando.obtenerProcesos();
            while (procesosAEjecutar.hasNext()) {
                Proceso proceso = procesosAEjecutar.next();
                
                // chequeo si tiene permisos para ejecutar el programa
                if(!proceso.programa.permiso.equals(logueado.permiso)){
                    GUIInterface.write("Error: permisos insuficientes.");
                    this.transicion(Transicion.terminar, proceso);
                    continue;
                }
                
                proceso.ejecutarPrograma(this, administradorRecursos, quantum, operaciones, operacionesCiclo, operacionesDelay, logueado);
            }
            
            // chequeo estado del sistema operativo
            if (listo.estaVacio()) {
                if (bloqueado.estaVacio()) {
                    GUIInterface.write("Nada más que ejecutar");
                    break;
                } else {
                    GUIInterface.write("Quedan procesos bloqueados");
                    wait(operacionesDelay == 0 ? 0 : 1000);
                }
            }
        }
    }
}
