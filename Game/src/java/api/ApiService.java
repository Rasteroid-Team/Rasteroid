package api;

import View.Objects.ObjectModels.Players.PlayerModel;
import View.Resources;

public class ApiService {

        public enum PLAYER_ID{

                HR75("pl:id:hr75"),
                PHOENIX("pl:id:phoenix");

                String id;
                PLAYER_ID(String playerId) {
                        id = playerId;
                }
                public String getId(){
                        return id;
                }
        }

        public static PLAYER_ID getPlayerById(String id){
                switch (id) {
                        case "pl:id:hr75": return PLAYER_ID.HR75;
                        case "pl:id:phoenix": return PLAYER_ID.PHOENIX;
                        default: return null;
                }
        }

        //TODO: Make this method learn how to return a PlayerModel, only in pc version
        public static PlayerModel getPlayerModel(PLAYER_ID id) {
                switch (id){
                        case HR75 -> {
                                return Resources.PLAYER_HR75();
                        }
                        case PHOENIX -> {
                                return Resources.PLAYER_PHOENIX();
                        }
                }
                return null;
        }

}
