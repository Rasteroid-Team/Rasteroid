package View.Objects.ObjectModels.Maps;

import View.Objects.ObjectModels.ObjectModel;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public abstract class MapModel extends ObjectModel {

  protected List<Rectangle2D> borders = new ArrayList<>();

  public abstract void load_borders();

  public List<Rectangle2D> get_border()
  {
    return borders;
  }

}
