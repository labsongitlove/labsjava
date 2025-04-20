package org.ServerClient.Client;

import org.Game.*;
import org.ServerClient.Message;

import java.util.ArrayList;

public class MessagesHandlerUser {
    private Hand _table;
    private Player _myPlayer;
    private ArrayList<Player> _players = new ArrayList<Player>();
    private int myNum;

    private boolean _isQuit = false;

    public void Parsing(Message message){
        if (message.GetType() == 0){
            _table = message.GetTable();
            _players = message.GetPlayers();
            _myPlayer = FindMyPlayer(_players);
        }
        if (message.GetType() == 2){
            myNum = message.GetValue();
        }
    }
    public Message MakeMessage(String input){
        String[] parsingInput = input.split(" ");
        if (parsingInput.length > 1 && parsingInput[0].equals("join")){
            return new Message(2, 0, parsingInput[1]);
        }
        else if (parsingInput.length > 1 && parsingInput[0].equals("bet")){
            int money;
            try {
                money = Integer.parseInt(parsingInput[1]);
            } catch (NumberFormatException e){
                return null;
            }
            return new Message(1, money, "");
        }
        else if (parsingInput.length > 0 && parsingInput[0].equals("quit")){
            _isQuit = true;
            return new Message(4, 0, "quit");
        }
        return null;
    }

    private Player FindMyPlayer(ArrayList<Player> players){
        for (Player player : players){
            if (myNum == player.GetNumber()){
                return player;
            }
        }
        return new Player();
    }

    public Hand GetTable(){
        return _table;
    }
    public Player GetMyPlayer(){
        return _myPlayer;
    }
    public ArrayList<Player> GetPlayers(){
        return _players;
    }
    public boolean GetAndResetIsQuit(){
        boolean isQuit = _isQuit;
        _isQuit = false;
        return isQuit;
    }
}
