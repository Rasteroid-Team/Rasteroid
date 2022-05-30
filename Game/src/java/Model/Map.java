package Model;

import Testing.InputAdapter;
import View.Objects.MachineState;
import View.Objects.ObjectModels.Maps.MapModel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Map extends GameObject {

  private MachineState map_state;
  private List<Rectangle2D> borders;

  public Map(MapModel model)
  {
    map_state = model.get_machine_states().get(0);
    borders = model.get_border();
  }


  @Override
  public void update(List<GameObject> objects) {
    map_state.get_animation().update();
  }

  @Override
  public void render(Graphics2D graphics) {
    graphics.drawImage(map_state.get_animation().get_current_sprite().get_image(), 0, 0, null);
  }


  public void draw_borders(Graphics2D graphics)
  {
    for (Rectangle2D rectangle: borders)
    {
      graphics.fillRect((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }
  }

  public Rectangle2D get_top_border(){
    return borders.get(0);
  }

  public Rectangle2D get_left_border(){
    return borders.get(1);
  }

  public Rectangle2D get_bottom_border(){
    return borders.get(2);
  }

  public Rectangle2D get_right_border(){
    return borders.get(3);
  }

}
