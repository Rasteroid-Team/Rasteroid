package main.java.View.test;

import main.java.View.test.Logic.GameControl;
import main.java.View.test.Logic.GameEngine;
import main.java.View.test.Logic.GraphicEngine;
import main.java.View.test.Logic.InputMapper;
import main.java.View.test.Objects.PlayerEntity;
import main.java.View.test.Objects.PlayerModels.CirclePlayer;
import main.java.View.test.View.MainFrame;

public class Main {

  public static void main(String[] args) {
    GraphicEngine graphics = new GraphicEngine();
    GameControl game_control = new GameControl();
    GameEngine game_engine = new GameEngine(game_control, graphics);
    game_engine.set_max_ups(60);
    InputMapper input = new InputMapper();
    graphics.addKeyListener(input);
    game_control.set_engine(game_engine);
    game_control.set_debug_mode(true);
    game_control.set_input_mapper(input);
    game_control.set_player(new PlayerEntity(new CirclePlayer()));
    MainFrame mainFrame = new MainFrame(graphics);
    mainFrame.setVisible(true);
    game_engine.init();
  }

}
