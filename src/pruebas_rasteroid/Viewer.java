/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas_rasteroid;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * Esta es una prueba
 */
public class Viewer extends Canvas implements Runnable {

    private  BufferedImage ii;
    private ArrayList<GameObject> naves;

    public Viewer(ArrayList<GameObject> naves, int width, int height) {
        this.naves = naves;
        this.setSize(width, height);

    }

    public void run() {
        try {

            Thread.sleep(500);

            this.createBufferStrategy(2);
            while (true) {
                paint2();

                Thread.sleep(16);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void paint2() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            System.out.println("mal");
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (g == null) {
            System.out.println("mal 2");
            return;
        }

        drawRedSpace(g);
        pintarNaves(g);

        bs.show();
        g.dispose();

    }

    private void pintarNaves(Graphics g) {
        try {
            BufferedImage iii = ImageIO.read(new File("src\\resources\\shipGirada.png"));
            ii = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics g2 = ii.createGraphics();
            g2.drawImage(iii, 0, 0, 100, 100, null);
            g2.dispose();
        } catch (IOException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        for (int i = 0; i < naves.size(); i++) {
            BufferedImage imagenRotada = rotate(ii, naves.get(i).getAngle());
//            System.out.println("El anulo es " + naves.get(i).getAngle());

        
            g.drawImage(imagenRotada,(int) naves.get(i).getDynamicBody().getPosX(),(int) naves.get(i).getDynamicBody().getPosY(), imagenRotada.getWidth(),imagenRotada.getHeight(), this);
            
//            double rotationRequired = Math.toRadians (naves.get(i).getAngle());
//            double locationX = ii.getWidth() / 2;
//            double locationY = ii.getHeight() / 2;
//            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
//            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//
//            // Drawing the rotated image at the required drawing locations
//            g.drawImage(op.filter(ii, null), (int) naves.get(i).getPosX() ,(int) naves.get(i).getPosY(),100,100, null);
            
            
          //  g.drawImage(ii.getImage(),(int) naves.get(i).getDynamicBody().getPosX() ,(int) naves.get(i).getDynamicBody().getPosY(), 100,100, this);
            //g.drawRect((int) naves.get(i).getPosX(), (int) naves.get(i).getPosY(), 10, 10);
            // g.fillRect((int) naves.get(i).getPosX(), (int) naves.get(i).getPosY(), 10, 10);
            //g.drawRect(20, 20, 10, 10);
            // g.drawImage(naves.get(i), coordenadaX, coordenadaY, null);
        }
    }

    private void drawRedSpace(Graphics g) {
        int x1 = 0;
        int y1 = 0;
        int x2 = this.getWidth();
        int y2 = this.getHeight();
        g.setColor(Color.WHITE);
        g.fillRect(x1, y1, x2, y2);
    }
    
    
    public static BufferedImage rotate(BufferedImage image, double angle) {
            double radians = Math.toRadians(angle);
            System.out.println("El radian es " + radians);
            double sin = Math.abs(Math.sin(radians)), cos = Math.abs(Math.cos(radians));
            int w = image.getWidth(), h = image.getHeight();
            int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
            GraphicsConfiguration gc = getDefaultConfiguration();
            BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
            Graphics2D g = result.createGraphics();
            g.translate((neww - w) / 2, (newh - h) / 2);
            g.rotate(radians, w / 2, h / 2);
            g.drawRenderedImage(image, null);
            g.dispose();
            return result;
}

private static GraphicsConfiguration getDefaultConfiguration() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    return gd.getDefaultConfiguration();
}

}
