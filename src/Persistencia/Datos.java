/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import Simulador.Programa;
import Simulador.Proceso;
import Simulador.Operacion;

/**
 *
 * @author agustin
 */
public class Datos {
    private static final String ARCHIVO = "so.data";
    public static List<Programa> programas;
    public static List<Proceso> procesos;
    public static List<Operacion> operaciones;
            
    public static void guardarDatos(List<Programa> programas, List<Proceso> procesos, List<Operacion> operaciones) {
        FileOutputStream file = null;
        
        try {
            file = new FileOutputStream(new File(ARCHIVO));
            ObjectOutputStream stream = new ObjectOutputStream(file);
            
            stream.writeObject(programas);
            stream.writeObject(procesos);
            stream.writeObject(operaciones);
            
            stream.close();
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void cargarDatos() {
        FileInputStream file = null;
        
        try {
            file = new FileInputStream(new File(ARCHIVO));
            ObjectInputStream stream = new ObjectInputStream(file);
            
            programas = (List<Programa>) stream.readObject();
            procesos = (List<Proceso>) stream.readObject();
            operaciones = (List<Operacion>) stream.readObject();
            
            stream.close();
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
