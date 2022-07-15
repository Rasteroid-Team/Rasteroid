package Controller;

import Model.Player;
import View.Objects.ObjectModels.Players.PlayerColors;
import View.Objects.ObjectModels.Players.PlayerModel;
import View.Resources;
import api.ApiService;
import communications.CommunicationController;
import communications.ProtocolDataPacket;

public class PlayerConnectionController {

    public CommunicationController comController;

    public PlayerConnectionController(CommunicationController comController) {
        this.comController = comController;
    }

    protected void recievePlayerMovement(ProtocolDataPacket packet){
        int[] movement = (int[]) packet.getObject();
        int strength = movement[0];
        int angle = movement[1];
        int i = 0;
        boolean found = false;

        while (i < GameControl.objects.size() && !found) {
            if (GameControl.objects.get(i) instanceof Player && ((Player)GameControl.objects.get(i)).getAssociatedMac() != null
                    && ((Player) GameControl.objects.get(i)).getAssociatedMac().equals(packet.getSourceID())) {

                ((Player) GameControl.objects.get(i)).getPlayerBody().setAngle(angle);
                ((Player) GameControl.objects.get(i)).getPlayerBody().setPotenciaAcceleracion(strength);
                if (strength == 0){
                    ((Player) GameControl.objects.get(i)).getPlayerBody().setAccelerando(false);
                } else {
                    ((Player) GameControl.objects.get(i)).getPlayerBody().setAccelerando(true);
                }
                found = true;
            }
            i++;
        }
    }

    protected void recievePlayerShootOrder(ProtocolDataPacket packet){
        int i = 0;
        boolean found = false;

        while (i < GameControl.objects.size() && !found) {
            if (GameControl.objects.get(i) instanceof Player && ((Player)GameControl.objects.get(i)).getAssociatedMac() != null
                    && ((Player) GameControl.objects.get(i)).getAssociatedMac().equals(packet.getSourceID())) {

                ((Player) GameControl.objects.get(i)).setShooting(true);
                found = true;
            }
            i++;
        }
    }

    protected void acceptNewPlayer(String mac){
        int i = 0;
        boolean found = false;
        int position = -1;

        synchronized (ConfigurationController.pcsInformation) {
            while (i < ConfigurationController.pcsInformation.length && !found) {
                if (ConfigurationController.pcsInformation[i][1] != null &&
                        ConfigurationController.pcsInformation[i][1].equals(mac)) {
                    found = true;
                } else if (ConfigurationController.pcsInformation[i][1] == null &&
                        position == -1) {
                    position = i;
                    ConfigurationController.pcsInformation[position][1] = mac;
                }
                i++;
            }
        }

        if (!found && position != -1) {
            //protocolo 155 = preguntar modelo nave
            //protocolo 156 = devuelve mensaje
            //Mandamos un mensaje para preguntar el modelo elegido al móvil
            comController.sendMessage(comController.createPacket(mac, 155,new String []
                    {ConfigurationController.pcsInformation[position][0], ""+(position+1)}));
            System.out.println("enviado");
            //GameControl.add_object(new Player(Resources.PLAYER_PHOENIX(), PlayerColors.cyan, mac));
        } else if (!found && position == -1){
            comController.disconnect(mac);
        }
    }

    protected void reconnectPlayer(String mac){
        int i = 0;
        boolean found = false;

        synchronized (ConfigurationController.pcsInformation) {
            while (i < ConfigurationController.pcsInformation.length && !found) {
                if (ConfigurationController.pcsInformation[i][1] != null &&
                        ConfigurationController.pcsInformation[i][1].equals(mac)) {
                    synchronized (ConfigurationController.disconnectedPlayers) {
                        ConfigurationController.disconnectedPlayers[i] = null;
                    }
                    if (GameControl.searchPlayer(mac)){
                        comController.sendMessage(comController.createPacket(mac, 180, comController.getLocalMAC()));
                    }else {
                        comController.sendBroadcastMessage(700, mac);
                    }
                    comController.sendMessage(comController.createPacket(mac, 550, null));
                    found = true;
                }
                i++;
            }
        }

        if (!found) {
            comController.disconnect(mac);
        }
    }

    protected void setPlayerModel(String modelID, String mac){
        PlayerModel model = ApiService.getPlayerModel(ApiService.getPlayerById(modelID));
        Player player = new Player(model, PlayerColors.cyan, mac);
        player.setModelID(modelID);
        GameControl.add_object(player);
    }



    public void sendStartingPcs(){
        synchronized (ConfigurationController.pcsInformation) {
            for (String[] pcInformation : ConfigurationController.pcsInformation) {
                if (pcInformation[1] != null && pcInformation[0].equals("this")){
                    comController.sendMessage(comController.createPacket(pcInformation[1],180, comController.getLocalMAC()));
                }
                else if (pcInformation[1] != null){
                    comController.sendMessage(comController.createPacket(pcInformation[1],180, pcInformation[0]));
                }
            }
        }
    }

}
