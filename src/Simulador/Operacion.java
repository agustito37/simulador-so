/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulador;

import java.io.Serializable;

/**
 *
 * @author agustin
 */
public class Operacion implements Serializable {
    public String operacion;
    public int peso;
            
    public Operacion(String pOperacion, int pPeso) {
        operacion = pOperacion;
        peso = pPeso;
    }
}
