/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.awt.event.KeyEvent;

/**
 *
 * @author alfon
 */
public class Body {
    
    private float posX, posY;
    private int angle;
    private int acceleration;

    
    //1000, 700
    public Body() {
        posX = 100f + (int)(Math.random() * 400f );
        posY = 50f + (int)(Math.random() * 400f );
        angle = 0;
        acceleration = 0;
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

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

        private float getRadius(Nave nave){
        float radius = 0f;
//        int w = nave.getWidth();
//        int h = nave.getHeight();
//        if(w >= h){
//            radius = w/2;
//
//        }else {
//            radius = h/2;
//
//        }

        radius = 2.5f;
        
        return radius;
        
    }
    
       
       
        private void borderCollide(Nave nave){
        
        int width = 975;
        int height = 650;
        float rad = getRadius(nave);
        
        if(((nave.getPosX() - rad) <= 0) || ((nave.getPosX() + rad) >= width)){
            nave.setSpeedX(nave.getSpeedX() * -0.95f);
        } 
        
        if(((nave.getPosY() - rad) <= 0) || ((nave.getPosY() + rad) >= height)){
            nave.setSpeedY(nave.getSpeedY() * -0.95f);
        }
        
        
    }

}
