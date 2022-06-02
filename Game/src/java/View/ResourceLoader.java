package View;

import Model.Player;
import View.Objects.ObjectModels.Bullets.YellowBullet;
import View.Objects.ObjectModels.Maps.MapModel;
import View.Objects.ObjectModels.Maps.Space;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Particles.ExplosionFx;
import View.Objects.ObjectModels.Players.CirclePlayer;
import View.Objects.ObjectModels.Players.HR75;
import View.Objects.ObjectModels.Players.PlayerModel;

public class ResourceLoader implements Runnable {

  private boolean is_loading;
  private int resource_count;
  private int resource_loaded;
  private int percent_done;
  private Resources res;

  public void init()
  {
    is_loading = true;
    percent_done = 0;
    res = new Resources();
    resource_count = res.get_res_count();
    resource_loaded = 0;
    new Thread(this).start(); //--> launch resource loader
  }

  private ObjectModel load(Class<? extends ObjectModel> model_class)
  {
    ObjectModel model = null;
    try {
      model = model_class.getDeclaredConstructor().newInstance();
      model.load_states();
    } catch (Exception e) {
      e.printStackTrace();
    }
    resource_loaded ++;
    calculate_percent_done();
    return model;
  }

    private PlayerModel load_player(Class<? extends PlayerModel> model_class)
  {
    PlayerModel model = null;
    try {
      model = model_class.getDeclaredConstructor().newInstance();
      model.load_states();
    } catch (Exception e) {
      e.printStackTrace();
    }
    resource_loaded ++;
    calculate_percent_done();
    return model;
  }

  private Sprite load(String path)
  {
    return new Sprite(path).load();
  }

  public boolean is_loading()
  {
    return is_loading;
  }

  public int get_percent_done()
  {
    return percent_done;
  }

  public void calculate_percent_done()
  {
    percent_done = (resource_loaded*100)/resource_count;
  }

  @Override
  public void run()
  {
    //loading elements
    Resources.MAP_SPACE_RAW = (MapModel) load(Space.class);
    Resources.PLAYER_HR75_RAW = load_player(HR75.class);
    Resources.PLAYER_CIRCLE_RAW = load(CirclePlayer.class);
    Resources.BULLET_YELLOW_RAW = load(YellowBullet.class);
    Resources.PARTICLE_EXPLOSION_RAW = load(ExplosionFx.class);
    Resources.UI_LIFE_BAR = load("Game/src/Resources/ui/life_bar.png");
    is_loading = false;
  }

}
