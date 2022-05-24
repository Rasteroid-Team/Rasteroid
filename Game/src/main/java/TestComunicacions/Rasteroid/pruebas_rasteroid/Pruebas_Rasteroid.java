package TestComunicacions.Rasteroid.pruebas_rasteroid;

import TestComunicacions.communications.CommunicationController;
import TestComunicacions.communications.ConnectionInterface;
import TestComunicacions.communications.ProtocolDataPacket;
import View.ConnectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

public class Pruebas_Rasteroid extends JFrame implements Runnable, ConnectionInterface {

    private CopyOnWriteArrayList<GameObject> naves = new CopyOnWriteArrayList<>();
    private Viewer viewer;
    private JButton boton;
    private JTextArea textArea;


    private int direction;
    private HashMap<String, Integer> connections = new HashMap<>();


    private JButton connectionTest = new JButton("Connect!");
    private CommunicationController controller = new CommunicationController();

    private int anguloFuerza = 0;
    private int potencia = 0;
    private boolean accelerando = false;
    private InputAdapter iad;

    public Pruebas_Rasteroid() {
        crearInterfaz(this);

        controller.addAllListeners(this);

        naves.add(new GameObject(new DynamicBody()));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));


        new Thread(this).start();
        inicializarProtocolos();
    }

    // ------ GAME CONTROLLER ----------

    private synchronized void updatePositions() {
        boolean[] teclas = iad.get_active_keys();
        int wall = -1;

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
                wall = nave.getDynamicBody().move(0, 0);
                if (potencia > 0) potencia -= 0.3;
            } else {
                potencia = 80;
                wall = nave.getDynamicBody().move(anguloFuerza, potencia);
            }


            if (wall != -1) {
                synchronized (nave) {
                    this.sendGameObject(wall, nave);
                    naves.remove(nave);
                    viewer.removePlayer(nave);

                }


            }
        }
    }

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
        viewer = new Viewer(naves, papi.getWidth() - 30, papi.getHeight() - 50);
        iad = new InputAdapter();
        viewer.addKeyListener(iad);

        papi.add(connectionTest);
        connectionTest.addActionListener(e -> {
            ConnectionPanel cpanel = new ConnectionPanel(this);
            System.out.println(Arrays.toString(cpanel.values));
            String port = (cpanel.values[0]);
            direction = Integer.parseInt(cpanel.values[1]);

            controller.connectToIp(port);


        });


        c.gridx = 0;
        c.gridy = 0;
        pane.add(textArea, c);

        c.gridx = 1;
        c.gridy = 0;
        pane.add(boton, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridwidth = 2;
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
        System.out.println("recieved");
        switch (packet.getId()) {
            case 100 -> {
                GameObject player = (GameObject) packet.getObject();
                player.getDynamicBody().setPosX(100);
                player.getDynamicBody().setPosY(100);
                player.getDynamicBody().setSpeedX(0);
                player.getDynamicBody().setSpeedY(0);
                this.addNave(player);

            }
        }
    }

    @Override
    public void onConnectionAccept(String mac) {
        connections.put(mac, direction);

    }

    @Override
    public void onConnectionClosed(String mac) {

    }

    @Override
    public void onLookupUpdate(ArrayList<String> macs) {

    }

    public void sendGameObject(int wall, GameObject go) {
        for (String e : connections.keySet()) {
            if (connections.get(e) == wall) {
                controller.sendMessage(controller.createPacket(e, 100, go));
                System.out.println("message sent");
            }
        }
    }

    private void inicializarProtocolos() {
        controller.addNewProtocol(
                100,
                "Pasar GameObjects entre dispositivos",
                "GameObject"
        );
    }

    private void addNave(GameObject nave) {
        viewer.addPlayer(nave);
        this.naves.add(nave);
    }
}
