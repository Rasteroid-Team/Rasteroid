package Controller;

import Model.GameObject;
import Model.Map;
import Model.Player;
import Testing.AvtomatV1;
import Testing.InputAdapter;
import View.GraphicEngine;
import communications.CommunicationController;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class GameControl {

  //objects
  public static final List<GameObject> objects = new ArrayList<>();
  private static List<GameObject> adding_list = new ArrayList<>();
  private static List<GameObject> removing_list = new ArrayList<>();

  //for print engine values
  private boolean debug_mode;
  private GameEngine game_engine;
  DecimalFormat two_decimals = new DecimalFormat("#.00");

  public void update()
  {
    //TODO: update here your game objects
    try
    {
      for (GameObject object : objects)
      {
        object.update(objects);
      }
      check_modification_list();
      checkVictory();
    }
    catch (ConcurrentModificationException e)
    {
      e.printStackTrace();
    }

  }

  public void render(Graphics2D graphics)
  {
    //TODO: draw here your game objects
    for (GameObject object : objects) {
      object.render(graphics);
      if (debug_mode)
      {
        if (object instanceof Map)
        {
          graphics.setColor(Color.RED);
          ((Map) object).draw_borders(graphics, ScreenConnectionController.getConnections());
        }
      }

    }
    //★ debug ~ mode ★
    if (debug_mode)
    {
      draw_engine_values(graphics);
      draw_ip_address(graphics);
      this.drawButtons(graphics);
      //draw_object_position(graphics, player);
      //draw_object_hit_box(graphics, player);
      //draw_player_nickname(graphics, player);
      //draw_player_stats(graphics, player);
    }
  }

  //Nomes per a test, a nes final sa conexio entre pantalles se fera automaticament.
  private void drawButtons(Graphics2D g){

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int diameter = 25;

    if (ScreenConnectionController.getConnections()[0] != null){
      g.setColor(Color.green);
    } else {
      g.setColor(Color.red);
    }
    g.fillOval((int)(screenSize.getWidth()/2-diameter/2), 15, diameter, diameter);

    if (ScreenConnectionController.getConnections()[3] != null){
      g.setColor(Color.green);
    } else {
      g.setColor(Color.red);
    }
    g.fillOval(15, (int)(screenSize.getHeight()/2-diameter/2), diameter, diameter);

    if (ScreenConnectionController.getConnections()[1] != null){
      g.setColor(Color.green);
    } else {
      g.setColor(Color.red);
    }
    g.fillOval((int)((screenSize.getWidth()-diameter)-15), (int)(screenSize.getHeight()/2-diameter/2), diameter, diameter);

    if (ScreenConnectionController.getConnections()[2] != null){
      g.setColor(Color.green);
    } else {
      g.setColor(Color.red);
    }
    g.fillOval((int)(screenSize.getWidth()/2-diameter/2), (int)((screenSize.getHeight()-diameter)-15), diameter, diameter);
  }

  private void check_modification_list()
  {
    synchronized (adding_list) {
      objects.addAll(adding_list);
      adding_list.clear();
    }
    synchronized (removing_list) {
      objects.removeAll(removing_list);
      removing_list.clear();
    }
  }

  //comprueba la lista de GameObjects para declarar ganador, no funciona porque las pantallas no comparten la lista(?)
  public void checkVictory() {
    int i = 0;
    boolean victory = false;
    boolean onePlayer = true;
    Player winPlayer = null;
    for (GameObject object : objects) {
      if (object instanceof Player) {
        if (!onePlayer) {
          victory = false;
        }
        if (onePlayer) {
          winPlayer = (Player) object;
          victory = true;
          onePlayer = false;
        }

      }
    }
    if (victory) {
      System.out.println(winPlayer.getName() + "is the winner");
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

  private void draw_ip_address(Graphics2D graphics)
  {
    String ip;
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    }
    catch (UnknownHostException not_connected)
    {
      not_connected.printStackTrace();
      ip = "Not connected ...";
    }
    graphics.setColor(Color.white);
    graphics.drawLine(20, 90, 160, 90);
    graphics.setColor(Color.MAGENTA);
    graphics.drawString("► IPv4: ", 20,110);
    graphics.setColor(Color.gray);
    graphics.drawString(ip, 65, 110);
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

  //public void set_player(PlayerEntity player_entity)
  //{
  //  player = player_entity;
  //}

  public static void add_object(GameObject object)
  {
    synchronized (adding_list) {
      adding_list.add(object);
    }
  }

  public static void remove_object(GameObject object)
  {
    synchronized (adding_list) {
      removing_list.add(object);
    }
  }
}

