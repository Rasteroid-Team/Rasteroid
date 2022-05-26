package Model;

import Testing.InputAdapter;

public class DynamicBody extends Body{
    
    private float speedX, speedY;
    private float frictionCofficient = 0.05f;
    private int anguloFuerza = 0;
    private int potencia = 0;
    private boolean accelerando = false;

    //TO DO 
    
    //implement
    float speedLimit = 5;
    
    
    //create
    //private float[] descomponerFuerzas( angulo , fuerza ) { return [ velX, velY ] }
    
    
    //ESTO ES UN OBJETO MOVIBLE
    //MAP SIZE --> 1000, 700
    //HardCoded
    public DynamicBody() {
    
        speedX = (float) Math.random() * 5 - 2.5f;
        speedY = (float) Math.random() * 5 - 2.5f;
                
    }


    public DynamicBody(float posX, float posY, int angle, float radius, float speedX, float speedY, float frictionCofficient, float speedLimit) {
        super(posX, posY, angle, radius);
        this.speedX = speedX;
        this.speedY = speedY;
        this.frictionCofficient = frictionCofficient;
        this.speedLimit = speedLimit;
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

    public float getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(float speedLimit) {
        this.speedLimit = speedLimit;
    }

    public void move(float angulo, float potencia ) {

        //APLICAR FOLMULA PARA SACAR FUERZA DE X e Y - sin cos
        double anguloRad = Math.toRadians(angulo);
        float sin = potencia * (float)Math.sin(anguloRad);
        float cos = potencia * (float)Math.cos(anguloRad);
        
        float addSpeedX = sin * 1 / 90;
        float addSpeedY = cos * -1 / 90;
        
        //Aplicar veloidad

        speedY += addSpeedY;
        speedX += addSpeedX;
        
        
        float speedTotal = (float) Math.sqrt( (speedY*speedY) + (speedX*speedX));
        
        if ( speedTotal > speedLimit ) {
            
            float xNorm = speedX / speedTotal;
            float yNorm = speedY / speedTotal;
            
            speedX = xNorm * speedLimit;
            speedY = yNorm * speedLimit;
            
        }
        
        this.checkBorderCollisions();
        this.applyFriction();
        
        
    }
    
    
    public void checkBorderCollisions(){
        //CHECK COLISIONS WITH WALLS
        float posX = super.getPosX();
        float posY = super.getPosY();
        
        //IF COLISION RIGHT 
        if( ( posX + super.getRadius() )  > 1270 ) {
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
        //IF COLISION LEFT
        } else if ( posX - super.getRadius() < 0 ){
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
        
        //IF NOT COLISION
        } else {
            super.setPosX( super.getPosX() + speedX );
        }
        
        
        //IF COLISION UP
        if( posY - super.getRadius() < 0 ) {
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
        //IF COLISION BOT
        } else if ( posY + super.getRadius()  > 950 ){
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
        
        //IF NOT COLISION
        } else {
            super.setPosY( super.getPosY() + speedY );
        }
    }
    
   

    private void applyFriction() {
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

    @Override
    public void update(InputAdapter input) {
        super.update(input);
        accelerando =  input.get_active_keys()[0];
        if (input.get_active_keys()[1]) {
            anguloFuerza -= 5;
        }
        if (input.get_active_keys()[2]) {
            anguloFuerza += 5;
        }

        setAngle(anguloFuerza);
        if (!accelerando) {
            move(0,0);
            if( potencia > 0 )potencia-= 0.3;
        } else {
            potencia = 80;
            move(anguloFuerza, potencia);
        }
    }
    
    
    
}
