package View.Objects.ObjectModels.Players;

import View.Objects.Animation;
import View.Objects.MachineState;

import java.util.ArrayList;

public class Phoenix extends PlayerModel {

  /**
   * META_SET
   */
  public Phoenix()
  {
    meta = new Meta();
    meta.health_points = 70;
    meta.velocity = 7;
    meta.damage_per_bullet = 5;
    meta.shoot_interval = 0.2;
    meta.bullet_offset_x_y_list = new ArrayList<>();
    meta.bullet_offset_x_y_list.add(new int[]{-30,-10});
    meta.bullet_offset_x_y_list.add(new int[]{30,-10});
  }

  /**
   * RESOURCE_SET
   */

  private final String res_path = "Game/src/Resources/players/phoenix/";

  @Override
  public void load_states()
  {
    load_aura_colors(res_path+"aura/phoenix_aura_1.png", 150, 150);

    machine_states = new ArrayList<>();

    //IDLE
    MachineState idle = new MachineState("idle");
    {
      Animation idle_animation = new Animation();
      idle_animation.set_delay(3);
      idle_animation.set_loop(true);
      load_sprite_in_range(idle_animation, res_path+"idle/phoenix_idle_", 50, 100, 100);
      idle.set_animation(idle_animation);
    }
    machine_states.add(idle);

    //MOVE
    MachineState move = new MachineState("move");
    {
      Animation move_animation = new Animation();
      move_animation.set_delay(1);
      move_animation.set_loop(true);
      load_sprite_in_range(move_animation, res_path+"move/phoenix_move_", 10, 100, 100);
      move.set_animation(move_animation);
    }

    machine_states.add(move);

    //SHOOT
    MachineState shoot = new MachineState("shoot");
    {
      Animation shoot_animation = new Animation();
      shoot_animation.set_delay(1);
      shoot_animation.set_loop(true);
      load_sprite_in_range(shoot_animation, res_path+"shoot/phoenix_shoot_", 1, 100, 100);
      shoot.set_animation(shoot_animation);
    }

    machine_states.add(shoot);

    //MOVE-SHOOT
    MachineState move_shoot = new MachineState("move_shoot");
    {
      Animation move_shoot_animation = new Animation();
      move_shoot_animation.set_delay(1);
      move_shoot_animation.set_loop(true);
      load_sprite_in_range(move_shoot_animation, res_path+"move_shoot/phoenix_move_shoot_", 1, 100, 100);
      move_shoot.set_animation(move_shoot_animation);
    }

    machine_states.add(move_shoot);

  }

}
