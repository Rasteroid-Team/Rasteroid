package main.java.View.test.Objects;

import java.awt.*;
import java.util.List;

public abstract class GameEntity {

  private int x;
  private int y;
  private int width;
  private int height;
  private List<MachineState> machine_states;
  private MachineState machine_state;

  public GameEntity()
  {
    x = 200;
    y = 200;
  }

  public GameEntity(int spawn_x, int spawn_y)
  {
    x = spawn_x;
    y = spawn_y;
  }

  public GameEntity(int spawn_x, int spawn_y, List<MachineState> states)
  {
    x = spawn_x;
    y = spawn_y;
    machine_states = states;
  }

  public void move(int move_x, int move_y)
  {
    x += move_x;
    y += move_y;
  }

  public void update(){}

  public void render(Graphics2D graphics){}

  public abstract void spawn();

  public int get_x()
  {
    return x;
  }

  public int get_y()
  {
    return y;
  }

  public void set_x(int new_x)
  {
    x = new_x;
  }

  public void set_y(int new_y)
  {
    y = new_y;
  }

  public int get_width()
  {
    return width;
  }

  public int get_height()
  {
    return height;
  }

  public void set_width(int w)
  {
    width = w;
  }

  public void set_height(int h)
  {
    height = h;
  }

  public void resize(int w, int h)
  {
    width = w;
    height = h;
  }

  public MachineState get_current_state()
  {
    return machine_state;
  }

  public List<MachineState> get_possible_states()
  {
    return machine_states;
  }

  public void change_state(MachineState state)
  {
    if (machine_state != state)
    {
      state.set_animation(state.get_animation().reset());
      machine_state = state;
    }
  }

}
