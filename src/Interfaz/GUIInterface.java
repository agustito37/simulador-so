/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.JTextArea;

/**
 *
 * @author agustin
 */
public class GUIInterface {
    static JTextArea field;
    
    public static void setControl(JTextArea pField) {
        field = pField;
    }
    
    public static void write(String text) {
        field.append(text + "\n");
        field.setCaretPosition(field.getDocument().getLength());
    }
    
    public static void clear() {
        field.setText("");
    }
}
