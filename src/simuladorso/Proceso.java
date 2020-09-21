/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorso;

/**
 *
 * @author agustin
 */
public class Proceso {
    private final int CANTIDAD_OPERACIONES = 4;
    private static int ultimoId = 0;
    private int id;
    private int linea;
    private Programa programa;
    
    public Proceso() {
        Proceso.ultimoId += 1;
        id = Proceso.ultimoId;
        linea = 0;
    }

    public void setearPrograma(Programa programa) {
        this.programa = programa;
    }
    
    // devuelve false si el programa no tiene mas lineas que ejecutar
    public boolean ejecutarPrograma() {
        int hasta = linea + CANTIDAD_OPERACIONES;
        
        while (linea < hasta) {
            if (!programa.hasNext()) {
                return false;
            }
            
            linea += 1;
            System.out.println(programa.next());
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
