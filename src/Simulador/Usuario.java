
package Simulador;

import java.io.Serializable;

/**
 *
 * @author Mauro
 */
public class Usuario implements Serializable, Permisible {
    public String idUsuario;
    private Permisos permiso;
    
    //tipos de usario: todos los servicios, servicios basicos, servicios escenciales
    public Usuario (String pIdUsuario, Permisos pPermiso){
        idUsuario = pIdUsuario;
        permiso = pPermiso;
    }  
  
    @Override
    public String toString(){
        return idUsuario;
    }
    
    @Override
    public Permisos obtenerPermiso() {
        return permiso;
    }
}
