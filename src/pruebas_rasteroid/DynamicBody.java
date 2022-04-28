/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_rasteroid;

/**
 *
 * @author alfon
 */
public class DynamicBody extends Body{
    private float speedX, speedY;
    private float frictionCofficient = 0.05f;

    
    //1000, 700
    public DynamicBody() {
    
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

    public float getFrictionCofficient() {
        return frictionCofficient;
    }

    public void setFrictionCofficient(float friction) {
        this.frictionCofficient = friction;
    }

    public void move(){
        
    }
    
    private void move(Nave nave) {

        float friction = nave.getFriction();

        nave.setPosX(nave.getPosX() + nave.getSpeedX());
        nave.setPosY(nave.getPosY() + nave.getSpeedY());
        
        
////        if (nave.getSpeedX() > 0 ) nave.setSpeedX(nave.getSpeedX() - friction);
////        if (nave.getSpeedX() < 0 ) nave.setSpeedX(nave.getSpeedX() + friction);
////        
////        if (nave.getSpeedY() > 0 ) nave.setSpeedY(nave.getSpeedY() - friction);
////        if (nave.getSpeedY() < 0 ) nave.setSpeedY(nave.getSpeedY() + friction);
//        
        
    }
    

    
}
