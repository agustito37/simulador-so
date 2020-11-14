
package simuladorso;

import java.util.ArrayList;

/**
 *
 * @author Mauro
 */
public class Usuario {
    
    private String idUsuario;
    private Permisos permisos;

    public String getId_usuario() {
        return idUsuario;
    }

    public void setId_usuario(String id_usuario) {
        this.idUsuario = id_usuario;
    }        
            
    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }
   
    //tipos de usario: todos los servicios, servicios basicos, servicios escenciales
    public Usuario (String unIdUsuario){
        idUsuario = unIdUsuario;
        permisos = new Permisos();
    }
  
    
    
}
