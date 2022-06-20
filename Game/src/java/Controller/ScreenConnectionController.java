package Controller;

import Model.Bullet;
import Model.Player;
import View.GraphicEngine;
import View.Resources;
import api.ApiService;
import communications.CommunicationController;
import communications.ProtocolDataPacket;

import javax.swing.*;

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

    public void connectAnotherScreen(int conPosition, GraphicEngine graphics){
        if(connections[conPosition] == null){
            //show a panel asking for an ip
            String ip = JOptionPane.showInputDialog(graphics,
                    "Ip to connect to?", null);
            if(ip != null && !ip.isEmpty()){
                comController.connectToIp(ip);
                connections[conPosition] = "waiting";
                comController.sendBroadcastMessage(123123, null);


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
