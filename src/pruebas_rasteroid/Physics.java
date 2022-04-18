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
        nave.setSpeedX(nave.getSpeedX() - friction);

        nave.setPosY(nave.getPosY() + nave.getSpeedY());
        nave.setSpeedY(nave.getSpeedY() - friction);

    }

    private void updatePositions() {
        for (Nave nave : naves) {
            calcularPosicionNave(nave);
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

}
