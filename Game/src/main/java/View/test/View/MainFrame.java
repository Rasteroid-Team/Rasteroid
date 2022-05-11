package main.java.View.test.View;

import main.java.View.test.Logic.GraphicEngine;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;

public class MainFrame extends JFrame {

  public MainFrame(GraphicEngine game_graphics)
  {
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

}
