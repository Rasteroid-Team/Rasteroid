package Controller;

import Model.Bullet;
import Model.Player;
import View.GraphicEngine;
import View.Resources;
import api.ApiService;
import communications.CommunicationController;
import communications.ProtocolDataPacket;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ScreenConnectionController {

    // 0 up, 1 right, 2 down, 3 left
    private static String [] connections = new String[4];
    public CommunicationController comController;
    private int expectedConnections = 0;
    private ConfigurationController p;

    public ScreenConnectionController(CommunicationController comController) {
        this.comController = comController;
        if (this.checkReady()) {
            comController.sendBroadcastMessage(302, null);
            GameControl.readyPCs++;
            if (GameControl.readyPCs == GameControl.expectedPCs) {
                GameEngine.phase = GameEngine.GamePhase.LOBBY;
            }
        }
    }

    public static String[] getConnections() {
        return connections;
    }

    public void connectAnotherScreen() {
        boolean activo = true;
        while (activo) {
            for (int i = 0; i < 4; i++) {
                this.p = new ConfigurationController();
                try {
                    if (connections[i] == null) {
                        //connect screens
                        ArrayList<String> connect = new ArrayList<>();
                        connect = p.connect();
                        if (i == 0) {
                            String ip = connect.get(0);
                            if (!ip.equals("null")) {
                                comController.connectToIp(ip);
                                connections[i] = "waiting";
                                activo = false;
                            } else {
                                activo = false;
                            }
                        }
                        if (i == 1) {
                            String ip = connect.get(1);
                            if (!ip.equals("null")) {
                                comController.connectToIp(ip);
                                connections[i] = "waiting";
                                activo = false;
                            } else {
                                activo = false;
                            }
                        }
                        if (i == 2) {
                            String ip = connect.get(2);
                            if (!ip.equals("null")) {
                                comController.connectToIp(ip);
                                connections[i] = "waiting";
                                activo = false;
                            } else {
                                activo = false;
                            }
                        }
                        if (i == 3) {
                            String ip = connect.get(3);

                            if (!ip.equals("null")) {
                                comController.connectToIp(ip);
                                connections[i] = "waiting";
                                activo = false;
                            } else {
                                activo = false;
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    activo = true;
                }
            }
        }
        this.expectedConnections = this.calculateExpectedConnections();
    }

    private int calculateExpectedConnections() {
        ArrayList<String> ipArrayList = this.p.connect();
        int expectedConnections = 0;

        for (String ip : ipArrayList) {
            if (!(ip.equals("null"))) {
                expectedConnections++;

            }
        }

        return expectedConnections;
    }

    //Solo para test, despues hacer automatico
    protected void recieveConnectionPosition(ProtocolDataPacket packet) {
        int direction = (int) packet.getObject();
        //Gira la direccion pasada para que sea correcta
        direction = (direction < 2) ? direction + 2 : direction - 2;
        connections[direction] = packet.getSourceID();
    }

    protected void returnConnectionPosition(String mac) {
        int i = 0;
        boolean found = false;
        while (!found && i < connections.length) {
            if (connections[i] != null && connections[i].equals("waiting")) {
                connections[i] = mac;
                comController.sendMessage(comController.createPacket(mac, 120, i));

                if (this.checkReady()) {
                    comController.sendBroadcastMessage(302, null);
                    GameControl.readyPCs++;
                    if (GameControl.readyPCs == GameControl.expectedPCs) {
                        GameEngine.phase = GameEngine.GamePhase.LOBBY;
                    }
                }

                found = true;
            }
            i++;
        }
    }

    private boolean checkReady() {
        boolean ready = false;
        int readyConnections = 0;

        for (String connection : connections) {
            if (connection != null && !connection.equals("waiting")) {
                readyConnections++;
            }
        }

        if (readyConnections == this.expectedConnections) {
            ready = true;
        }

        return ready;
    }

    protected void receivePlayer(ProtocolDataPacket packet) {
        Player player = (Player) packet.getObject();
        player.setLast_fire(System.currentTimeMillis() - 1000);
        player.setModel(ApiService.getPlayerModel(ApiService.getPlayerById(player.getModelID())));
        player.getModel().set_aura_color(player.getColor());
        player.setStateList(player.getModel().get_machine_states());
        player.getBody().repositionAfterTransfer(player.getTransferingSide());
        GameControl.add_object(player);
    }

    protected void receiveShoot(ProtocolDataPacket packet) {
        Bullet bullet = (Bullet) packet.getObject();
        bullet.setStateList(Resources.BULLET_YELLOW().get_machine_states());
        bullet.getBody().repositionAfterTransfer(bullet.getTransferingSide());
        GameControl.add_object(bullet);
    }
}
