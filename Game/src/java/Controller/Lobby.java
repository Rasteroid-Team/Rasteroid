package Controller;

import View.GraphicEngine;

import java.awt.*;

public class Lobby extends Thread{

    final GraphicEngine graphics;
    private GamePhaseController phaseController;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Lobby(GraphicEngine graphics, GamePhaseController phaseController) {
        this.graphics = graphics;
        this.phaseController = phaseController;
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
}
