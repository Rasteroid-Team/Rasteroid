package Model;

import communications.CommunicationController;

import java.util.ArrayList;

public class GameRules {

    private boolean teamsActivated;
    //0 mac jugador, 1 si viu o mort("alive" o "dead"), 2 kills.
    private ArrayList <String []> players;
    private String winnerMac;
    private String winnerName;
    private boolean finished;
    private CommunicationController communicationController;

    public GameRules(boolean teamsActivated, String[][] pcsInformation, CommunicationController communicationController) {
        this.teamsActivated = teamsActivated;
        this.communicationController = communicationController;
        this.players = new ArrayList<>();
        for (String[] pcInformation : pcsInformation){
            if (pcInformation[1] != null){
                players.add(new String[]{pcInformation[1],"alive","0"});
            }
        }
        this.finished = false;
    }

    public GameRules(boolean teamsActivated, CommunicationController communicationController) {
        this.teamsActivated = teamsActivated;
        this.communicationController = communicationController;
        this.finished = false;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean checkVictory(){
        boolean ended = false;

        if (!teamsActivated){
            ended = checkAllAgainstAllVictory();
        }

        return ended;
    }

    public synchronized boolean checkAllAgainstAllVictory(){
        boolean ended = false;
        int playersAlive = 0;
        int i = 0;

        while (i < players.size() && playersAlive < 2){
            if (players.get(i)[1].equals("alive")){
                playersAlive++;
                winnerMac = players.get(i)[0];
            }
            i++;
        }

        if (playersAlive < 2){
            ended = true;
        }
        return ended;
    }

    public synchronized void updatePlayerStatus(String mac, String state, boolean kill){
        boolean found = false;
        int i = 0;
        while (i < players.size() && !found){
            if (players.get(i)[0].equals(mac)){
                players.get(i)[1] = state;
                if (kill){
                    players.get(i)[2] = "" + (Integer.parseInt(players.get(i)[2]) + 1);
                }
                found = true;
            }
            i++;
        }

        if (state.equals("dead")){
            this.finished = this.checkVictory();

            if (this.finished){
                communicationController.sendBroadcastMessage(610,null);
                communicationController.sendMessage(communicationController.createPacket(winnerMac, 620, null));
            }
        }
    }

}
