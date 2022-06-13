package View;

import Model.Player;
import View.Objects.ObjectModels.Bullets.YellowBullet;
import View.Objects.ObjectModels.Maps.MapModel;
import View.Objects.ObjectModels.Maps.Space;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Particles.ExplosionFx;
import View.Objects.ObjectModels.Players.HR75;
import View.Objects.ObjectModels.Players.Phoenix;
import View.Objects.ObjectModels.Players.PlayerModel;
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

  //models
  protected static MapModel MAP_SPACE_RAW;
  public static MapModel MAP_SPACE() {return (MapModel) ObjectModel.copy(MAP_SPACE_RAW, new Space());}

  protected static PlayerModel PLAYER_HR75_RAW;
  public static PlayerModel PLAYER_HR75() {return PlayerModel.copy(PLAYER_HR75_RAW, new HR75());}

  protected static PlayerModel PLAYER_PHOENIX_RAW;
  public static PlayerModel PLAYER_PHOENIX() {return PlayerModel.copy(PLAYER_PHOENIX_RAW, new Phoenix());}

  protected static ObjectModel PLAYER_CIRCLE_RAW;
  public static ObjectModel PLAYER_CIRCLE()  {return ObjectModel.copy(PLAYER_CIRCLE_RAW, new HR75());}

  protected static ObjectModel BULLET_YELLOW_RAW;
  public static ObjectModel BULLET_YELLOW()  {return ObjectModel.copy(BULLET_YELLOW_RAW, new YellowBullet());}

  protected static ObjectModel PARTICLE_EXPLOSION_RAW;
  public static ObjectModel PARTICLE_EXPLOSION()  {return ObjectModel.copy(PARTICLE_EXPLOSION_RAW, new ExplosionFx());}

  //raw res
  public static Sprite UI_LIFE_BAR;

  public int get_res_count()
  {
    return 7;
  }


}
