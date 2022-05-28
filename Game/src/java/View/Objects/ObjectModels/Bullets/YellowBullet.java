package View.Objects.ObjectModels.Bullets;

import View.Objects.Animation;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;

import java.util.ArrayList;

public class YellowBullet extends ObjectModel {

  private String res_path = "Game/src/Resources/misc/bullets/yellow/";

  @Override
  public void load_states() {
    machine_states = new ArrayList<>();

    //MAP STATE
    MachineState bullet = new MachineState("map");
    {
      Animation bullet_animation = new Animation();
      bullet_animation.set_delay(3);
      bullet_animation.set_loop(true);
      load_sprite_in_range(bullet_animation, res_path + "yellow_", 1, 20, 20);
      bullet.set_animation(bullet_animation);
    }
    machine_states.add(bullet);
  }

}
