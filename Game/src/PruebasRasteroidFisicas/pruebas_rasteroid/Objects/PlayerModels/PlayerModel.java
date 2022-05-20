package PruebasRasteroidFisicas.pruebas_rasteroid.Objects.PlayerModels;


import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.Animation;
import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.MachineState;
import PruebasRasteroidFisicas.pruebas_rasteroid.Resources.Sprite;

import java.util.List;

public abstract class PlayerModel {

  protected List<MachineState> machine_states;

  public PlayerModel()
  {
    load_states();
  }

  public abstract void load_states();

  public List<MachineState> get_machine_states()
  {
    return machine_states;
  }

  public void load_sprite_in_range(Animation anim, String path, int sprite_number, int width, int height)
  {
    Sprite[] sprites = new Sprite[sprite_number];
    for (int i = 1; i <= sprite_number; i++) {
      sprites[i-1] = new Sprite((path+i+".png")).load().resize(width, height);
    }
    anim.set_sprite_list(sprites);
  }

}
