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
    private GamePhase phase = GamePhase.START;

    public GameEngine(GameControl game, GraphicEngine graphics, ConnectionController connController) {
        this.game = game;
        this.graphics = graphics;
        this.connController = connController;
        this.addMouseClickDetector();
    }

    public double get_average_ups() {
        return ups_average;
    }

    public double get_average_fps() {
        return fps_average;
    }

    public void init() {
        this.phase = GamePhase.START;
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
        while (is_running) {
            switch (phase) {
                case START -> {

                }
                case LOBBY -> {
                }
                case IN_GAME -> {
                    //Intentamos actualizar y renderizar los objetos de la clase Juego
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
                case NONE -> {
                    System.exit(404);
                }
            }
        }
    }

  public void set_max_ups(int ups) {
    ups_max = ups;
    ups_period = 1E+3 / ups_max;
  }

    private void addMouseClickDetector() {
        graphics.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //check if mouse position is inside one of the circles
                //if it is, show either the connection logic and a disconnect button
                //if the connection is live
                //or if it isn't show a menu asking for an ip address
                //check bottom mid circle

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int diameter = 25;

                if (e.getX() <= screenSize.getWidth() / 2 + diameter / 2 && e.getX() >= screenSize.getWidth() / 2 - diameter / 2 &&
                            e.getY() <= screenSize.getHeight() - 15 && e.getY() >= screenSize.getHeight() - diameter - 15) {
                    System.out.println("Pressed bottom");
                    connController.connectAnotherScreen(2, graphics);
                }//then check top mid
                else if (e.getX() <= screenSize.getWidth() / 2 + diameter / 2 && e.getX() >= screenSize.getWidth() / 2 - diameter / 2 &&
                                 e.getY() <= 15 + diameter && e.getY() >= 15) {
                    System.out.println("Pressed top");
                    connController.connectAnotherScreen(0, graphics);
                }//then check right
                else if (e.getX() <= screenSize.getWidth() - 15 && e.getX() >= screenSize.getWidth() - diameter - 15 &&
                                 e.getY() <= screenSize.getHeight() / 2 + diameter / 2 && e.getY() >= screenSize.getHeight() / 2 - diameter / 2) {
                    System.out.println("Pressed right");
                    connController.connectAnotherScreen(1, graphics);
                }//finally check left
                else if (e.getX() <= 0 + diameter + 15 && e.getX() >= 15 &&
                                 e.getY() <= screenSize.getHeight() / 2 + diameter / 2 && e.getY() >= screenSize.getHeight() / 2 - diameter / 2) {
                    System.out.println("Pressed left");
                    connController.connectAnotherScreen(3, graphics);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
    }

    private enum GamePhase {
        START,
        LOBBY,
        IN_GAME,
        NONE
    }
}
