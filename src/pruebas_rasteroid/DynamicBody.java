package pruebas_rasteroid;

public class DynamicBody extends Body{
    private float speedX, speedY;
    private float frictionCofficient = 0.05f;

    
    //1000, 700
    public DynamicBody() {
    
        speedX = (float) Math.random() * 6 - 3;
        speedY = (float) Math.random() * 6 - 3;
        
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

    public void move( int angulo, int potencia ) {

       
        
    }
    

    
}
