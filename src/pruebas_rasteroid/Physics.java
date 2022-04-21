package pruebas_rasteroid;

import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class Physics implements Runnable {

    ArrayList<Nave> naves = new ArrayList<>();

    public Physics(ArrayList<Nave> naves) {
        this.naves = naves;
    }

    public void addNave(Nave naveToAdd) {
        naves.add(naveToAdd);
    }

 
    
    private void calcularPosicionNave(Nave nave) {

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

    
    

    
    private void updatePositions() {
        for (Nave nave : naves) {
            calcularPosicionNave(nave);
            borderCollide(nave);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                updatePositions();
                sleep(16);
            } catch (InterruptedException ex) {
                System.out.println("El thread de fisicas ha sufrido un problema");
            }
        }
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
