package Controller;

import View.GraphicEngine;

import java.awt.*;

public class SetUp extends Thread{

    final GraphicEngine graphics;
    private ConnectionController connController;
    private GamePhaseController phaseController;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int puntos = 1;

    public SetUp(GraphicEngine graphics, ConnectionController connController, GamePhaseController phaseController) {
        this.graphics = graphics;
        this.connController = connController;
        this.phaseController = phaseController;

        new Thread(new Runnable() {
            @Override
            public void run() {
                connController.connectAnotherScreen(graphics);
            }
        }).start();
    }

    @Override
    public void run() {
        super.run();
        while (phaseController.getGamePhase() == GamePhaseController.GamePhase.SETUP){
            try {
                synchronized (graphics) {
                    this.render(graphics.lock_canvas());
                    Thread.sleep(550);
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
        String frase = "WAITING FOR CONNECTIONS";
        puntos = (puntos+1) % 4;
        for (int i = 0; i < puntos; i++){
            frase += ".";
        }
        graphics.setColor(Color.black);
        graphics.fill3DRect(0,0,(int)screenSize.getWidth(), (int)screenSize.getHeight(),false);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Monospaced", Font.BOLD, 60));
        graphics.drawString(frase,(int)screenSize.getWidth()/2-400,(int)screenSize.getHeight()/2);
    }
}
