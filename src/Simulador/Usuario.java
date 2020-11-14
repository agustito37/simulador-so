
package Simulador;

import java.io.Serializable;

/**
 *
 * @author Mauro
 */
public class Usuario implements Serializable {
    public String idUsuario;
    public String permiso;

    public String getId_usuario() {
        return idUsuario;
    }

    public void setId_usuario(String id_usuario) {
        this.idUsuario = id_usuario;
    }  
   
    //tipos de usario: todos los servicios, servicios basicos, servicios escenciales
    public Usuario (String pIdUsuario, String pPermiso){
        idUsuario = pIdUsuario;
        permiso = pPermiso;
    }
  
    
    
}
