package Controller;

import View.GraphicEngine;
import communications.CommunicationController;

import java.awt.*;

public class Lobby extends Thread{

    final GraphicEngine graphics;
    private GamePhaseController phaseController;
    private CommunicationController communicationController;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int countDown = 30;

    public Lobby(GraphicEngine graphics, GamePhaseController phaseController, CommunicationController communicationController) {
        this.graphics = graphics;
        this.phaseController = phaseController;
        this.communicationController = communicationController;
    }

    @Override
    public void run() {
        super.run();
        while (phaseController.getGamePhase() == GamePhaseController.GamePhase.LOBBY){
            try {
                synchronized (graphics) {
                    this.render(graphics.lock_canvas());
                    Thread.sleep(500);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (graphics != null) {
                    try {
                        graphics.unlock_and_post();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.countingDown();
        if (ConfigurationController.mainFrame) {
            this.communicationController.sendBroadcastMessage(550, null);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render (Graphics2D graphics){
        graphics.setColor(Color.black);
        graphics.fill3DRect(0,0,(int)screenSize.getWidth(), (int)screenSize.getHeight(),false);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Monospaced", Font.BOLD, 320));
        graphics.drawString(""+ConfigurationController.pcNumber,(int)screenSize.getWidth()/2-100,(int)screenSize.getHeight()/2);
        graphics.setFont(new Font("Monospaced", Font.BOLD, 60));
        graphics.drawString("WAITING PLAYERS",(int)screenSize.getWidth()/2-260,(int)screenSize.getHeight()/2+150);
    }

    public void countingDown (){
        while (countDown > -2) {
            try {
                synchronized (graphics) {
                    Graphics2D graphics2D = graphics.lock_canvas();
                    graphics2D.setColor(Color.black);
                    graphics2D.fill3DRect(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight(), false);
                    graphics2D.setColor(Color.white);
                    if (countDown != -1) {
                        graphics2D.setFont(new Font("Monospaced", Font.BOLD, 60));
                        graphics2D.drawString("STARTING IN", (int) screenSize.getWidth() / 2 - 200, (int) screenSize.getHeight() / 2 - 150);
                        graphics2D.setFont(new Font("Monospaced", Font.BOLD, 320));
                        graphics2D.drawString("" + this.countDown, (int) screenSize.getWidth() / 2 - 150, (int) screenSize.getHeight() / 2 + 150);
                        countDown--;
                        Thread.sleep(999);
                    } else {
                        graphics2D.setFont(new Font("Monospaced", Font.BOLD, 160));
                        graphics2D.drawString("STARTING", (int) screenSize.getWidth() / 2 - 360, (int) screenSize.getHeight() / 2);
                        countDown--;
                        Thread.sleep(500);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (graphics != null) {
                    try {
                        graphics.unlock_and_post();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
