package Model;

import Testing.InputAdapter;

import java.io.Serializable;
import java.util.List;

public class Body implements Serializable {

    private float posX, posY;
    private int angle;
    private float radius = 50;
    
    //ESTO ES UN OBJETO ESTATICO
    //MAP SIZE --> 1000, 700

    //Hardcoded
    public Body() {
        posX = 100f + (int) (Math.random() * 400f);
        posY = 50f + (int) (Math.random() * 400f);
        angle = 0;
    }

    public Body(float posX, float posY, int angle, float radius) {
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        this.radius = radius;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int update(List<GameObject> objects) {
        return -1;
    }

}
