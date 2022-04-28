package pruebas_rasteroid;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class Pruebas_Rasteroid extends JFrame {

    private ArrayList<Nave> naves = new ArrayList<Nave>();
    private Physics physics;
    private Viewer viewer;
    private JButton boton;
    private JTextArea textArea ;

    public Pruebas_Rasteroid() {
        
        setWindowParameters();
        naves.add(new Nave());
        naves.add(new Nave());
        naves.add(new Nave());
        naves.add(new Nave());
        naves.add(new Nave());
        physics = new Physics(naves);
        
        
        // botone
        Container pane = this.getContentPane();


        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        boton = new JButton("ENVIA EL ÁNGULO");
        textArea = new JTextArea("ESCRIBE EL ÁNGULO");
        viewer = new Viewer(naves, this.getWidth()-30, this.getHeight()-50);
        viewer.addKeyListener(new TAdapter());

        c.gridx = 0;
        c.gridy = 0;
       pane.add(textArea, c);
        
         c.gridx = 1;
        c.gridy = 0;
      pane.add(boton, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridwidth =2;
        c.gridy = 1;
    
         pane.add(viewer, c);


        Thread thread1 = new Thread(physics);
        thread1.start();
        Thread thread2 = new Thread(viewer);
        thread2.start();

        this.setVisible(true);
        
        //Activar boton
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int angulo = Integer.parseInt(textArea.getText());
                System.out.println("Me has clocado. El ángulo es " +angulo);
                //TODO 
                //paso el angulo a la nave
                 for (int i = 0; i < naves.size(); i++) {
                       naves.get(i).setAngle(angulo);
        }
            }
        });
        
        

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
