package Testing;

import Controller.*;
import Model.*;
import View.*;
import View.Interface.ErrorDialog;
import View.Interface.LoadingDialog;
import View.Interface.MainFrame;
import View.Objects.ObjectModels.Players.PlayerColors;
import View.Objects.ObjectModels.Players.PlayerModel;
import View.Resources;
import communications.CommunicationController;

public class Test {

    public static void main(String[] args) {
        //Loading res
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.setVisible(true);
        try {
            loadingDialog.get_thread().join();
            //Start game
            //Canvas donde se pintara el juego
            GraphicEngine graphics = new GraphicEngine();
            CommunicationController comuContr = new CommunicationController();
            ScreenConnectionController scrConnContr = new ScreenConnectionController(comuContr);
            PlayerConnectionController plyrConnContr = new PlayerConnectionController(comuContr);
            GamePhaseController phaseContr = new GamePhaseController();
            ConnectionController connController = new ConnectionController(comuContr, scrConnContr, plyrConnContr, phaseContr);
            SetUp setUp = new SetUp(graphics, connController, phaseContr);
            MainFrame mainFrame = new MainFrame(graphics);
            mainFrame.setVisible(true);
            setUp.start();
            setUp.join();

            //blucle while aqui, pensa a fe net arrays de objectes.
            while (phaseContr.getGamePhase() != GamePhaseController.GamePhase.NONE) {
                //Clase que actualiza los objectos del juego
                GameControl game_control = new GameControl();
                GameControl.communicationController = comuContr;
                Lobby lobby = new Lobby(graphics, phaseContr, comuContr);
                lobby.start();
                lobby.join();
                if (ConfigurationController.mainFrame) {
                    GameControl.gameRules = new GameRules(false, ConfigurationController.pcsInformation, comuContr);
                } else {
                    GameControl.gameRules = new GameRules(false, comuContr);
                }
                //Clase donde esta el loop del juego i se controlan los fps
                GameEngine game_engine = new GameEngine(game_control, graphics, connController, phaseContr, comuContr);
                game_engine.set_max_ups(60);
                InputAdapter input = new InputAdapter();
                graphics.addKeyListener(input);
                game_control.set_engine(game_engine);
                game_control.set_debug_mode(true);
                GameControl.add_object(new Map(Resources.MAP_SPACE()), 0);
                //GameControl.add_object(new Player(Resources.PLAYER_PHOENIX(), PlayerColors.cyan, ""));
                //GameControl.add_object(new AvtomatV1(Resources.PLAYER_HR75(), PlayerColors.red));
                game_engine.init();
                game_engine.join();
                GameControl.clearObjectList();
                if (ConfigurationController.mainFrame) {
                    plyrConnContr.sendStartingPcs();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            new ErrorDialog("Initializer", "Could not load resources");
        }

    }

}
