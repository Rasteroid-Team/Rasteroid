package main.java.View.test.Objects;

import main.java.View.test.Logic.InputMapper;
import main.java.View.test.Objects.PlayerModels.PlayerModel;

import java.awt.*;

public class PlayerEntity extends GameEntity {

  private PlayerModel model;
  private int velocity = 10;

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

  @Override
  public void spawn() {
    set_x(200);
    set_y(200);
  }

  public void update(InputMapper input)
  {
    process_input(input);
    get_current_state().get_animation().update();
  }

  private void process_input(InputMapper input)
  {

    int output_velocity = velocity;

    //idle
    if (!input.get_active_keys()[0] && !input.get_active_keys()[1]
            && !input.get_active_keys()[2] && !input.get_active_keys()[3])
    {
      change_state(model.get_machine_states().get(0));
    }

    //transversal
    if ((input.get_active_keys()[0]||input.get_active_keys()[2])
            && (input.get_active_keys()[1]||input.get_active_keys()[3]))
    {
      output_velocity /= 1.33;
    }

    if (input.get_active_keys()[0]) //W
    {
      change_state(model.get_machine_states().get(1));
      move(0, -output_velocity);
    }



    if (input.get_active_keys()[1]) //A
    {
      change_state(model.get_machine_states().get(4));
      move(-output_velocity, 0);
    }

    if (input.get_active_keys()[2]) //S
    {
      change_state(model.get_machine_states().get(3));
      move(0, output_velocity);
    }

    if (input.get_active_keys()[3]) //D
    {
      change_state(model.get_machine_states().get(2));
      move(output_velocity, 0);
    }

  }

  @Override
  public void render(Graphics2D graphics) {
    graphics.drawImage(get_current_state().get_animation().get_current_sprite().get_image(), get_x(), get_y(), null);
  }
}
