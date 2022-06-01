package Testing;

import Controller.GameControl;
import Model.DynamicBody;
import Model.GameObject;
import Model.Player;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Players.PlayerModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * <h1>👿 AVTOMAT MACHINE 👿</h1>
 * <p><b>Description: </b>Used to search and track players</p>
 * <br>
 * <p>
 *   <b>Machine states</b>
 * <ul>
 *   <li>-Attack to trace angle and shoot player-</li>
 *   <li>-Search to move randomly-</li>
 * </ul>
 * </p>
 * <br>
 * <b>This object cannot be extended, modified or implemented
 * without the author permission</b>
 * <br>
 * <br>
 * <p>
 *   <h2>Author info</h2>
 *    <ul>
 *      <li>Tag = @author josel</li>
 *      <br>
 *      <li>
 *        Github links:
 *        <ul>
 *          <li><a href="https://github.com/joseluissaiz">joseluissaiz</a></li>
 *          <li><a href="https://github.com/JoseLuisSaizLopez/">JoseLuisSaizLopez</a></li>
 *        </ul>
 *      </li>
 *      <li>
 *        Email:
 *        <ul>
 *          <li>joselu609@gmail.com</li>
 *        </ul>
 *      </li>
 *    </ul>
 * </p>
 */
public final class AvtomatV1 extends Player {

  private final AI_STATE_AVTOMAT av;

  public AvtomatV1(GameControl g_control, InputAdapter adp, ObjectModel model, Color av_color) {
    super(g_control, adp, (PlayerModel) model, av_color);
    AI_STATE_AVTOMAT.AVT_ADAPTER av_adapter = new AI_STATE_AVTOMAT.AVT_ADAPTER();
    set_adapter(av_adapter);
    av = new AI_STATE_AVTOMAT(this, av_adapter);
  }

  @Override
  public void update(List<GameObject> objects) {
    super.update(objects);
    av.update_av();
  }

  private class AI_STATE_AVTOMAT
  {
    static int search_dx = 500;
    ContextReader cr;
    InputAdapter AI_ADAPTER;
    AvtomatV1 MACHINE;
    long LAST_CHANGE;
    int SLEEP_TIME = 1000;
    int shoot_miss = 0;
    AVT_CONDITION CONDITION;
    private AI_STATE_AVTOMAT(AvtomatV1 machine, InputAdapter CONTROL)
    {
      cr = new ContextReader(new PlayerTracker(), new AVT_RANDOMIZER());
      AI_ADAPTER = CONTROL;
      MACHINE = machine;
      CONDITION = new AVT_CONDITION(cr, AI_ADAPTER);
    }

    private void update_av()
    {
      AI_STATE_AVTOMAT.AVT_STATE state = cr.read_context(GameControl.objects);
      switch (state)
      {
        case ATTACK -> CONDITION.attack();
        case SEARCH -> CONDITION.search();
      }
    }

    private static class AVT_ADAPTER extends InputAdapter
    {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public boolean no_key_pressed() {
        return super.no_key_pressed();
      }
    }

    private class AVT_CONDITION
    {
      ContextReader CONTEXT;
      InputAdapter ADAPTER;
      AVT_RANDOMIZER RANDOMIZER;
      public AVT_CONDITION(ContextReader context, InputAdapter adapter)
      {
        CONTEXT = context;
        ADAPTER = adapter;
        RANDOMIZER = new AVT_RANDOMIZER();
      }
      private void attack()
      {
        //get attack angle
        float pl_posx = CONTEXT.TRACKED_PLAYER.getBody().getPosX();
        float pl_posy = CONTEXT.TRACKED_PLAYER.getBody().getPosY();
        double obj_angle = 90 + Math.toDegrees(Math.atan2(pl_posy - MACHINE.getBody().getPosY(), pl_posx - MACHINE.getBody().getPosX()));
        MACHINE.getBody().setAngle((int) obj_angle+MACHINE.av.shoot_miss);
        ADAPTER.get_active_keys()[0] = false;
        ADAPTER.get_active_keys()[3] = true;
        if (System.currentTimeMillis() - LAST_CHANGE > SLEEP_TIME)
        {
          MACHINE.av.shoot_miss = RANDOMIZER.shoot_miss();
          LAST_CHANGE = System.currentTimeMillis();
        }
      }
      private void search()
      {
        DynamicBody MACHINE_BODY = (DynamicBody) MACHINE.getBody();
        ADAPTER.get_active_keys()[3] = false;
        if (System.currentTimeMillis() - LAST_CHANGE > SLEEP_TIME)
        {
          MACHINE_BODY.setAngle(RANDOMIZER.direction(0));
          //check_top
          if (MACHINE.getBody().getPosY() < 200) {
            MACHINE_BODY.setAngle(RANDOMIZER.direction(180));
            MACHINE_BODY.setAngle(180);
          }
          // check_left
          if (MACHINE.getBody().getPosX() < 200) {
            MACHINE_BODY.setAngle(RANDOMIZER.direction(90));
            MACHINE_BODY.setAngle(90);
          }
          // check_right
          if (MACHINE.getBody().getPosX() > Toolkit.getDefaultToolkit().getScreenSize().width - 200) {
            MACHINE_BODY.setAngle(RANDOMIZER.direction(270));
            MACHINE_BODY.setAngle(270);
          }
          // check_bottom
          if (MACHINE.getBody().getPosY() > Toolkit.getDefaultToolkit().getScreenSize().height - 200) {
            MACHINE_BODY.setAngle(RANDOMIZER.direction(45));
            MACHINE_BODY.setAngle(0);
          }

          LAST_CHANGE = System.currentTimeMillis();
        }
        ADAPTER.get_active_keys()[0] = true;
      }
    }

