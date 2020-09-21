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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SistemaOperativo so = new SistemaOperativo();
        so.iniciar();
        so.ejecutar();
    } 
}
