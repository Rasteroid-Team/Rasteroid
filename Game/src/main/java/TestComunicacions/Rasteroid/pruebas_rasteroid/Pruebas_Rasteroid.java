package TestComunicacions.Rasteroid.pruebas_rasteroid;

import TestComunicacions.communications.*;

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
import java.util.HashMap;

import static java.lang.Thread.sleep;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Pruebas_Rasteroid extends JFrame implements Runnable, ConnectionInterface {

    private ArrayList<GameObject> naves = new ArrayList<GameObject>();
    private Viewer viewer;
    private JButton boton;
    private JTextArea textArea ;
    private CommunicationController communicationController;
    private int anguloFuerza = 0;
    private int potencia = 0;
    private boolean accelerando = false;
    private InputAdapter iad; 


    //********************************
    private HashMap<String, int> dispositivos = new HashMap<String, int>();
    //********************************

    public Pruebas_Rasteroid() {
        crearInterfaz(this);
        //*******************
        communicationController = new CommunicationController();
        communicationController.addAllListeners(this);
        inicializarProtocolos();



        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));

        
        new Thread(this).start();
    }

    //************************
    private void inicializarProtocolos() {
        communicationController.addNewProtocol(
                100,
                "Pasar GameObjects entre dispositivos",
                "GameObject"
        );



    }
    //**********************
    // ------ GAME CONTROLLER ----------
    
    private void updatePositions() {
        boolean [] teclas = iad.get_active_keys();
        for (GameObject nave : naves) {
        
        accelerando = teclas[0];
        if (teclas[1]) {
            anguloFuerza -= 5;
        }
        if (teclas[2]) {
            anguloFuerza += 5;
        }
        
          nave.getDynamicBody().setAngle(anguloFuerza);
            if (!accelerando) {
                nave.getDynamicBody().move(0,0);
                if( potencia > 0 )potencia-= 0.3;
            } else {

                System.out.println("aa");
                potencia = 80;
                //************
                int wall = nave.getDynamicBody().move(anguloFuerza, potencia);
                if(wall != -1) {
                    sendGameObject(wall, nave);
                }
                //**********
            }
            
        }
    }


    //********************************
        public void sendGameObject(int wall, GameObject go){
            for(String e : dispositivos){
                if(connections.get(e) == direction){
                    controller.sendMessage(controller.createPacket(e,69,ball));
                }
            }
        }
    //********************************
    
    // ---------------------------------
    
    // ------ INTERFAZ ----------

    private void setWindowParameters() {
        this.setSize(1300, 1000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private void crearInterfaz(Pruebas_Rasteroid papi) {
        setWindowParameters();
        
        // botone
        Container pane = papi.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        boton = new JButton("ENVIA EL ÁNGULO");
        textArea = new JTextArea("ESCRIBE EL ÁNGULO");
        viewer = new Viewer(naves, papi.getWidth()-30, papi.getHeight()-50);
        iad = new InputAdapter();
        viewer.addKeyListener(iad);
        
         

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


        Thread thread2 = new Thread(viewer);
        thread2.start();

        papi.setVisible(true);
        

        //Activar boton
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int angulo = Integer.parseInt(textArea.getText());
                //TODO 
                //paso el angulo a la nave
                 for (int i = 0; i < naves.size(); i++) {
                       naves.get(i).getDynamicBody().setAngle(angulo);
        }
            }
        });
    }

    // --------------------------
    
    public static void main(String[] args) {
        new Pruebas_Rasteroid();
    }

    //********************

    @Override
    public void run() {
        while (true) {
            try {
                updatePositions();
                sleep(16);
            } catch (InterruptedException ex) {
                System.out.println("El thread ha sufrido un problema");
            }
        }
    }

    @Override
    public void onMessageReceived(ProtocolDataPacket packet) {

    }

    @Override
    public void onConnectionAccept(String mac) {
        
    }

    @Override
    public void onConnectionClosed(String mac) {

    }

    @Override
    public void onLookupUpdate(ArrayList<String> macs) {

    }
}
