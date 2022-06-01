package Controller;

import static java.lang.Thread.sleep;

public abstract class GameLoop {

    //Attributes

    private static final double MILLIS_PER_FRAME = 1000d/60d;

    private static boolean running = true;

    //Main


    /**
     * Implementation of the Game Loop Pattern, consisting on prioritizing game Update() before Render(), making sure to render during CPU downtime.
     * It checks whether an update is due or not, defined by the amount of "lag" there is.
     * This lag is basically the time it takes between loop iterations, which, when higher than the expected MILLIS_PER_FRAME,
     * prioritizes updating in order to catch up (for this to work, Update() has to usually take less time than MILLIS_PER_FRAME), then renders the game and
     * resumes the operation.
     */
    public static void main(String[] args) {
        double previous = System.currentTimeMillis();
        double lag = 0.0;
        while (running) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            //User Input

            while (lag >= MILLIS_PER_FRAME) {
                //Update Method Here
                lag -= MILLIS_PER_FRAME;

            }

            /*Render Method Here. You should pass "lag / MILLIS_PER_FRAME" to it in order to calculate proper positions
                Note: "Lag / MILLIS_PER_FRAME" = Time since last frame, which essentially is how far into the next update tick we are
                Ex: Lag = 12 (ms), MILLIS_PER_FRAME = 16.67 (ms). We are about 3/4 of the game update tick, meaning any object that has motion
                will have moved 3/4 of the intended motion by then. This is because rendering can happen between game updates, causing stuttering if
                not coded accordingly with these values.
                We are basically "guessing" positions here, but since update ticks happen very rapidly, it's better to be a slight bit wrong than
                to not render the expected motion until the next frame.
            */
        }
    }

    //methods

    public static void stop() {
        running = false;
    }

}
