package main.java.View.test.Logic;

import main.java.View.test.Objects.GameEntity;
import main.java.View.test.Objects.PlayerEntity;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GameControl {

  //objects
  private PlayerEntity player;
  private final List<GameEntity> entities = new ArrayList<>();

  //for print engine values
  private boolean debug_mode;
  private GameEngine game_engine;
  DecimalFormat two_decimals = new DecimalFormat("#.00");

  //player input
  InputMapper input;

  public void update()
  {
    //TODO: update here your game objects
    player.update(input);
    for (GameEntity entity : entities) {
      entity.update();
    }
  }

  public void render(Graphics2D graphics)
  {
    //TODO: draw here your game objects
    player.render(graphics);
    for (GameEntity entity : entities) {
      entity.render(graphics);
    }
    //★ debug ~ mode ★
    if (debug_mode)
    {
      draw_engine_values(graphics);
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

  public InputMapper get_input_mapper()
  {
    return input;
  }

  public void set_input_mapper(InputMapper input_mapper)
  {
    input = input_mapper;
  }

  public void set_player(PlayerEntity player_entity)
  {
    player = player_entity;
  }

  public void add_entity(GameEntity entity)
  {
    entities.add(entity);
  }

}
