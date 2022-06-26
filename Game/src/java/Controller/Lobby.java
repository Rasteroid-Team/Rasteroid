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
        graphics.setFont(new Font("Monospaced", Font.BOLD, 60));
        graphics.drawString("PC",(int)screenSize.getWidth()/2-20,(int)screenSize.getHeight()/2-50);
        graphics.setFont(new Font("Monospaced", Font.BOLD, 120));
        graphics.drawString(""+ConfigurationController.pcNumber,(int)screenSize.getWidth()/2-40,(int)screenSize.getHeight()/2);
        graphics.setFont(new Font("Monospaced", Font.BOLD, 60));
        graphics.drawString("WAITING PLAYERS",(int)screenSize.getWidth()/2-40,(int)screenSize.getHeight()/2);
    }
}
