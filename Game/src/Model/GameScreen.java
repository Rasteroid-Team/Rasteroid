package Model;

import java.util.ArrayList;

public abstract class GameScreen {

    static private int width;
    static private int height;
    static private long gameStartMillis;
    static private Wall[] walls = new Wall[4];
    static {for (int i = 0; i++<4;) walls[i] = new Wall(i);}
    static private ArrayList<GameObject> gameObjects = new ArrayList<>();
    static private GameRules gameRules;

    static public int getWidth() {
        return width;
    }

    static public void setWidth(int width) {
        GameScreen.width = width;
    }

    static public int getHeight() {
        return height;
    }

    static public void setHeight(int height) {
        GameScreen.height = height;
    }

    static public long getGameStartMillis() {
        return gameStartMillis;
    }

    static public void setGameStartMillis(long gameStartMillis) {
        GameScreen.gameStartMillis = gameStartMillis;
    }

    static public Wall[] getWalls() {
        return walls;
    }

    static public void setWalls(Wall[] walls) {
        GameScreen.walls = walls;
    }

    static public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

     static public void setGameObjects(ArrayList<GameObject> gameObjects) {
         GameScreen.gameObjects = gameObjects;
    }

    static public GameRules getGameRules() {
        return gameRules;
    }

    static public void setGameRules(GameRules gameRules) {
        GameScreen.gameRules = gameRules;
    }

    static public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    static public boolean removeGameObject(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }

    static public Wall getWall(int index) {
        if (index < 0 || index > 3) {
            System.err.println("Out of bounds access on wallArray with index: " + index);
            return null;
        } else {
            return GameScreen.walls[index];
        }
    }

    static public void update() {
    }

    static public GameObject addObject() {
        return null;
    }
}
