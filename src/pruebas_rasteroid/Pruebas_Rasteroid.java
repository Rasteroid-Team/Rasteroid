package pruebas_rasteroid;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Pruebas_Rasteroid extends JFrame {

    private ArrayList<Nave> naves = new ArrayList<Nave>();
    private Physics physics;
    private Viewer viewer;

    public Pruebas_Rasteroid() {
        addKeyListener(new TAdapter());
        setWindowParameters();
        naves.add(new Nave());
        naves.add(new Nave());
        naves.add(new Nave());
        naves.add(new Nave());
        naves.add(new Nave());
        physics = new Physics(naves);
        viewer = new Viewer(naves, this.getWidth(), this.getHeight());
        this.add(viewer);

        Thread thread1 = new Thread(physics);
        thread1.start();
        Thread thread2 = new Thread(viewer);
        thread2.start();

        this.setVisible(true);

    }

    private void setWindowParameters() {
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        new Pruebas_Rasteroid();

    }

    private class TAdapter extends KeyAdapter {

//        @Override
//        public void keyReleased(KeyEvent e) {
//            for (Nave nave : naves) {
//                nave.keyReleased(e);
//            }
//            
//        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Pressed");
              for (Nave nave : naves) {
                nave.daleGas(e);
            }
        }
    }

}
