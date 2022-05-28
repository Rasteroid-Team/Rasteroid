package Testing;

import Model.DynamicBody;
import Model.GameObject;
import Controller.GameControl;
import Controller.GameEngine;
import Model.Map;
import Model.Player;
import View.*;
import View.Interface.ErrorDialog;
import View.Interface.LoadingDialog;
import View.Interface.MainFrame;
import View.Resources;

public class Test {

    public static void main(String[] args) {
        //Loading res
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.setVisible(true);
        try {
            loadingDialog.get_thread().join();
            //Start game
            GraphicEngine graphics = new GraphicEngine();
            GameControl game_control = new GameControl();
            GameEngine game_engine = new GameEngine(game_control, graphics);
            game_engine.set_max_ups(60);
            InputAdapter input = new InputAdapter();
            graphics.addKeyListener(input);
            game_control.set_engine(game_engine);
            game_control.set_debug_mode(true);
            GameControl.add_object(new Map(Resources.MAP_SPACE()));
            GameControl.add_object(new Player(game_control, input, Resources.PLAYER_HR75()));
            GameControl.add_object(new AvtomatV1(game_control, new InputAdapter(), Resources.PLAYER_HR75()));
            MainFrame mainFrame = new MainFrame(graphics);
            mainFrame.setVisible(true);
            game_engine.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
            new ErrorDialog("Initializer", "Could not load resources");
        }



        //GraphicEngine graphics = new GraphicEngine();
        //GameControl game_control = new GameControl();
        //GameEngine game_engine = new GameEngine(game_control, graphics);
        //game_engine.set_max_ups(60);
        //InputAdapter input = new InputAdapter();
        //graphics.addKeyListener(input);
        //game_control.set_engine(game_engine);
        //game_control.set_debug_mode(true);
        //game_control.set_input_mapper(input);
        //game_control.add_object(new Map(new Space()));
        //game_control.add_object(new GameObject(new DynamicBody(), true, 100, true, new HR75()));
        //MainFrame mainFrame = new MainFrame(graphics);
        //mainFrame.setVisible(true);
        //game_engine.init();

    }

}
