package View.Objects.ObjectModels.Maps;

import View.Objects.Animation;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Space extends MapModel {

  private final String res_path = "Game/src/Resources/maps/space/";

  public Space() {
    super();
    load_borders();
  }

  @Override
  public void load_states() {
    machine_states = new ArrayList<>();

    //MAP STATE
    MachineState map = new MachineState("map");
    {
      Animation map_animation = new Animation();
      map_animation.set_delay(3);
      map_animation.set_loop(true);
      load_sprite_in_range(map_animation, res_path + "space_", 1, 7680, 3240);
      map.set_animation(map_animation);
    }
    machine_states.add(map);
  }

  @Override
  public void load_borders()
  {
    Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle2D top_border = new Rectangle(0,0,screen_size.width, 10);
    Rectangle2D left_border = new Rectangle(0,0,10, screen_size.height);
    Rectangle2D bottom_border = new Rectangle(0,screen_size.height-10,screen_size.width, 10);
    Rectangle2D right_border = new Rectangle(screen_size.width-10,0,10, screen_size.height);

    borders.add(top_border);
    borders.add(left_border);
    borders.add(bottom_border);
    borders.add(right_border);
  }
}
