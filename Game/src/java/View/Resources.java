package View;

import View.Objects.ObjectModels.Bullets.YellowBullet;
import View.Objects.ObjectModels.Maps.MapModel;
import View.Objects.ObjectModels.Maps.Space;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Players.HR75;
import View.Sprite;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>Resources</code><br>
 * Get or load any model or resource in the application
 * <ul>
 *   <li>
 *    <b>Warning:</b> You need to set up a raw resource that will be loaded at start,
 *     then you should add a copy instance to create new models with the same
 *     attributes to perform different actions at a time.
 *   </li>
 * </ul>
 */
public class Resources {

  protected static MapModel MAP_SPACE_RAW;
  public static MapModel MAP_SPACE() {return (MapModel) ObjectModel.copy(MAP_SPACE_RAW, new Space());}

  protected static ObjectModel PLAYER_HR75_RAW;
  public static ObjectModel PLAYER_HR75() {return ObjectModel.copy(PLAYER_HR75_RAW, new HR75());}

  protected static ObjectModel PLAYER_CIRCLE_RAW;
  public static ObjectModel PLAYER_CIRCLE()  {return ObjectModel.copy(PLAYER_CIRCLE_RAW, new HR75());}

  protected static ObjectModel BULLET_YELLOW_RAW;
  public static ObjectModel BULLET_YELLOW()  {return ObjectModel.copy(BULLET_YELLOW_RAW, new YellowBullet());}


  public int get_res_count()
  {
    return 4;
  }


}
