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
        
        super.setPosX( super.getPosX() + speedX );
        super.setPosY( super.getPosY() + speedY );
        
        if ( speedX > 0.1 ) {
            speedX -=  frictionCofficient;
        } else if ( speedX < -0.1 ) {
            speedX += frictionCofficient;
        } else speedX = 0;
        
        if ( speedY > 0.1 ) {
            speedY -=  frictionCofficient;
        } else if ( speedY < -0.1 ) {
            speedY += frictionCofficient;
        } else speedY = 0;
        
    }

    public void move( int angulo, int potencia ) {

        super.setPosX( super.getPosX() + speedX );
        super.setPosY( super.getPosY() + speedY );
        
        if ( speedX > 0.1 ) {
            speedX -=  frictionCofficient;
        } else if ( speedX < -0.1 ) {
            speedX += frictionCofficient;
        } else speedX = 0;
        
        if ( speedY > 0.1 ) {
            speedY -=  frictionCofficient;
        } else if ( speedY < -0.1 ) {
            speedY += frictionCofficient;
        } else speedY = 0;
        
    }
    

    
}
