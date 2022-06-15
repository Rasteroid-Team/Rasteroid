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

        this.setupProtocols();
    }

    public void transferObjects() {
        synchronized (transferingList) {
            for (GameObject object : transferingList) {
                if (object instanceof Player && !(object instanceof AvtomatV1)) {
                    ((Player) object).setModel(null);
                    object.setStateList(null);
                    object.getBody().repositionBeforeTransfer(object.getTransferingSide());
                    comController.sendMessage(comController.createPacket(object.getTransferingTo(), 150, object));
                    comController.sendMessage(comController.createPacket(((Player) object).getAssociatedMac(), 180, object.getTransferingTo()));
                } else if (object instanceof Bullet) {
                    object.setStateList(null);
                    ((Bullet.BulletBody) ((Bullet) object).getBody()).setPlayer_owner(null);
                    object.getBody().repositionBeforeTransfer(object.getTransferingSide());
                    comController.sendMessage(comController.createPacket(object.getTransferingTo(), 161, object));
                }
            }
            transferingList.clear();
        }
    }

    public static void addTransferingObject(GameObject object) {
        synchronized (transferingList) {
            transferingList.add(object);
        }
        GameControl.remove_object(object);
    }

    public void connectAnotherScreen(int conPosition, GraphicEngine graphics) {
        screenConnController.connectAnotherScreen(conPosition, graphics);
    }

    @Override
    public void onMessageReceived(ProtocolDataPacket packet) {
        switch (packet.getId()) {
            case 120 -> screenConnController.recieveConnectionPosition(packet);
            case 150 -> screenConnController.receivePlayer(packet);
            case 151 -> playerConnController.recievePlayerShootOrder(packet);
            case 152 -> playerConnController.recievePlayerMovement(packet);
            case 156 -> {
                System.out.println("Modelo recibido");
                playerConnController.setPlayerModel(packet.getObject().toString(), packet.getSourceID());
            }
            case 161 -> screenConnController.receiveShoot(packet);
        }
    }


    @Override
    public void onConnectionAccept(String mac) {

        if (comController.getConnectedDeviceType(mac) == CommunicationController.MVL) {
            playerConnController.acceptNewPlayer(mac);
        } else {
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
                151,
                "Shoot Bullet",
                "null"
        );

        comController.addNewProtocol(
                152,
                "Move Player",
                "float[]"
        );

        comController.addNewProtocol(
                155,
                "Request for Protocol 156. Asks for the model type the player has chosen",
                "null"
        );

        comController.addNewProtocol(
                156,
                "Receive Player's chosen model",
                "String"
        );

        comController.addNewProtocol(
                161,
                "Send Bullet",
                "Bullet"
        );

        comController.addNewProtocol(
                180,
                "Send device MAC to a Mobile Connection",
                "String"
        );

        comController.addNewProtocol(
                200,
                "Send Game Over to MVL",
                "null"
        );
    }
}
