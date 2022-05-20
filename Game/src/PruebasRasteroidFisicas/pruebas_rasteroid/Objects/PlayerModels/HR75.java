package PruebasRasteroidFisicas.pruebas_rasteroid.Objects.PlayerModels;



import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.Animation;
import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.MachineState;

import java.util.ArrayList;

public class HR75 extends PlayerModel {

  private final String res_path = "Game/src/View/test/Resources/players/HR75/";

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
    machine_states.add(idle);
    machine_states.add(idle);
    machine_states.add(idle);
    machine_states.add(idle);
  }
}
