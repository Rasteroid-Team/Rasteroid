package View.Objects.ObjectModels.Players;

import View.Objects.Animation;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;

import java.util.ArrayList;

public class HR75 extends ObjectModel {

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

    //SHOOT
    MachineState shoot = new MachineState("shoot");
    {
      Animation shoot_animation = new Animation();
      shoot_animation.set_delay(1);
      shoot_animation.set_loop(true);
      load_sprite_in_range(shoot_animation, res_path+"shoot/hr75_shoot_", 8, 100, 100);
      shoot.set_animation(shoot_animation);
    }

    machine_states.add(shoot);

    //MOVE-SHOOT
    MachineState move_shoot = new MachineState("move_shoot");
    {
      Animation move_shoot_animation = new Animation();
      move_shoot_animation.set_delay(1);
      move_shoot_animation.set_loop(true);
      load_sprite_in_range(move_shoot_animation, res_path+"move_shoot/hr75_move_shoot_", 8, 100, 100);
      move_shoot.set_animation(move_shoot_animation);
    }

    machine_states.add(move_shoot);

    //machine_states.add(idle);
    //machine_states.add(idle);
    //machine_states.add(idle);
  }
}
