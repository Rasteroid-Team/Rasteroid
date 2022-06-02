package View.Objects.ObjectModels.Players;



import View.Objects.Animation;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;
import View.Sprite;

import java.util.ArrayList;

public class CirclePlayer extends ObjectModel {

  private final String res_path = "Game/src/Resources/players/circlePlayer/";

  @Override
  public void load_states()
  {
    machine_states = new ArrayList<>();

    //IDLE
    MachineState idle = new MachineState("idle");
    {
      Animation idle_animation = new Animation();
      idle_animation.set_delay(10);
      idle_animation.set_loop(true);
      //sprite_list
      Sprite[] idle_sprites = new Sprite[1];
      idle_sprites[0] = new Sprite(res_path+"idle/circle_idle_1.png").load().resize(200,200);
      //
      idle_animation.set_sprite_list(idle_sprites);
      idle.set_animation(idle_animation);
    }
    machine_states.add(idle);

    //UP
    MachineState up = new MachineState("up");
    {
      Animation up_animation = new Animation();
      up_animation.set_delay(10);
      up_animation.set_loop(true);
      //sprite_list
      Sprite[] up_sprites = new Sprite[1];
      up_sprites[0] = new Sprite(res_path+"up/circle_up_1.png").load().resize(200,200);
      //
      up_animation.set_sprite_list(up_sprites);
      up.set_animation(up_animation);
    }
    machine_states.add(up);

    //LEFT
    MachineState left = new MachineState("left");
    {
      Animation left_animation = new Animation();
      left_animation.set_delay(10);
      left_animation.set_loop(true);
      //sprite_list
      Sprite[] left_sprites = new Sprite[1];
      left_sprites[0] = new Sprite(res_path+"left/circle_left_1.png").load().resize(200,200);
      //
      left_animation.set_sprite_list(left_sprites);
      left.set_animation(left_animation);
    }
    machine_states.add(left);

    //DOWN
    MachineState down = new MachineState("down");
    {
      Animation down_animation = new Animation();
      down_animation.set_delay(10);
      down_animation.set_loop(true);
      //sprite_list
      Sprite[] down_sprites = new Sprite[1];
      down_sprites[0] = new Sprite(res_path+"down/circle_down_1.png").load().resize(200,200);
      //
      down_animation.set_sprite_list(down_sprites);
      down.set_animation(down_animation);
    }
    machine_states.add(down);

    //RIGHT
    MachineState right = new MachineState("right");
    {
      Animation right_animation = new Animation();
      right_animation.set_delay(10);
      right_animation.set_loop(true);
      //sprite_list
      Sprite[] right_sprites = new Sprite[1];
      right_sprites[0] = new Sprite(res_path+"right/circle_right_1.png").load().resize(200,200);
      //
      right_animation.set_sprite_list(right_sprites);
      right.set_animation(right_animation);
    }
    machine_states.add(right);
  }
}
