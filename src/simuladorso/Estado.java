/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorso;

import java.util.Iterator;

/**
 *
 * @author agustin
 */
public interface Estado {
    public void agregarProceso(Proceso proceso);
    public void eliminarProceso(Proceso proceso);
    public Iterator<Proceso> obtenerProcesos();
}
