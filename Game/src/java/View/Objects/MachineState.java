package View.Objects;


public class MachineState {

  private String name;
  private Animation animation;

  public MachineState()
  {}

  public MachineState(String state_name)
  {
    name = state_name;
  }

  public MachineState(String state_name, Animation anim)
  {
    name = state_name;
    animation = anim;
  }

  public Animation get_animation()
  {
    return animation;
  }

  public void set_animation(Animation a)
  {
    animation = a;
  }

  public void set_name(String n)
  {
    name = n;
  }

  public String get_name() {return name;}

}
