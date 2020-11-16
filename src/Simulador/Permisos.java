
package Simulador;

/**
 *
 * @author Mauro
 */
public enum Permisos {
    Maximo(3),
    Intermedio(2),
    Minimo(1);
    
    private int valor;
    
    Permisos(int pValor) {
        valor = pValor;
    }
    
    public int obtenerValor() {
        return valor;
    }
    
    public static boolean tieneAcceso(Permisible usuario, Permisible objeto){
        return usuario.obtenerPermiso().obtenerValor() >= objeto.obtenerPermiso().obtenerValor();
    }
}