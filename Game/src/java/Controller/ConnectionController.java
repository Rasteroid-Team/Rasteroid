package Controller;

import Model.Bullet;
import Model.GameObject;
import Model.Player;
import Testing.AvtomatV1;
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
    }

    public void transferObjects(){
        synchronized (transferingList) {
            for (GameObject object : transferingList) {
                if (object instanceof Player && !(object instanceof AvtomatV1)) {
                    ((Player)object).setModel(null);
                    object.setStateList(null);
                    object.getBody().repositionBeforeTransfer(object.getTransferingSide());
                    comController.sendMessage(comController.createPacket(object.getTransferingTo(), 150, object));
                    comController.sendMessage(comController.createPacket(((Player)object).getAssociatedMac(), 180,object.getTransferingTo()));
                }
                else if (object instanceof Bullet){
                    object.setStateList(null);
                    ((Bullet.BulletBody)((Bullet)object).getBody()).setPlayer_owner(null);
                    object.getBody().repositionBeforeTransfer(object.getTransferingSide());
                    comController.sendMessage(comController.createPacket(object.getTransferingTo(), 161, object));
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
        screenConnController.connectAnotherScreen(conPosition);
    }

    @Override
    public void onMessageReceived(ProtocolDataPacket packet) {
        switch (packet.getId()) {
            case 150 -> {
                screenConnController.receivePlayer(packet);
            }
            case 120 -> {
                screenConnController.recieveConnectionPosition(packet);
            }
            case 152 -> {
                playerConnController.recievePlayerMovement(packet);
            }
            case 151 -> {
                playerConnController.recievePlayerShootOrder(packet);
            }
            case 161 -> {
                screenConnController.receiveShoot(packet);
            }
            case 156 -> {
                System.out.println("Modelo recibido");
                playerConnController.setPlayerModel(packet.getObject().toString(), packet.getSourceID());
            }
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

}
