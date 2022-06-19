package Controller;

import Model.Bullet;
import Model.GameObject;
import Model.GameRules;
import Model.Player;
import Testing.AvtomatV1;
import View.GraphicEngine;
import communications.CommunicationController;
import communications.ConnectionInterface;
import communications.ProtocolDataPacket;

import java.util.ArrayList;
import java.util.List;

public class ConnectionController implements ConnectionInterface {

    int contadorJugadores;
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
                this.notifyPlayerJoin();
            }
            case 300 -> {
                GameRules.numPlayers++;
                System.out.println("Added Player " + GameRules.numPlayers);
            }
            case 301 -> {
                GameRules.numPlayers--;
                System.out.println("Removed Player " + GameRules.numPlayers);
            }
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


    /**
     * Sends packet to all ConnectedPCs to update the amount of total players and updates this pc's total players.
     */
    private void notifyPlayerJoin() {
        //Gets all Mac Addresses (not LocalMac)
        for (String mac : comController.getConnectedMacs()) {
            //Checks if Mac is PC
            if (comController.getConnectedDeviceType(mac) == CommunicationController.PC) {
                comController.sendMessage(comController.createPacket(mac, 300, null));

            }
        }
        GameRules.numPlayers++;
        System.out.println("Added Local Player " + GameRules.numPlayers);

    }

    /**
     * Sends packet to all ConnectedPCs to update the amount of total players and updates this pc's total players.
     */
    public void notifyPlayerDeath() {
        //Gets all Mac Addresses (not LocalMac)
        for (String mac : comController.getConnectedMacs()) {
            //Checks if Mac is PC
            if (comController.getConnectedDeviceType(mac) == CommunicationController.PC) {
                comController.sendMessage(comController.createPacket(mac, 301, null));

            }
        }
        GameRules.numPlayers--;
        System.out.println("Added Local Player " + GameRules.numPlayers);

    }
}
