package View.test.Objects;

import View.test.Logic.InputMapper;
import View.test.Objects.PlayerModels.PlayerModel;

import java.awt.*;

public class PlayerEntity extends GameEntity {

  private String nickname;
  private PlayerModel model;
  private double max_velocity = 4;
  private double velocity_x_left = 0;
  private double velocity_x_right = 0;
  private double velocity_y_up = 0;
  private double velocity_y_down = 0;
  private double acceleration = 0.8;
  private int rotation_radiants = 0;

  public PlayerEntity(PlayerModel player_model) {
    super();
    model = player_model;
    change_state( model.get_machine_states().get(0));
  }

  public PlayerEntity(int spawn_x, int spawn_y, PlayerModel player_model) {
    super(spawn_x, spawn_y);
    model = player_model;
    change_state(model.get_machine_states().get(0));
  }

  public PlayerEntity(String nick, PlayerModel player_model) {
    super();
    model = player_model;
    nickname = nick;
    change_state(model.get_machine_states().get(0));
  }

  @Override
  public void spawn() {
    set_x(200);
    set_y(200);
  }

  public void update(InputMapper input)
  {
    process_input(input);
    get_current_state().get_animation().update();
    set_hit_box(new Rectangle(get_x()-40, get_y()-40,80,80));
  }

  private void process_input(InputMapper input)
  {


    //idle
    if (!input.get_active_keys()[0] && !input.get_active_keys()[1]
            && !input.get_active_keys()[2] && !input.get_active_keys()[3])
    {
      change_state(model.get_machine_states().get(0));
      decelerate_x();
      decelerate_y();
    }

    decelerate_x();
    decelerate_y();

    //transversal
    if ((input.get_active_keys()[0]||input.get_active_keys()[2])
            && (input.get_active_keys()[1]||input.get_active_keys()[3]))
    {
      //TODO Make velocity not increase
    }

    if (input.get_active_keys()[0]) //W
    {
      accelerate_y(-1);
      change_state(model.get_machine_states().get(1));
    }


    if (input.get_active_keys()[1]) //A
    {
      accelerate_x(-1);
      change_state(model.get_machine_states().get(4));
    }

    if (input.get_active_keys()[2]) //S
    {
      accelerate_y(1);
      change_state(model.get_machine_states().get(3));
    }

    if (input.get_active_keys()[3]) //D
    {
      accelerate_x(1);
      change_state(model.get_machine_states().get(2));
    }

    move((int) (velocity_x_right-velocity_x_left), (int) (velocity_y_down-velocity_y_up));

  }

  private void accelerate_x(int direction)
  {
    if (direction > 0)
    {
      if (velocity_x_right >= max_velocity)
      {
        velocity_x_right = max_velocity;
        velocity_x_left -= acceleration/10;
        decelerate_y();
      }
      else
      {
        velocity_x_right += acceleration;
        velocity_x_left -= acceleration/10;
        decelerate_y();
      }
    }
    else
    {
      if (velocity_x_left >= max_velocity)
      {
        velocity_x_left = max_velocity;
        velocity_x_right -= acceleration/10;
        decelerate_y();
      }
      else
      {
        velocity_x_left += acceleration;
        velocity_x_right -= acceleration/10;
        decelerate_y();
      }
    }

  }

  private void accelerate_y(int direction)
  {
    if (direction > 0)
    {
      if (velocity_y_down >= max_velocity)
      {
        velocity_y_down = max_velocity;
        velocity_y_up -= acceleration/10;
        decelerate_x();
      }
      else
      {
        velocity_y_down += acceleration;
        velocity_y_up -= acceleration/10;
        decelerate_x();
      }
    }
    else
    {
      if (velocity_y_up >= max_velocity)
      {
        velocity_y_up = max_velocity;
        velocity_y_down -= acceleration/10;
        decelerate_x();
      }
      else
      {
        velocity_y_up += acceleration;
        velocity_y_down -= acceleration/10;
        decelerate_x();
      }
    }
  }

  private void decelerate_x()
  {
    velocity_x_right -= acceleration/100;
    velocity_x_left -= acceleration/100;
    if (velocity_x_right < 0)
    {
      velocity_x_right = 0;
    }
    if (velocity_x_left < 0)
    {
      velocity_x_left = 0;
    }
  }

  private void decelerate_y()
  {
    velocity_y_up -= acceleration/100;
    velocity_y_down -= acceleration/100;
    if (velocity_y_up < 0)
    {
      velocity_y_up = 0;
    }
    if (velocity_y_down < 0)
    {
      velocity_y_down = 0;
    }
  }

  @Override
  public void render(Graphics2D graphics) {
    Image image = get_current_state().get_animation().get_current_sprite().rotate(rotation_radiants);
    graphics.drawImage(image, get_x()-image.getWidth(null)/2, get_y()-image.getHeight(null)/2, null);
  }

  public String get_nickname()
  {
    return this.nickname;
  }

  public void set_nickname(String nick)
  {
    nickname = nick;
  }

  public double get_velocity_x()
  {
    return velocity_x_right-velocity_x_left;
  }

  public double get_velocity_y()
  {
    return velocity_y_down-velocity_y_up;
  }

}
