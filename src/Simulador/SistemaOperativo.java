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
    private int operacionesDelay;
    private int schedulingFilaBaja;
    private Usuario logueado;
    private Estado ejecutando;
    private Estado bloqueado;
    private Listo listo;
    public AdministradorRecursos administradorRecursos;
    private List<Proceso> procesos;
    private List<Operacion> operaciones;
    
    public SistemaOperativo(Usuario pLogueado, List<Proceso> pProcesos, List<Operacion> pOperaciones, List<Recurso> pRecursos, int pQuantum, int pOperacionesDelay, int pSchedulingBaja) {
        logueado = pLogueado;
        quantum = pQuantum;
        operacionesDelay = pOperacionesDelay;
        ejecutando = new Ejecutando();
        bloqueado = new Bloqueado();
        listo = new Listo();
        administradorRecursos = new AdministradorRecursos(pRecursos);
        procesos = pProcesos;
        operaciones = pOperaciones;
        schedulingFilaBaja = pSchedulingBaja;
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
                proceso.prioridad = Prioridad.alta;
                output += "comenzado (prioridad alta)";
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
                proceso.prioridad = Prioridad.alta;
                listo.agregarProceso(proceso);
                output += "despertado (prioridad alta)";
                break;
            case timeout:
                ejecutando.quitarProceso(proceso);
                proceso.prioridad = Prioridad.baja;
                listo.agregarProceso(proceso);
                output += "timeout (prioridad baja)";
                break;
            case despachar:
                listo.quitarProceso(proceso);
                ejecutando.agregarProceso(proceso);
                output += "despachado (prioridad " + proceso.prioridad.toString() + ")";
                break;
            default:
                break;
        }
        GUIInterface.write(output);
    }
    
    public synchronized void ejecutar() throws InterruptedException {
        int tickActual = 0;
                
        // mientras tenga procesos que ejecutar
        while(true) {
            tickActual += 1;
            
            // despacho procesos según su prioridad
            Iterator<Proceso> procesosListos = listo.obtenerProcesosPriorizados(tickActual, schedulingFilaBaja);

            if (procesosListos.hasNext()) {
                transicion(Transicion.despachar, procesosListos.next());
            }
            
            // ejecuto procesos en la lista de ejecucion
            Iterator<Proceso> procesosAEjecutar = ejecutando.obtenerProcesos();
            while (procesosAEjecutar.hasNext()) {
                Proceso proceso = procesosAEjecutar.next();
                
                // chequeo si tiene permisos para ejecutar el programa
                if(!Permisos.tieneAcceso(logueado, proceso.programa)){
                    GUIInterface.write("Proceso " + proceso + ": Error - permisos insuficientes");
                    this.transicion(Transicion.terminar, proceso);
                    continue;
                }
                
                proceso.ejecutarPrograma(this, administradorRecursos, quantum, operaciones, operacionesDelay, logueado);
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
