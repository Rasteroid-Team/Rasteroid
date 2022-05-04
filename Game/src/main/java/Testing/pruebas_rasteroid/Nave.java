package pruebas_rasteroid;

import java.awt.event.KeyEvent;

public class Nave {

    private float posX, posY;
    private float speedX, speedY;
    private int angle;
    private int acceleration;
    private float friction = 0.05f;

    
    //1000, 700
    public Nave() {
        posX = 100f + (int)(Math.random() * 400f );
        posY = 50f + (int)(Math.random() * 400f );
        speedX = -4f + (int)(Math.random() * 9f );
        speedY = -4f + (int)(Math.random() * 9f );
        angle = 0;
        acceleration = 0;
    }

    public void daleGas(KeyEvent e){
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            if (speedY < 0) {
                speedY -= 00.5;
            }else {
                speedY += 00.5;
            }
        
            if (speedX < 0) {
                speedX -= 00.5;
            }else {
                speedX += 00.5;
            }
        }
            
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

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

}
