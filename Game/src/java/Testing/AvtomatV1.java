package Testing;

import Controller.GameControl;
import Model.DynamicBody;
import Model.GameObject;
import Model.Player;
import View.Objects.ObjectModels.ObjectModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
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

  public AvtomatV1(GameControl g_control, InputAdapter adp, ObjectModel model) {
    super(g_control, adp, model);
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
    static InputAdapter AI_ADAPTER;
    static AvtomatV1 MACHINE;
    static long LAST_CHANGE;
    static int SLEEP_TIME = 1000;
    private AI_STATE_AVTOMAT(AvtomatV1 machine, InputAdapter CONTROL)
    {
      cr = new ContextReader();
      AI_ADAPTER = CONTROL;
      MACHINE = machine;
    }

    private void update_av()
    {
      AI_STATE_AVTOMAT.AVT_STATE state = cr.read_context(GameControl.objects);
      switch (state)
      {
        case ATTACK -> AVT_CONDITION.attack();
        case SEARCH -> AVT_CONDITION.search();
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
      private static void attack()
      {
        //get attack angle
        float pl_posx = ContextReader.TRACKED_PLAYER.getBody().getPosX();
        float pl_posy = ContextReader.TRACKED_PLAYER.getBody().getPosY();
        double obj_angle = 90 + Math.toDegrees(Math.atan2(pl_posy - MACHINE.getBody().getPosY(), pl_posx - MACHINE.getBody().getPosX()));
        MACHINE.getBody().setAngle((int) obj_angle);
        AI_STATE_AVTOMAT.AI_ADAPTER.get_active_keys()[0] = false;
        AI_STATE_AVTOMAT.AI_ADAPTER.get_active_keys()[3] = true;
      }
      private static void search()
      {
        DynamicBody MACHINE_BODY = (DynamicBody) MACHINE.getBody();
        AI_STATE_AVTOMAT.AI_ADAPTER.get_active_keys()[3] = false;
        if (System.currentTimeMillis() - LAST_CHANGE > SLEEP_TIME)
        {
          MACHINE_BODY.setAngle(AVT_RANDOMIZER.direction(0));
          //check_top
          if (MACHINE.getBody().getPosY() < 200) {
            MACHINE_BODY.setAngle(AVT_RANDOMIZER.direction(180));
            MACHINE_BODY.setAngle(180);
          }
          // check_left
          if (MACHINE.getBody().getPosX() < 200) {
            MACHINE_BODY.setAngle(AVT_RANDOMIZER.direction(90));
            MACHINE_BODY.setAngle(90);
          }
          // check_right
          if (MACHINE.getBody().getPosX() > Toolkit.getDefaultToolkit().getScreenSize().width - 200) {
            MACHINE_BODY.setAngle(AVT_RANDOMIZER.direction(270));
            MACHINE_BODY.setAngle(270);
          }
          // check_bottom
          if (MACHINE.getBody().getPosY() > Toolkit.getDefaultToolkit().getScreenSize().height - 200) {
            MACHINE_BODY.setAngle(AVT_RANDOMIZER.direction(45));
            MACHINE_BODY.setAngle(0);
          }

          LAST_CHANGE = System.currentTimeMillis();
        }
        AI_STATE_AVTOMAT.AI_ADAPTER.get_active_keys()[0] = true;
      }
    }

    private static class AVT_RANDOMIZER
    {
      static Random rd = new Random();
      static int direction(int excluded)
      {
        int new_dir = excluded;
        //while (new_dir == excluded || new_dir < excluded+45 || new_dir > excluded-45)
        //{
          new_dir = rd.nextInt(360-excluded)+excluded;
          System.out.println(new_dir);
        //}
        return new_dir;
      }
    }

    private enum AVT_STATE
    {
      SEARCH, ATTACK,
    }

    private static class PlayerTracker
    {
      private static boolean track(Player pl, AvtomatV1 this_av)
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
      private static Player TRACKED_PLAYER = null;
      public AVT_STATE read_context(List<GameObject> objects)
      {
        for (GameObject obj:objects) {
          if (obj instanceof Player)
          {
            if (obj != MACHINE)
            {
              if (PlayerTracker.track((Player) obj, AvtomatV1.this))
              {
                TRACKED_PLAYER = (Player) obj;
                return AVT_STATE.ATTACK;
              }
            }
          }
        }
        TRACKED_PLAYER = null;
        return AVT_STATE.SEARCH;
      }
    }
  }

}
