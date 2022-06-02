package Controller;

import View.GraphicEngine;
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
}
