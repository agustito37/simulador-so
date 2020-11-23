/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulador;

import Interfaz.GUIInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author agustin
 */
public class AdministradorMemoria {
    private ArrayList<Proceso> procesos;
    private int memoriaInicial;
    private int memoriaDisponible;
    
    public AdministradorMemoria(int pMemoria) {
        procesos = new ArrayList();
        memoriaInicial = pMemoria;
        memoriaDisponible = pMemoria;
        GUIInterface.write("Administrador de memoria: " + memoriaDisponible + " bytes");
    }
    
    public List<Proceso> procesosAsignados() {
        List<Proceso> aProcesar = new ArrayList();
        
        for (int x = 0; x < procesos.size(); x += 1) {
            Proceso proceso = procesos.get(x);
            
            if (memoriaDisponible - proceso.programa.memoria >= 0) {
                aProcesar.add(proceso);
                memoriaDisponible -= proceso.programa.memoria;
                GUIInterface.write("Administrador de memoria: proceso " + proceso.id + " asignado (" + proceso.programa.memoria + " bytes) - " + memoriaDisponible + " bytes disponibles");
            } else {
                break;
            }
        }
        
        for (int x = 0; x < aProcesar.size(); x += 1) {
            procesos.remove(aProcesar.get(x));
        }
        
        return aProcesar;
    }
    
    public void pedirMemoria(List<Proceso> pProcesos) {
        for (int x = 0; x < pProcesos.size(); x += 1) {
            Proceso proceso = pProcesos.get(x);
            
            if (proceso.programa.memoria > memoriaInicial) {
                GUIInterface.write("Administrador de memoria: memoria insuficiente para el proceso " + proceso.id + " (" + proceso.programa.memoria + " bytes) - ignorado");
                continue;
            }
            
            procesos.add(proceso);
        }
    }
    
    public void liberarMemoria(Proceso proceso) {
        memoriaDisponible += proceso.programa.memoria;
        GUIInterface.write("Administrador de memoria: proceso " + proceso.id + " liberado (" + proceso.programa.memoria + " bytes) - " + memoriaDisponible + " bytes disponibles");
    }
    
    public boolean estaVacio() {
        return procesos.isEmpty();
    }
}
