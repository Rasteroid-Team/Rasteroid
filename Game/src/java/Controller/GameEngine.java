package Controller;


import Model.GameObject;
import Model.Player;
import Testing.InputAdapter;
import View.GraphicEngine;
import View.Objects.ObjectModels.Players.PlayerColors;
import View.Resources;
import communications.CommunicationController;
import communications.ConnectionInterface;
import communications.ProtocolDataPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GameEngine extends Thread {

    private static double ups_max = 60.0;
    private static double ups_period = 1E+3 / ups_max;
    final GraphicEngine graphics;
    private final GameControl game;
    private boolean is_running = false;
    private double ups_average;
    private double fps_average;
    private ConnectionController connController;
    private GamePhaseController phaseController;
    private CommunicationController communicationController;

    public GameEngine(GameControl game, GraphicEngine graphics, ConnectionController connController,
              GamePhaseController phaseController, CommunicationController communicationController) {
        this.game = game;
        this.graphics = graphics;
        this.connController = connController;
        this.phaseController = phaseController;
        this.communicationController = communicationController;
    }

    public double get_average_ups() {
        return ups_average;
    }

    public double get_average_fps() {
        return fps_average;
    }

    public void init() {
        //if (phase == null) phase = GamePhase.SETUP;
        is_running = true;
        start();
    }

    @Override
    public void run() {
        super.run();
        //Declaramos las variables de tiempo y ciclo de vida
        int update_count = 0;
        int frame_count = 0;
        long init_time;
        long current_time;
        long sleep_time;

        //El loop del juego
        init_time = System.currentTimeMillis();
        while (is_running && !GameControl.gameRules.isFinished()) {
            try {
                synchronized (graphics) {
                    game.update();
                    update_count++;
                    game.render(graphics.lock_canvas());
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (graphics != null) {
                    try {
                        graphics.unlock_and_post();
                        frame_count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //Se transfieren los objetos
            connController.transferObjects();

            //Pausamos el loop para que no exceda los UPS objetivo
            current_time = System.currentTimeMillis() - init_time;
            sleep_time = (long) (update_count * ups_period - current_time);
            if (sleep_time > 0) {
                try {
                    sleep(sleep_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Nos saltamos varios frames para mantener los UPS objetivo
            while (sleep_time < 0 && update_count < ups_max - 1) {
                game.update();
                update_count++;
                current_time = System.currentTimeMillis() - init_time;
                sleep_time = (long) (update_count * ups_period - current_time);
            }

            //Calculamos el UPS y FPS objetivo
            current_time = System.currentTimeMillis() - init_time;
            if (current_time >= 1000) {
                ups_average = update_count / (1E-3 * current_time);
                fps_average = frame_count / (1E-3 * current_time);
                update_count = 0;
                frame_count = 0;
                init_time = System.currentTimeMillis();
            }
        }

        this.showWinner();
        if (ConfigurationController.mainFrame) {
            communicationController.sendBroadcastMessage(630, null);
        }
    }

    public void set_max_ups(int ups) {
        ups_max = ups;
        ups_period = 1E+3 / ups_max;
    }

    public void showWinner(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int i = 0;
        while (phaseController.getGamePhase() == GamePhaseController.GamePhase.IN_GAME){
            try {
                synchronized (graphics) {
                    this.renderEndGameScreen(graphics.lock_canvas(), screenSize);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (graphics != null) {
                    try {
                        graphics.unlock_and_post();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            if (i > 3){
                phaseController.setGamePhase(GamePhaseController.GamePhase.LOBBY);
            }
        }
    }

    public void renderEndGameScreen(Graphics2D graphics2D, Dimension screenSize){
        graphics2D.setColor(Color.black);
        graphics2D.fill3DRect(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight(), false);
        graphics2D.setColor(Color.white);

        if (GameControl.gameRules.getWinnerName() == null) {
            graphics2D.setFont(new Font("Monospaced", Font.BOLD, 160));
            graphics2D.drawString("GAME ENDED", (int) screenSize.getWidth() / 2 - 300, (int) screenSize.getHeight() / 2);
        } else {
            graphics2D.setFont(new Font("Monospaced", Font.BOLD, 100));
            graphics2D.drawString("CONGRATULATIONS", (int) screenSize.getWidth() / 2 - 300, (int) screenSize.getHeight() / 2 - 100);
            graphics2D.setFont(new Font("Monospaced", Font.BOLD, 160));
            graphics2D.drawString(GameControl.gameRules.getWinnerName(),
                    (int) screenSize.getWidth() / 2 - 350, (int) screenSize.getHeight() / 2 + 100);
        }
    }
}
