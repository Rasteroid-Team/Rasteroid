package pruebas_rasteroid;

public class DynamicBody extends Body{
    
    private float speedX, speedY;
    private float frictionCofficient = 0.05f;

    //TO DO 
    
    //implement
    float speedLimit;
    
    
    //create
    //private float[] descomponerFuerzas( angulo , fuerza ) { return [ velX, velY ] }
    
    
    //ESTO ES UN OBJETO MOVIBLE
    //MAP SIZE --> 1000, 700
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

    public void move( float angulo, float potencia ) {

        float speedToAdd = (float) (potencia * 1) / 100;
        
        speedX += speedToAdd;
        speedY += speedToAdd;
        //UPDATE POSITION
        //CHECK COLISIONS WITH WALLS
        float centerX = getCenterX(super.getPosX());
        float centerY = getCenterX(super.getPosY());
        //IF COLISION RIGHT 
        if( (centerX - super.getRadius())  > 1000 ) {
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
        //IF COLISION LEFT
        } else if ( centerX - super.getRadius() < 0 ){
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
        
        //IF NOT COLISION
        } else {
            super.setPosX( super.getPosX() + speedX );
        }
        
        
        //IF COLISION UP 
        if( centerY + super.getRadius()  > 700 ) {
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
        //IF COLISION BOT
        } else if ( centerY + super.getRadius()  < 0 ){
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
        
        //IF NOT COLISION
        } else {
            super.setPosY( super.getPosY() + speedY );
        }
        
        //SPEED LOSE
        if ( speedX > 0.05 ) {
            speedX -=  frictionCofficient;
        } else if ( speedX < -0.05 ) {
            speedX += frictionCofficient;
        } else speedX = 0;
  
        if ( speedY > 0.05 ) {
            speedY -=  frictionCofficient;
        } else if ( speedY < -0.05 ) {
            speedY += frictionCofficient;
        } else speedY = 0;
        
    }
    
    
    public float getCenterX(float posX){
        return  posX + super.getRadius();
        
    }
    
    public float getCenterY(float posY){
        return  posY + super.getRadius();
        
    }
    
    
    
}
