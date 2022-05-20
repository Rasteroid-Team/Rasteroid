package PruebasRasteroidFisicas.pruebas_rasteroid;

import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.PlayerModels.PlayerModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicBody extends Body{
    
    private float speedX, speedY;
    private float frictionCofficient = 0.05f;

    ////status
    // edit jose: Lo he movido aqui por que veía que
    //            que hacía referencias constantes al
    //            dynamic body y para optimizar las
    //            llamadas, lo encontraba necesario.
    private int anguloFuerza = 0;
    private int potencia = 0;
    private boolean accelerando = false;

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

    @Override
    public void render(Graphics2D graphics) {

        BufferedImage output_image;

        //iii = ImageIO.read(new File("Game/src/PruebasRasteroidFisicas/resources/shipGirada.png"));
        output_image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = output_image.createGraphics();
        g2.drawImage(current_state.get_animation().get_current_sprite().get_image(), 0, 0, 100, 100, null);
        g2.dispose();

        float posX = getPosX();
        float posY = getPosY();
        float orientation = getAngle();

        AffineTransform affineTransform = new AffineTransform();

        //Poner la posicion del affinetransform
        affineTransform.translate( posX, posY );

        //rotar el affineTransform
        affineTransform.rotate( Math.toRadians( orientation ) );

        // esto es para que gire por el centro de la figura (como mide 100 x100, ponemos que gire a mitad de cada distancia)
        affineTransform.translate(-50, -50);

        //Cambiar el tamaño
        affineTransform.scale(1,1);

        graphics.drawImage(output_image, affineTransform, null);
    }

    ////

    //TO DO
    
    //implement
    float speedLimit = 5;
    
    
    //create
    //private float[] descomponerFuerzas( angulo , fuerza ) { return [ velX, velY ] }
    
    
    //ESTO ES UN OBJETO MOVIBLE
    //MAP SIZE --> 1000, 700
    public DynamicBody() {
    
        speedX = (float) Math.random() * 5 - 2.5f;
        speedY = (float) Math.random() * 5 - 2.5f;
                
    }

    public DynamicBody(PlayerModel model) {
        super(model);
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
    
    
    
}
