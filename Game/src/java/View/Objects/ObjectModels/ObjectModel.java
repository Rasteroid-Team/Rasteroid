package View.Objects.ObjectModels;


import View.Objects.Animation;
import View.Objects.MachineState;
import View.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectModel {

  protected List<MachineState> machine_states = new ArrayList<>();

  public ObjectModel()
  {}

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

  public static ObjectModel copy(ObjectModel original, ObjectModel copy) {
    for (MachineState state: original.get_machine_states()) {
      copy.machine_states.add(new MachineState(state.get_name(), new Animation(state.get_animation().get_sprite_list(), state.get_animation().get_loop_delay(),state.get_animation().is_loop())));
    }
    return copy;
  }
}
