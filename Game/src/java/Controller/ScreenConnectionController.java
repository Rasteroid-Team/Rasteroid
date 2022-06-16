package Controller;

import Model.Bullet;
import Model.Player;
import View.GraphicEngine;
import View.Resources;
import api.ApiService;
import communications.CommunicationController;
import communications.ProtocolDataPacket;

import javax.swing.*;
import java.util.ArrayList;

public class ScreenConnectionController {

    // 0 up, 1 right, 2 down, 3 left
    private static String [] connections = new String[4];
    public CommunicationController comController;

    public ScreenConnectionController(CommunicationController comController) {
        this.comController = comController;
    }

    public static String[] getConnections() {
        return connections;
    }

    public void connectAnotherScreen(int conPosition){
        ConfigurationController p = new ConfigurationController();
        if(connections[conPosition] == null){
            //connect screens
            ArrayList<String> connect = new ArrayList<>();
            connect = p.connect();
            if (conPosition == 0){
                String ip = connect.get(0);
                if(ip != null && !ip.isEmpty()){
                    comController.connectToIp(ip);
                    connections[conPosition] = "waiting";
                }
            }
            if (conPosition == 1){
                String ip = connect.get(1);
                if(ip != null && !ip.isEmpty()){
                    comController.connectToIp(ip);
                    connections[conPosition] = "waiting";
                }
            }
            if (conPosition == 2){
                String ip = connect.get(2);
                if(ip != null && !ip.isEmpty()){
                    comController.connectToIp(ip);
                    connections[conPosition] = "waiting";
                }
            }
            if (conPosition == 3){
                String ip = connect.get(3);
                if(ip != null && !ip.isEmpty()){
                    comController.connectToIp(ip);
                    connections[conPosition] = "waiting";
                }
            }
        }else{
            //show panel with connection info and disconnect button
            //ipLabel.setText(main.getInfo(3));
            //dataDialog.setVisible(true);
        }
    }

    //Solo para test, despues hacer automatico
    protected void recieveConnectionPosition(ProtocolDataPacket packet){
        int direction = (int) packet.getObject();
        //Gira la direccion pasada para que sea correcta
        direction = (direction < 2) ? direction + 2 : direction - 2;
        connections[direction] = packet.getSourceID();
    }

    protected void returnConnectionPosition(String mac){
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

    protected void receivePlayer(ProtocolDataPacket packet){
        Player player = (Player) packet.getObject();
        player.setLast_fire(System.currentTimeMillis()-1000);
        player.setModel(ApiService.getPlayerModel(ApiService.getPlayerById(player.getModelID())));
        player.getModel().set_aura_color(player.getColor());
        player.setStateList(player.getModel().get_machine_states());
        player.getBody().repositionAfterTransfer(player.getTransferingSide());
        GameControl.add_object(player);
    }

    protected void receiveShoot(ProtocolDataPacket packet){
        Bullet bullet = (Bullet) packet.getObject();
        bullet.setStateList(Resources.BULLET_YELLOW().get_machine_states());
        bullet.getBody().repositionAfterTransfer(bullet.getTransferingSide());
        GameControl.add_object(bullet);
    }
}
