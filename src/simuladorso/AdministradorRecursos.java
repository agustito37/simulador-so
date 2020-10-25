
package simuladorso;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Mauro
 */
public class AdministradorRecursos {
    
    
    
     private HashMap<String, Recurso> recursos;
     
     public AdministradorRecursos(){
         recursos = new HashMap <String, Recurso> ();
     }
     
     
     public boolean solicitar (String idRecurso, Proceso proceso){
         String id = idRecurso.replaceFirst("S", "");
         if(recursos.containsKey(id)){
             Recurso recurso = recursos.get(id);
             if(recurso.isDisponible()){
                 recurso.setProceso(proceso);
             }
             return recurso.isDisponible();
         }
         else {
             Recurso recurso = new Recurso (id, proceso);
             recursos.put(id, recurso);
             return true;
         }
     }
     
     public void devolver (String idRecurso, Proceso proceso){
         
         String id = idRecurso.replaceFirst("D", "");
         
         Recurso recurso = recursos.get(id);
         if(recurso.getProceso() == proceso){
              recurso.setProceso(null);
              recurso.setDisponible(true);
         }
         
     }
    
}
