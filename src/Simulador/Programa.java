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
public class Programa implements Iterator<String>, Serializable {
    private final String DELIMITADORES = "\\s+|\\r?\\n+";
    private String[] operaciones;
    private String cadena;
    private int linea;
    public String permiso;
    
    public Programa(String pCadena, String permisoUsuario) {
        linea = -1;
        cadena = pCadena;
        operaciones = cadena.split(DELIMITADORES);
        permiso = permisoUsuario;
    }
    
    @Override
    public boolean hasNext() {
        return linea < operaciones.length - 1;
    }
    
    @Override
    public String next(){
        linea++;
        return operaciones[linea];
    }
    
    public void back() {
        linea--;
    }
    
    @Override
    public String toString(){
        return cadena;
    }
    
    public Programa nuevo( ) {
        return new Programa(this.cadena, this.permiso);
    }
}
