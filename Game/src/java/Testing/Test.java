package Testing;

import Model.GameObject;
import Controller.GameControl;
import Controller.GameEngine;
import View.*;

public class Test {

    public static void main(String[] args) {
        GraphicEngine graphics = new GraphicEngine();
        GameControl game_control = new GameControl();
        GameEngine game_engine = new GameEngine(game_control, graphics);
        game_engine.set_max_ups(60);
        InputAdapter input = new InputAdapter();
        graphics.addKeyListener(input);
        game_control.set_engine(game_engine);
        game_control.set_debug_mode(true);
        game_control.set_input_mapper(input);
        game_control.add_object(new GameObject());
        //game_control.set_player(new PlayerEntity("tuMhorenito19",new HR75()));
        MainFrame mainFrame = new MainFrame(graphics);
        mainFrame.setVisible(true);
        game_engine.init();
    }

}
