package scripts.server;

import scripts.model.AI;
import scripts.model.Game;
import scripts.model.Message;
import scripts.model.Player;

import java.util.ArrayList;

public class MessagesHandlerServer {
    private Game _game;

    public MessagesHandlerServer(Game game){
        _game = game;
    }

    public void UpdateGame(Game game){

    }
    /*public Message GetMessage(){
        return new Message(0, 0, 0, _game.GetTable(), _game.GetPlayers());
    }*/
    public void Parsing(Message message, Player player){
        if (message.GetType() == 1){
            if (_game.GetStatus() == 1){
                _game.DoBet(player, message.GetValue());
            }
        }
    }
    public Message MakeSafelyMessage(Player playerTo, int type, int value, String text){
        ArrayList<Player> players = new ArrayList<>();
        players.add(playerTo);
        for (Player player : _game.GetPlayers()){
            if (player != playerTo){
                players.add(player.MakeSafelyCopy());
            }
        }
        return new Message(type, value, text, _game.GetTable(), players);
    }
    public Message MakeMessage(int type, int value, String text){
        return new Message(type, value, text, _game.GetTable(), _game.GetPlayers());
    }
    public String GetNameInMessage(Message message){
        return message.GetText();
    }
}
