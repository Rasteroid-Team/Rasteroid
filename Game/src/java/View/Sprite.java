package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

  private String path;
  private BufferedImage image;

  public Sprite(String res_path)
  {
    path = res_path;
  }

  public Sprite load()
  {
    try
    {
      File img_file = new File(path);
      System.out.println("★ Sprite loaded:"+img_file.getAbsolutePath());
      image = ImageIO.read(new File(path));
    }
    catch (IOException image_not_found)
    {
      image_not_found.printStackTrace();
    }
    return this;
  }

  public BufferedImage get_image()
  {
    return this.image;
  }

  public Sprite resize(int width, int height)
  {
    Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    BufferedImage new_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics2D graphics = new_image.createGraphics();
    graphics.drawImage(scaled, 0, 0, null);
    graphics.dispose();

    image = new_image;

    return this;
  }

  public BufferedImage rotate(double radians)
  {
    BufferedImage output_image;

    //iii = ImageIO.read(new File("Game/src/PruebasRasteroidFisicas/resources/shipGirada.png"));
    output_image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    Graphics g2 = output_image.createGraphics();
    g2.drawImage(image, 0, 0, 100, 100, null);
    g2.dispose();

    double orientation = radians;

    AffineTransform affineTransform = new AffineTransform();

    //Poner la posicion del affinetransform
    affineTransform.translate( 0, 0 );

    //rotar el affineTransform
    affineTransform.rotate( Math.toRadians( orientation ) );

    // esto es para que gire por el centro de la figura (como mide 100 x100, ponemos que gire a mitad de cada distancia)
    affineTransform.translate(-50, -50);

    //Cambiar el tamaño
    affineTransform.scale(1,1);

    return output_image;
  }

}
