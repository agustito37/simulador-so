/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulador;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author agustin
 */
public class Programa implements Iterator<String>, Serializable, Permisible {
    private final String DELIMITADORES = "\\s+|\\r?\\n+";
    private String[] operaciones;
    public String cadena;
    private int linea;
    private Permisos permiso;
    public int memoria;
    
    public Programa(String pCadena, Permisos permisoUsuario, int pMemoria) {
        linea = -1;
        cadena = pCadena;
        operaciones = cadena.split(DELIMITADORES);
        permiso = permisoUsuario;
        memoria = pMemoria;
    }
    
    public Programa nuevo( ) {
        return new Programa(this.cadena, this.permiso, this.memoria);
    }
    
    public void back() {
        linea--;
    }
    
    @Override
    public String next(){
        linea++;
        return operaciones[linea];
    }
    
    @Override
    public boolean hasNext() {
        return linea < operaciones.length - 1;
    }
    
    @Override
    public String toString(){
        return cadena + " - " + memoria + " bytes - " + permiso;
    }
    
    @Override
    public Permisos obtenerPermiso() {
        return permiso;
    }
}
