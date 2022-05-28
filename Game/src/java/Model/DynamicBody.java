package Model;

import java.util.List;

public class DynamicBody extends Body{
    
    protected float speedX, speedY;
    protected float frictionCofficient = 0.05f;
    protected int anguloFuerza = 0;
    protected int potencia = 0;
    protected int potencia_aceleracion;
    protected boolean accelerando = false;

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
    
    public void move( float angulo, float potencia ) {

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
        
        this.applyFriction();
        
        
    }
    
    
    public void checkBorderCollisions(Map map_object){
        //CHECK COLISIONS WITH WALLS
        float posX = super.getPosX();
        float posY = super.getPosY();
        
        //IF COLISION RIGHT 
        if( ( posX + super.getRadius() )  >= map_object.get_right_border().getX() ) {
            setPosX((float) (map_object.get_right_border().getX()-1-getRadius()));
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
            collision(map_object);
        //IF COLISION LEFT
        } else if ( posX - super.getRadius() <= 0 ){
            setPosX(1+getRadius());
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
            collision(map_object);
        
        //IF NOT COLISION
        } else {
            super.setPosX( super.getPosX() + speedX );
        }
        
        
        //IF COLISION UP
        if( posY - super.getRadius() <= 0 ) {
            setPosY(1+getRadius());
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
            collision(map_object);
            //IF COLISION BOTTOM
        } else if ( posY + super.getRadius()  >= map_object.get_bottom_border().getY() ){
            setPosY((float) (map_object.get_bottom_border().getY()-1-getRadius()));
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
            collision(map_object);

            //IF NOT COLISION
        } else {
            super.setPosY( super.getPosY() + speedY );
        }
    }

    private void checkObjectCollision(GameObject object)
    {
        float obj_posx = object.getBody().getPosX();
        float obj_posy = object.getBody().getPosY();
        double h;
        {
            //calculate distance between objects
            float c1 = obj_posy - getPosY();
            float c2 = obj_posx - getPosX();
            h = (float) Math.sqrt(Math.pow(c1,2) + Math.pow(c2,2));
        }
        if (h-object.getBody().getRadius() <= getRadius())
        {
            collision(object);
        }
    }

    /**Set consequence in every object body class*/
    public void collision(GameObject object) {}

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
    public void update(List<GameObject> objects) {
        super.update(objects);
        for (GameObject object: objects)
        {
            if (object instanceof Map)
            {
                this.checkBorderCollisions((Map) object);
            }
            else
            {
                this.checkObjectCollision(object);
            }
        }

        if (!accelerando) {
            move(0,0);
            if( potencia > 0 )potencia-= 0.3;
        } else {
            potencia = potencia_aceleracion;
            move(anguloFuerza, potencia);
        }
    }

    public boolean is_accelerating()
    {
        return accelerando;
    }

    @Override
    public void setAngle(int angle) {
        super.setAngle(angle);
        anguloFuerza = angle;
    }
}
