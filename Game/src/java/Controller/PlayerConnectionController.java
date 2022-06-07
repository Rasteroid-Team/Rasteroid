package Controller;

import Model.Player;
import View.Objects.ObjectModels.Players.PlayerColors;
import View.Resources;
import communications.CommunicationController;
import communications.ProtocolDataPacket;

public class PlayerConnectionController {

    public CommunicationController comController;

    public PlayerConnectionController(CommunicationController comController) {
        this.comController = comController;
    }

    protected void receivePlayer(ProtocolDataPacket packet){
        Player player = (Player) packet.getObject();
        player.setModel(Resources.PLAYER_HR75());
        player.getModel().set_aura_color(player.getColor());
        player.setStateList(player.getModel().get_machine_states());
        player.repositionAfterTransfer();
        GameControl.add_object(player);
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

    protected void recievePlayerShoot(ProtocolDataPacket packet){
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

        while (i < GameControl.objects.size() && !found) {
            if (GameControl.objects.get(i) instanceof Player && ((Player)GameControl.objects.get(i)).getAssociatedMac() != null
                    && ((Player) GameControl.objects.get(i)).getAssociatedMac().equals(mac)) {
                found = true;
            }
            i++;
        }

        if (!found) {
            GameControl.add_object(new Player(Resources.PLAYER_HR75(), PlayerColors.cyan, mac));
        }
    }
}
