package Controller;

public class GamePhaseController {

    private GamePhase gamePhase;

    public GamePhaseController() {
        gamePhase = GamePhase.SETUP;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public enum GamePhase {
        SETUP,
        LOBBY,
        IN_GAME,
        NONE
    }
}
