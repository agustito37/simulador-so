package simuladorso;


public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SistemaOperativo so = new SistemaOperativo();
        so.iniciar();
        so.ejecutar();
    } 
}
