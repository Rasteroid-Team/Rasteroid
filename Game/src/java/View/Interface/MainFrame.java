package View.Interface;


import View.GraphicEngine;
import View.Sprite;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;

public class MainFrame extends JFrame {

  GraphicEngine graphics;

  public MainFrame(GraphicEngine game_graphics)
  {
    graphics = game_graphics;
    setTitle("Rasteroid");
    setIconImage(new Sprite("Game/src/Resources/icons/rasteroid_logo.png").load().get_image());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setUndecorated(true);
    setSize(1920,1080);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());
    GridBagConstraints grid_constraints = new GridBagConstraints();
    grid_constraints.weightx = 1f;
    grid_constraints.weighty = 1f;
    grid_constraints.fill = BOTH;
    add(game_graphics, grid_constraints);
  }

  @Override
  public void setVisible(boolean b) {
    super.setVisible(b);
    if (b)
    {
      graphics.requestFocus();
    }
  }
}
