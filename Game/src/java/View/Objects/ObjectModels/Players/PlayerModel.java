package View.Objects.ObjectModels.Players;

import View.Objects.Animation;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;
import View.Sprite;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public abstract class PlayerModel extends ObjectModel implements Serializable {

  private Sprite aura;

  public static PlayerModel copy(PlayerModel original, PlayerModel copy)
  {
    for (MachineState state: original.get_machine_states())
    {
      copy.machine_states.add(new MachineState(state.get_name(), new Animation(state.get_animation().get_sprite_list(), state.get_animation().get_loop_delay(),state.get_animation().is_loop())));
    }
    copy.aura_colors = (HashMap<Color, Sprite>) original.aura_colors.clone();
    return copy;
  }

  protected HashMap<Color, Sprite> aura_colors = new HashMap<>();

  public void load_aura_colors(String path, int width, int height)
  {
    Sprite aura_model = new Sprite(path).load().resize(width, height);
    aura = aura_model;
    create_aura(aura_model, PlayerColors.red);
    create_aura(aura_model, PlayerColors.blue);
    create_aura(aura_model, PlayerColors.green);
    create_aura(aura_model, PlayerColors.yellow);
    create_aura(aura_model, PlayerColors.brown);
    create_aura(aura_model, PlayerColors.cyan);
    create_aura(aura_model, PlayerColors.magenta);
    create_aura(aura_model, PlayerColors.orange);
    create_aura(aura_model, PlayerColors.violet);
  }

  private void create_aura(Sprite aura_model, Color color)
  {
    Sprite aura = new Sprite("");
    aura.set_image(aura_model.apply_filter(color));
    aura_colors.put(color, aura);
  }

  public void set_aura_color(Color color)
  {
    System.out.println(aura_colors.get(color));
    aura = aura_colors.get(color);
  }

  public Sprite get_aura() {return aura;}


  /**
   * <h2>💀 Meta options and configuration 💀</h2>
   * <p>Add here any property that can be used to distinct each model</p>
   * <p><b>Warning :</b>
   * <br>
   * &nbsp;&nbsp;&nbsp;&nbsp; You need to create a new instance of {@link Meta} inside any model<br>
   * &nbsp;&nbsp;&nbsp;&nbsp; "<code>Class<?extends PlayerModel></code>" and be sure to set all of these variables<br>
   * &nbsp;&nbsp;&nbsp;&nbsp; to have a good in-game performance</p>
   */
  Meta meta;
  public Meta get_meta() {return meta;}
  public static class Meta
  {
    public int health_points;
    public int damage_per_bullet;
    public double shoot_interval;
    public List<int[]> bullet_offset_x_y_list;
    public int velocity;
  }

}
