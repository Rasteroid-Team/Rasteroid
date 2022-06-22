package Model;

import Testing.InputAdapter;


import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class Body implements Serializable {

    private float posX, posY, oldPosX, oldPosY;
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

    public float getOldPosX() {
        return oldPosX;
    }

    public void setOldPosX(float oldPosX) {
        this.oldPosX = oldPosX;
    }

    public float getOldPosY() {
        return oldPosY;
    }

    public void setOldPosY(float oldPosY) {
        this.oldPosY = oldPosY;
    }

    public int update(List<GameObject> objects) {
        return -1;
    }

    public void repositionBeforeTransfer(int transferingSide) {
        switch (transferingSide) {
            case 0:
            case 2:
                float x = this.getPosX();
                int totalWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
                this.setPosX((x * 100) / totalWidth);
                break;

            case 1:
            case 3:
                float y = this.getPosY();
                int totalHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
                this.setPosY((y * 100) / totalHeight);
                break;
        }
    }

    public void repositionAfterTransfer(int transferingSide) {
        switch (transferingSide) {
            case 0:
                this.setPosY(Toolkit.getDefaultToolkit().getScreenSize().height-70);
                this.setPosX((this.getPosX() * Toolkit.getDefaultToolkit().getScreenSize().width) / 100);
                break;

            case 1:
                this.setPosX(70);
                this.setPosY((this.getPosY() * Toolkit.getDefaultToolkit().getScreenSize().height) / 100);
                break;

            case 2:
                this.setPosY(70);
                this.setPosX((this.getPosX() * Toolkit.getDefaultToolkit().getScreenSize().width) / 100);
                break;

            case 3:
                this.setPosX(Toolkit.getDefaultToolkit().getScreenSize().width-70);
                this.setPosY((this.getPosY() * Toolkit.getDefaultToolkit().getScreenSize().height) / 100);
                break;
        }
    }
}
