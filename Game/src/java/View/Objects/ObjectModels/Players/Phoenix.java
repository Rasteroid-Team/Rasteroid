package View.Objects.ObjectModels.Players;

import View.Objects.Animation;
import View.Objects.MachineState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Phoenix extends PlayerModel {

  /**
   * META_SET
   */
  JSONParser parser = new JSONParser();
  Object object;
  public Phoenix()
  {
    asignarValoresJSON();
  }

  public void asignarValoresJSON() {
    String json = "Phoenix.json";
    Path path = Paths.get(json);
    String ruta = String.valueOf(path.toAbsolutePath());
    String rutaAbsoluta = ruta.replace(json,"Game\\src\\Resources\\config\\")+json;
    {
      try {
        object = parser.parse(new FileReader(rutaAbsoluta));
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }

    JSONObject config = (JSONObject) object;
    String vel = (String) config.get("velocity");
    String health = (String) config.get("health_points");
    String damage = (String) config.get("damage_per_bullet");
    String shoot = (String) config.get("shoot_interval");

    JSONArray arr = (JSONArray) config.get("BulletOffset");

    for (int i = 0; i < arr.size(); i++) {
      JSONObject j = (JSONObject) arr.get(i);
      String x = j.get("x").toString();
      String y = j.get("y").toString();
      String x2 = j.get("x2").toString();
      String y2 = j.get("y2").toString();

      meta = new Meta();
      meta.health_points = Integer.parseInt(health);
      meta.velocity = Integer.parseInt(vel);
      meta.damage_per_bullet = Integer.parseInt(damage);
      meta.shoot_interval = Double.parseDouble(shoot);
      meta.bullet_offset_x_y_list = new ArrayList<>();
      meta.bullet_offset_x_y_list.add(new int[]{Integer.parseInt(x), Integer.parseInt(y)});
      meta.bullet_offset_x_y_list.add(new int[]{Integer.parseInt(x2), Integer.parseInt(y2)});
    }
  }

  /**
   * RESOURCE_SET
   */

  private final String res_path = "Game/src/Resources/players/phoenix/";

  @Override
  public void load_states()
  {
    load_aura_colors(res_path+"aura/phoenix_aura_1.png", 150, 150);

    machine_states = new ArrayList<>();

    //IDLE
    MachineState idle = new MachineState("idle");
    {
      Animation idle_animation = new Animation();
      idle_animation.set_delay(3);
      idle_animation.set_loop(true);
      load_sprite_in_range(idle_animation, res_path+"idle/phoenix_idle_", 50, 100, 100);
      idle.set_animation(idle_animation);
    }
    machine_states.add(idle);

    //MOVE
    MachineState move = new MachineState("move");
    {
      Animation move_animation = new Animation();
      move_animation.set_delay(1);
      move_animation.set_loop(true);
      load_sprite_in_range(move_animation, res_path+"move/phoenix_move_", 10, 100, 100);
      move.set_animation(move_animation);
    }

    machine_states.add(move);

    //SHOOT
    MachineState shoot = new MachineState("shoot");
    {
      Animation shoot_animation = new Animation();
      shoot_animation.set_delay(1);
      shoot_animation.set_loop(true);
      load_sprite_in_range(shoot_animation, res_path+"shoot/phoenix_shoot_", 1, 100, 100);
      shoot.set_animation(shoot_animation);
    }

    machine_states.add(shoot);

    //MOVE-SHOOT
    MachineState move_shoot = new MachineState("move_shoot");
    {
      Animation move_shoot_animation = new Animation();
      move_shoot_animation.set_delay(1);
      move_shoot_animation.set_loop(true);
      load_sprite_in_range(move_shoot_animation, res_path+"move_shoot/phoenix_move_shoot_", 1, 100, 100);
      move_shoot.set_animation(move_shoot_animation);
    }

    machine_states.add(move_shoot);

  }

}
