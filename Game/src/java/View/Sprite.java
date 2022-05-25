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
      image = ImageIO.read(new File(path));
      System.out.println("★ Sprite loaded:"+img_file.getAbsolutePath());
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

  public void draw_rotated(double radians, float posX, float posY, float angle, Graphics2D graphics)
  {

  }

}
