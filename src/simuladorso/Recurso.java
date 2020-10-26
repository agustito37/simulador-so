
package simuladorso;

/**
 *
 * @author Mauro
 */
public class Recurso {
    
    private boolean disponible = true;
    private String idRecurso;
    private Proceso proceso;

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Recurso(String unRecurso, Proceso unProceso) {
        disponible = true;
        proceso = unProceso;
        idRecurso = unRecurso;
    }
    
     public Recurso(String unRecurso, boolean esDisponible) {
        disponible = esDisponible;
        idRecurso = unRecurso;
    }


    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getId() {
        return idRecurso;
    }

    public void setId(String id) {
        this.idRecurso = id;
    }
}
