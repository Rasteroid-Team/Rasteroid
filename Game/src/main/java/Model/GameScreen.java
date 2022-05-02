package Model;

import java.util.ArrayList;

public abstract class GameScreen {

    private int width;
    private int height;
    private long gameStartMillis;
    private Wall[] wallArray = {new Wall(), new Wall(), new Wall(), new Wall()};
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private GameRules gameRules;

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

    public Wall[] getWallArray() {
        return wallArray;
    }

    public void setWallArray(Wall[] wallArray) {
        this.wallArray = wallArray;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public boolean removeGameObject(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }

    public Wall getWall(int index) {
        if (index < 0 || index > 3) {
            System.err.println("Out of bounds access on wallArray with index: " + index);
            return null;
        } else {
            return this.wallArray[index];
        }
    }

    public void update() {
    }

    public GameObject addObject() {
        return null;
    }
}
