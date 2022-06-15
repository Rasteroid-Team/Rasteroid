package Controller;

import Model.GameObject;
import Model.Player;
import View.GraphicEngine;
import communications.CommunicationController;
import communications.ConnectionInterface;
import communications.ProtocolDataPacket;

import java.util.ArrayList;
import java.util.List;

public class ConnectionController implements ConnectionInterface {

    CommunicationController comController;
    ScreenConnectionController screenConnController;
    PlayerConnectionController playerConnController;

    private static List<GameObject> transferingList = new ArrayList<>();

    public ConnectionController(CommunicationController comController,
        ScreenConnectionController screenConnController, PlayerConnectionController playerConnController) {

        this.comController = comController;
        this.comController.addAllListeners(this);
        this.screenConnController = screenConnController;
        this.playerConnController = playerConnController;

        this.setupProtocols();
    }

    public void transferObjects(){
        synchronized (transferingList) {
            for (GameObject object : transferingList) {
                if (object instanceof Player) {
                    ((Player)object).setModel(null);
                    object.setStateList(null);
                    ((Player)object).repositionBeforeTransfer();
                    comController.sendMessage(comController.createPacket(object.getTransferingTo(), 150, object));
                    comController.sendMessage(comController.createPacket(((Player)object).getAssociatedMac(), 180,object.getTransferingTo()));
                }
            }
            transferingList.clear();
        }
    }

    public static void addTransferingObject(GameObject object)
    {
        synchronized (transferingList) {
            transferingList.add(object);
        }
        GameControl.remove_object(object);
    }

    public void connectAnotherScreen(int conPosition , GraphicEngine graphics){
        screenConnController.connectAnotherScreen(conPosition, graphics);
    }

    @Override
    public void onMessageReceived(ProtocolDataPacket packet) {
        switch (packet.getId()) {
            case 150 -> playerConnController.receivePlayer(packet);
            case 120 -> screenConnController.recieveConnectionPosition(packet);
            case 152 -> playerConnController.recievePlayerMovement(packet);
            case 151 -> playerConnController.recievePlayerShoot(packet);
        }
    }

    @Override
    public void onConnectionAccept(String mac) {

        if (comController.getConnectedDeviceType(mac) == CommunicationController.MVL){
            playerConnController.acceptNewPlayer(mac);
        }
        else {
            screenConnController.returnConnectionPosition(mac);
        }

    }

    @Override
    public void onConnectionClosed(String mac) {

    }

    @Override
    public void onLookupUpdate(ArrayList<String> macs) {

    }

    private void setupProtocols() {
        comController.addNewProtocol(
                120,
                "(Testing) Tell the new device the side from where it's coming the Player",
                "int"
        );

        comController.addNewProtocol(
                150,
                "Send Player between devices",
                "Object[]"
        );

        comController.addNewProtocol(
                152,
                "Move Player",
                "float[]"
        );

        comController.addNewProtocol(
                151,
                "Shoot Bullet",
                "null"
        );

        comController.addNewProtocol(
                180,
                "Send device MAC to a Mobile Connection",
                "String"
        );

    }
}