    private class AVT_RANDOMIZER
    {
      Random rd = new Random();
      int direction(int excluded)
      {
        int new_dir = excluded;
        //while (new_dir == excluded || new_dir < excluded+45 || new_dir > excluded-45)
        //{
          new_dir = rd.nextInt(360-excluded)+excluded;
          //System.out.println(new_dir);


        //}
        return new_dir;
      }
      int shoot_miss()
      {
        return rd.nextInt(10)-rd.nextInt(20);
      }
      boolean flee_oportunity(List<GameObject> RIVAL_AV)
      {
        int RIVAL_AV_HEALTH = 0;
        int RIVALS = 0;
        for (GameObject RIVAL:RIVAL_AV) {
          if (RIVAL instanceof Player && RIVAL != AvtomatV1.this)
          {
            RIVAL_AV_HEALTH+=RIVAL.getHealth();
            RIVALS++;
          }
        }
        RIVAL_AV_HEALTH /= RIVALS;
        if (RIVAL_AV_HEALTH > getHealth())
        {
          return rd.nextInt(100) - 50 > 0;
        }
        return false;
      }
    }

    private enum AVT_STATE
    {
      SEARCH, ATTACK,
    }

    private class PlayerTracker
    {
      private boolean track(Player pl, AvtomatV1 this_av)
      {
        //get attack angle
        float pl_posx = pl.getBody().getPosX();
        float pl_posy = pl.getBody().getPosY();
        double h;
        {
          //angle trigonometry
          float c1 = pl_posy - MACHINE.getBody().getPosY();
          float c2 = pl_posx - MACHINE.getBody().getPosX();
          h = (float) Math.sqrt(Math.pow(c1,2) + Math.pow(c2,2));
        }
        return h < search_dx;
      }
    }

    private class ContextReader
    {
      private PlayerTracker PLAYER_TRACKER;
      private boolean POSSIBLE_FLEE;
      private AVT_RANDOMIZER RANDOMIZER;
      private long LAST_CHANGE = 0;
      private ContextReader(PlayerTracker tracker, AVT_RANDOMIZER randomizer)
      {
        PLAYER_TRACKER = tracker;
        RANDOMIZER = randomizer;
      }
      private Player TRACKED_PLAYER = null;
      public AVT_STATE read_context(List<GameObject> objects)
      {
        if (System.currentTimeMillis() - LAST_CHANGE > 15000)
        {
          av.cr.POSSIBLE_FLEE = false;
        }
        else
        {
          LAST_CHANGE = System.currentTimeMillis();
        }
        if (POSSIBLE_FLEE)
        {
          if(RANDOMIZER.flee_oportunity(objects))
          {
            for (GameObject obj:objects) {
              if (obj instanceof Player)
              {
                if (obj != MACHINE)
                {
                  TRACKED_PLAYER = (Player) obj;
                  return AVT_STATE.ATTACK;
                }
              }
            }
          }
          else
          {
            TRACKED_PLAYER = null;
            return AVT_STATE.SEARCH;
          }
        }
        else
        {
          for (GameObject obj:objects) {
            if (obj instanceof Player)
            {
              if (obj != MACHINE)
              {
                if (PLAYER_TRACKER.track((Player) obj, AvtomatV1.this))
                {
                  TRACKED_PLAYER = (Player) obj;
                  return AVT_STATE.ATTACK;
                }
              }
            }
          }
        }
        TRACKED_PLAYER = null;
        return AVT_STATE.SEARCH;
      }
    }
  }

  @Override
  public void take_damage(float damage) {
    super.take_damage(damage);
    if (health < 50)
    {
      av.cr.POSSIBLE_FLEE = true;
    }
  }
}
