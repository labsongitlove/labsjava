package org.ServerClient.Server;

import org.ServerClient.Message;
import org.Game.*;

import java.util.ArrayList;

public class MessagesHandlerServer {
    private Game _game;

    public MessagesHandlerServer(Game game){
        _game = game;
    }

    public void UpdateGame(Game game){
        _game = game;
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
    public boolean IsMessageRegistrationOrLogin(Message message){
        return message.GetType() == 2 || message.GetType() == 3;
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
    public String GetTextInMessage(Message message){
        return message.GetText().split(" ")[0];
    }
    public boolean IsQuitMessage(Message message){
        return message.GetType() == 4;
    }
}
