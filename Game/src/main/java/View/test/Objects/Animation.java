package main.java.View.test.Objects;

import main.java.View.test.Resources.Sprite;

public class Animation {

  private Sprite[] sprite_list;
  private int current_sprite = 0;
  private int delay = 0;
  private int loop_count = 0;
  private boolean loop_forever = false;
  private boolean is_finished = false;

  public Animation()
  {}

  public Animation reset()
  {
    current_sprite = 0;
    delay = 0;
    loop_count = 0;
    return this;
  }

  public Animation(Sprite[] sprites)
  {
    sprite_list = sprites;
  }

  public Animation(Sprite[] sprites, int delay_loop, boolean loop)
  {
    sprite_list = sprites;
    delay = delay_loop;
    loop_forever = loop;
  }

  public void update()
  {
    if (!is_finished)
    {
      if (loop_count == delay)
      {
        if (current_sprite == sprite_list.length-1)
        {
          if (loop_forever)
          {
            current_sprite = 0;
          }
          else
          {
            finish();
          }
        }
        else
        {
          current_sprite++;
          loop_count = 0;
        }
      }
      else
      {
        loop_count++;
      }
    }
  }

  public void finish()
  {
    is_finished = true;
  }

  public void set_sprite_list(Sprite[] list)
  {
    sprite_list = list;
  }

  public Sprite[] get_sprite_list()
  {
    return sprite_list;
  }

  public Sprite get_current_sprite()
  {
    return sprite_list[current_sprite];
  }

  public void set_delay(int delay_loop)
  {
    delay = delay_loop;
  }

  public int get_loop_delay()
  {
    return delay;
  }

  public void set_loop(boolean loop)
  {
    loop_forever = loop;
  }

  public boolean is_loop()
  {
    return loop_forever;
  }

}
