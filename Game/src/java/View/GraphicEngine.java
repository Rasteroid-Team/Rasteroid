package View;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GraphicEngine extends Canvas {

  private BufferStrategy buffer_strategy;

  public GraphicEngine()
  {
    setBackground(new Color(0x000000));
  }

  //powered buffer booster//
  public Graphics2D lock_canvas()
  {
    buffer_strategy = getBufferStrategy();
    if (buffer_strategy == null)
    {
      createBufferStrategy(2);
      buffer_strategy = getBufferStrategy();
    }

    return (Graphics2D) buffer_strategy.getDrawGraphics();
  }

  //print and free memory//
  public void unlock_and_post()
  {
    if (buffer_strategy != null)
    {
      buffer_strategy.show();
      buffer_strategy.getDrawGraphics().dispose();
    }
  }

}
