package main.java.View.test.Logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputMapper implements KeyListener {

  private final boolean[] active_keys = new boolean[]
  {
    false, //W
    false, //A
    false, //S
    false  //D
  };

  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      case 'w' -> active_keys[0] = true;
      case 'a' -> active_keys[1] = true;
      case 's' -> active_keys[2] = true;
      case 'd' -> active_keys[3] = true;
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyChar()) {
      case 'w' -> active_keys[0] = false;
      case 'a' -> active_keys[1] = false;
      case 's' -> active_keys[2] = false;
      case 'd' -> active_keys[3] = false;
    }
  }

  public boolean[] get_active_keys()
  {
    return active_keys;
  }
}
