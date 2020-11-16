
package Simulador;

/**
 *
 * @author Mauro
 */
public class Permisos {
    public static boolean tieneAcceso(Permisible usuario, Permisible objeto){
        return usuario.obtenerPermiso().equals(objeto.obtenerPermiso());
    }
}
