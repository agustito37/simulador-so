/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorso;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author agustin
 */
public class Programa implements Iterator<String> {
    private final String DELIMITADORES = "\\s+|\\r?\\n+";
    private String[] operaciones;
    private int linea;
    private String permiso;

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
    
    
    public Programa(String cadena, String permisoUsuario) {
        linea = -1;
        operaciones = cadena.split(DELIMITADORES);
        permiso = permisoUsuario;
    }
    
    
    
    @Override
    public boolean hasNext() {
        return linea < operaciones.length - 1;
    }
    
    @Override
    public String next(){
        linea += 1;
        return operaciones[linea];
    }
}
