/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulador;

/**
 *
 * @author agustin
 */
public enum Prioridad {
    baja(2),
    alta(1);

    private int valor;

    Prioridad(int pValor) {
        valor = pValor;
    }

    public int obtenerValor() {
        return valor;
    }
};
