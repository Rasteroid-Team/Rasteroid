package Controller;

import Model.Bullet;
import Model.GameObject;
import Model.Player;
import Testing.AvtomatV1;
import View.GraphicEngine;
import View.Interface.MainFrame;
import communications.CommunicationController;
import communications.ConnectionInterface;
import communications.ProtocolDataPacket;

import java.util.ArrayList;
import java.util.List;

public class ConnectionController implements ConnectionInterface {

    CommunicationController comController;
    ScreenConnectionController screenConnController;
    PlayerConnectionController playerConnController;
    GamePhaseController phaseController;


    private static List<GameObject> transferingList = new ArrayList<>();

    public ConnectionController(CommunicationController comController, ScreenConnectionController screenConnController,
            PlayerConnectionController playerConnController, GamePhaseController phaseController) {

        this.comController = comController;
        this.comController.addAllListeners(this);
        this.screenConnController = screenConnController;
        this.playerConnController = playerConnController;
        this.phaseController = phaseController;
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

    public void connectAnotherScreen(GraphicEngine graphics){
        screenConnController.connectAnotherScreen();
        if (ConfigurationController.mainFrame){
            while(phaseController.getGamePhase() == GamePhaseController.GamePhase.SETUP) {
                comController.sendBroadcastMessage(301, null);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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

                if (!ConfigurationController.mainFrame){
                    comController.sendMessage(comController.createPacket(ConfigurationController.macMainFrame,
                            501,null));
                } else {
                    if (phaseController.getGamePhase() == GamePhaseController.GamePhase.LOBBY) {
                        comController.sendBroadcastMessage(502,null);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        phaseController.setGamePhase(GamePhaseController.GamePhase.IN_GAME);
                    }
                }
            }
            case 301 -> {
                if (!ConfigurationController.mainFrame){
                    comController.sendMessage(comController.createPacket(packet.getSourceID(),302,ConfigurationController.pcNumber));
                    ConfigurationController.macMainFrame = packet.getSourceID();
                }
            }
            case 302 -> {
                System.out.println("Ready PC Added.");
                if (ConfigurationController.mainFrame){
                    if (ConfigurationController.addPcInformation(packet.getSourceID(), (int)packet.getObject())){
                        comController.sendBroadcastMessage(303,null);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        phaseController.setGamePhase(GamePhaseController.GamePhase.LOBBY);
                    }
                }
            }
            case 303 -> {
                if (!ConfigurationController.mainFrame){
                    phaseController.setGamePhase(GamePhaseController.GamePhase.LOBBY);
                }
            }
            case 501 -> {
                System.out.println("Starting game");
                if (ConfigurationController.mainFrame){
                    if (phaseController.getGamePhase() == GamePhaseController.GamePhase.LOBBY) {
                        comController.sendBroadcastMessage(502,null);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        phaseController.setGamePhase(GamePhaseController.GamePhase.IN_GAME);
                    }
                }
            }
            case 502 ->{
                phaseController.setGamePhase(GamePhaseController.GamePhase.IN_GAME);
            }
        }
    }

    @Override
    public void onConnectionAccept(String mac) {

        if (comController.getConnectedDeviceType(mac) == CommunicationController.MVL){
            if (ConfigurationController.mainFrame && phaseController.getGamePhase() == GamePhaseController.GamePhase.LOBBY) {
                playerConnController.acceptNewPlayer(mac);
            } else {
                comController.disconnect(mac);
            }
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
