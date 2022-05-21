package View.Objects.PlayerModels;

import View.Objects.Animation;
import View.Objects.MachineState;

import java.util.ArrayList;

public class HR75 extends PlayerModel {

  private final String res_path = "Game/src/Resources/players/hr75/";

  @Override
  public void load_states() {
    machine_states = new ArrayList<>();

    //IDLE
    MachineState idle = new MachineState("idle");
    {
      Animation idle_animation = new Animation();
      idle_animation.set_delay(3);
      idle_animation.set_loop(true);
      load_sprite_in_range(idle_animation, res_path+"idle/hr75_idle_", 30, 100, 100);
      idle.set_animation(idle_animation);
    }
    machine_states.add(idle);

    //MOVE
    MachineState move = new MachineState("move");
    {
      Animation move_animation = new Animation();
      move_animation.set_delay(1);
      move_animation.set_loop(true);
      load_sprite_in_range(move_animation, res_path+"move/hr75_move_", 10, 100, 100);
      move.set_animation(move_animation);
    }

    machine_states.add(move);
    //machine_states.add(idle);
    //machine_states.add(idle);
    //machine_states.add(idle);
  }
}
