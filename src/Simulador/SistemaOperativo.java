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
    private int schedulingFactor;
    private Usuario logueado;
    private Ejecutando ejecutando;
    private Bloqueado bloqueado;
    private Listo listo;
    public AdministradorRecursos administradorRecursos;
    private List<Proceso> procesos;
    private List<Operacion> operaciones;
    private AdministradorMemoria administradorMemoria;
    
    public SistemaOperativo(Usuario pLogueado, List<Proceso> pProcesos, List<Operacion> pOperaciones, List<Recurso> pRecursos, int pQuantum, int pOperacionesDelay, int pSchedulingFactor, int pMemoriaIncial) {
        ejecutando = new Ejecutando();
        bloqueado = new Bloqueado();
        listo = new Listo();
        schedulingFactor = pSchedulingFactor;
        logueado = pLogueado;
        quantum = pQuantum;
        operacionesDelay = pOperacionesDelay;
        procesos = pProcesos;
        operaciones = pOperaciones;
        administradorRecursos = new AdministradorRecursos(pRecursos);
        administradorMemoria = new AdministradorMemoria(pMemoriaIncial);
    }
    
    public void iniciar() {
        administradorMemoria.pedirMemoria(procesos);
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
                administradorRecursos.devolverRecursosProceso(proceso, this);
                administradorMemoria.liberarMemoria(proceso);
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
            
            // chequeo si tengo procesos para comenzar, los traigo si hay memoria disponible
            List<Proceso> procesosAsignados = administradorMemoria.asignarProcesosPedidos();
            for (int x = 0; x < procesosAsignados.size(); x++) {
                // clono proceso y programa para no reutilizarlo cada vez que comienza
                Proceso proceso = procesosAsignados.get(x).nuevo();
                proceso.setearPrograma(proceso.programa.nuevo());

                transicion(Transicion.comenzar, proceso);
            }
            
            // despacho procesos según su prioridad (despacho de a uno)
            Iterator<Proceso> procesosListos = listo.obtenerProcesosPriorizados(tickActual, schedulingFactor);
            if (procesosListos.hasNext()) {
                transicion(Transicion.despachar, procesosListos.next());
            }
            
            // ejecuto procesos en la lista de ejecucion (tiene que haber uno sólo despachado)
            Iterator<Proceso> procesosAEjecutar = ejecutando.obtenerProcesos();
            while (procesosAEjecutar.hasNext()) {
                Proceso proceso = procesosAEjecutar.next();
                
                // chequeo si el usuario tiene permisos para ejecutar el programa
                if(!Permisos.tieneAcceso(logueado, proceso.programa)){
                    GUIInterface.write("Proceso " + proceso + ": Error - permisos insuficientes");
                    this.transicion(Transicion.terminar, proceso);
                    continue;
                }
                
                proceso.ejecutarPrograma(this, administradorRecursos, quantum, operaciones, operacionesDelay, logueado);
            }
            
            // chequeo estado del sistema operativo si no hay proceso pendientes para asignar
            if (administradorMemoria.estaVacio() && listo.estaVacio()) {
                if (bloqueado.estaVacio()) {
                    GUIInterface.write("Sistema operativo: finalizado");
                    break;
                } else {
                    GUIInterface.write("Sistema operativo: deadlock");
                    resolverDeadlock();
                }
            }
        }
    }
    
    private void resolverDeadlock () {
        // quito el primer proceso de la lista de bloqueados para evitar deadlock
        Proceso proceso = bloqueado.desencolar();
        GUIInterface.write("Proceso " + proceso + ": matado");
        transicion(Transicion.terminar, proceso);
    }
}
