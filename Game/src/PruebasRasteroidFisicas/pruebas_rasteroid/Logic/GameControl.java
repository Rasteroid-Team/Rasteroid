package PruebasRasteroidFisicas.pruebas_rasteroid.Logic;

import PruebasRasteroidFisicas.pruebas_rasteroid.GameObject;
import PruebasRasteroidFisicas.pruebas_rasteroid.InputAdapter;
import PruebasRasteroidFisicas.pruebas_rasteroid.Viewer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameControl {

  static BufferedImage iii;
  static BufferedImage ii = null;

  static {
    try {
      iii = ImageIO.read(new File("Game/src/PruebasRasteroidFisicas/resources/shipGirada.png"));
      ii = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
      Graphics g2 = ii.createGraphics();
      g2.drawImage(iii, 0, 0, 100, 100, null);
      g2.dispose();
    } catch (IOException ex) {
      Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }




  //objects
  private final List<GameObject> objects = new ArrayList<>();

  //for print engine values
  private boolean debug_mode;
  private GameEngine game_engine;
  DecimalFormat two_decimals = new DecimalFormat("#.00");

  //player input
  InputAdapter input;


  public void update()
  {
    //TODO: update here your game objects
    for (GameObject object : objects)
    {
      object.getDynamicBody().update(input);

    }
  }

  public void render(Graphics2D graphics)
  {
    //TODO: draw here your game objects
    for (GameObject object : objects) {
      object.getDynamicBody().render(graphics);

    }
    //★ debug ~ mode ★
    if (debug_mode)
    {
      draw_engine_values(graphics);
      //draw_object_position(graphics, player);
      //draw_object_hit_box(graphics, player);
      //draw_player_nickname(graphics, player);
      //draw_player_stats(graphics, player);
    }
  }

  private void draw_engine_values(Graphics2D graphics)
  {
    if (game_engine != null)
    {
      graphics.setColor(Color.white);
      graphics.drawString("★ DEBUG ~ MODE ★", 20, 20);
      graphics.drawLine(20, 30, 160, 30);

      double ups = game_engine.get_average_ups();
      double fps = game_engine.get_average_fps();
      graphics.setColor(Color.MAGENTA);
      graphics.drawString("► UPS: ", 20,50);
      graphics.drawString("► FPS: ", 20, 70);
      graphics.setColor(get_color_by_rate(ups));
      graphics.drawString(two_decimals.format(game_engine.get_average_ups()),65,50);
      graphics.setColor(get_color_by_rate(fps));
      graphics.drawString(two_decimals.format(game_engine.get_average_fps()),65,70);
    }
  }

  //private void draw_object_position(Graphics2D graphics, GameObject object)
  //{
  //  //draw players
  //  {
  //    //y axis
  //    graphics.setColor(Color.ORANGE);
  //    graphics.drawLine(
  //            0,
  //            entity.get_y(),
  //            entity.get_x()-5,
  //            entity.get_y());
  //    graphics.drawString("(0,"+entity.get_y()+")", 0, entity.get_y()-10);
//
  //    graphics.drawLine(
  //            game_engine.graphics.getWidth(),
  //            entity.get_y(),
  //            entity.get_x()+5,
  //            entity.get_y());
  //    graphics.drawString("("+game_engine.graphics.getWidth()+","+entity.get_y()+")", game_engine.graphics.getWidth()-65, entity.get_y()-10);
  //  }
  //  {
  //    //x axis
  //    graphics.setColor(Color.ORANGE);
  //    graphics.drawLine(
  //            entity.get_x(),
  //            0,
  //            entity.get_x(),
  //            entity.get_y()-5);
  //    graphics.drawString("("+entity.get_x()+", 0)", entity.get_x()+5, 15);
//
  //    graphics.drawLine(
  //            entity.get_x(),
  //            game_engine.graphics.getHeight(),
  //            entity.get_x(),
  //            entity.get_y()+5);
  //    graphics.drawString("("+entity.get_x()+", "+game_engine.graphics.getHeight()+")", entity.get_x()+5, game_engine.graphics.getHeight()-10);
  //  }
//
  //}

  //private void draw_object_hit_box(Graphics2D graphics, GameEntity entity)
  //{
  //  if (entity.get_hit_box() != null)
  //  {
  //    Rectangle2D hit_box = entity.get_hit_box();
  //    graphics.setColor(Color.red);
  //    graphics.drawRect((int) hit_box.getX(), (int) hit_box.getY(), (int) hit_box.getWidth(), (int) hit_box.getHeight());
  //  }
  //}
//
  //private void draw_player_nickname(Graphics2D graphics, PlayerEntity player)
  //{
  //  if (player.get_nickname() != null)
  //  {
  //    graphics.setColor(Color.white);
  //    int horizontal_margin = player.get_nickname().length();
  //    graphics.drawString(player.get_nickname(), player.get_x()-player.get_current_state().get_animation().get_current_sprite().get_image().getWidth()/2+horizontal_margin, player.get_y()+player.get_current_state().get_animation().get_current_sprite().get_image().getHeight()/2);
  //  }
  //}
//
  //private void draw_player_stats(Graphics2D graphics, PlayerEntity player)
  //{
  //  graphics.setColor(Color.gray);
  //  graphics.drawString("➜ Player velocity:", 17, 90);
  //  graphics.setColor(Color.white);
  //  graphics.drawString(String.valueOf(two_decimals.format((player.get_velocity_x()+ player.get_velocity_y())*20))+" km/h", 120, 90);
  //}

  private Color get_color_by_rate(double rate)
  {
    if (rate < 20)
    {
      return Color.red;
    }
    else if (rate < 30)
    {
      return Color.orange;
    }
    else
    {
      return Color.green;
    }
  }

  public void set_engine(GameEngine game_engine)
  {
    this.game_engine = game_engine;
  }

  public void set_debug_mode(boolean is_debug)
  {
    debug_mode = is_debug;
  }

  public InputAdapter get_input_adapter()
  {
    return input;
  }

  public void set_input_mapper(InputAdapter input_adapter)
  {
    input = input_adapter;
  }

  //public void set_player(PlayerEntity player_entity)
  //{
  //  player = player_entity;
  //}

  public void add_object(GameObject object)
  {
    objects.add(object);
  }

}

