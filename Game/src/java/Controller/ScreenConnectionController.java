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
    private static String[] connections = new String[4];
    public CommunicationController comController;
    private int expectedConnections = 0;

    public ScreenConnectionController(CommunicationController comController) {
        this.comController = comController;
    }

    public static String[] getConnections() {
        return connections;
    }

    public void connectAnotherScreen() {
        int activo = 0;
        ConfigurationController p = new ConfigurationController();
        ArrayList<String> connect = p.connect();

        for (String checkingIp : connect){
            if (!checkingIp.equals("null")){
                this.expectedConnections++;
            }
        }

        while (activo < this.expectedConnections) {
            for (int i = 0; i < 4; i++) {
                try {
                    if (connections[i] == null) {
                        //connect screens
                        String ip = connect.get(i);
                        if (!ip.equals("null")) {
                            comController.connectToIp(ip);
                            connections[i] = "waiting";
                            activo ++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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

                found = true;
            }
            i++;
        }
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
