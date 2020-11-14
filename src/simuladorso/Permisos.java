
package simuladorso;

import java.util.ArrayList;

/**
 *
 * @author Mauro
 */
public class Permisos {
    private ArrayList<String> permisos;
    

    public ArrayList<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(ArrayList<String> permisos) {
        this.permisos = permisos;
    }
    
    public Permisos(){
        permisos = new ArrayList();
    }
    
    public Permisos(String permiso){
          
        if(validarPermisos(permiso)){
            permisos = new ArrayList();
            permisos.add(permiso);
        }
        else {
            permisos = new ArrayList();
            permisos.add("Servicios Escencales");
        }
   
    
        
    }
    
    public void agregarPermiso(String permiso){
        if(validarPermisos(permiso)){
            permisos.add(permiso);
        }
    }
    
    public void quitarPermiso(String permiso){
        if(permisos.contains(permiso)){
            permisos.remove(permiso);
        }
    }

    
    
    boolean validarPermisos(String tipos){
        return tipos == "Todos Los Servicios" || 
               tipos == "Servicios Basicos" || 
               tipos == "Servicios Escencales";
    }
}
