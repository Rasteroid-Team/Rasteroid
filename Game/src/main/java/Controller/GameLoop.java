package Controller;

import static java.lang.Thread.sleep;

public abstract class GameLoop {

    //Attributes

    private static final double MILLIS_PER_FRAME = 1000d/60d;

    private static boolean running = true;

    //Main

    public static void main(String[] args) throws InterruptedException {
        while (running) {
            double start = System.currentTimeMillis();
            //GameScreen.update(PlayerConnectionController.copyPlayerInputs());
            //Viewer.update();
            sleep((long) (start + MILLIS_PER_FRAME - System.currentTimeMillis()));
        }
    }

    //methods

    public static void stop() {
        running = false;
    }

}
