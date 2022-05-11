package main.java.View.test.Objects.PlayerModels;

import main.java.View.test.Objects.MachineState;

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

}
