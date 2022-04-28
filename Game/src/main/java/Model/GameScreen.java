package Model;

public abstract class GameScreen {

    private int width;
    private int height;
    private long gameStartMillis;
    Wall wall1;
    Wall wall2;
    Wall wall3;
    Wall wall4;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getGameStartMillis() {
        return gameStartMillis;
    }

    public void setGameStartMillis(long gameStartMillis) {
        this.gameStartMillis = gameStartMillis;
    }

    public void update(){}

    public GameObject addObject() {
        return null;
    }
}
