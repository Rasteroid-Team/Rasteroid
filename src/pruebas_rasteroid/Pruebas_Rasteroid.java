package pruebas_rasteroid;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Pruebas_Rasteroid extends JFrame{

    public Pruebas_Rasteroid() {
        setWindowParameters();
        
    
    
    }
    
    private void setWindowParameters() {
        this.setVisible(true);
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        new Pruebas_Rasteroid();
        
    }
    
}
