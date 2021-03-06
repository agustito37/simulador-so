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
import Simulador.Recurso;
import Simulador.Usuario;

/**
 *
 * @author agustin
 */
public class Datos {
    private static final String ARCHIVO = "so.data";
    public static List<Programa> programas;
    public static List<Proceso> procesos;
    public static List<Operacion> operaciones;
    public static List<Usuario> usuarios;
    public static List<Recurso> recursos;
            
    public static void guardarDatos(List<Programa> programas, List<Proceso> procesos, List<Operacion> operaciones, List<Usuario> usuarios, List<Recurso> recursos) {
        FileOutputStream file = null;
        
        try {
            file = new FileOutputStream(new File(ARCHIVO));
            ObjectOutputStream stream = new ObjectOutputStream(file);
            
            stream.writeObject(programas);
            stream.writeObject(procesos);
            stream.writeObject(operaciones);
            stream.writeObject(usuarios);
            stream.writeObject(recursos);
            
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
            usuarios = (List<Usuario>) stream.readObject();
            recursos = (List<Recurso>) stream.readObject();
            Proceso.ultimoId = obtenerIdUltimoProceso();
            
            stream.close();
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
    private static int obtenerIdUltimoProceso() {
        int mayorId = 0;
        
        for (int x = 0; x < procesos.size(); x += 1) {
            Proceso proceso = procesos.get(x);
            if (proceso.id > mayorId) {
                mayorId = proceso.id;
            }
        }
            
        return mayorId;
    }
}
