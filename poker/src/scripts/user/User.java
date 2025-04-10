package scripts.user;

import scripts.model.Hand;
import scripts.model.Message;
import scripts.model.Player;

import java.util.ArrayList;

public class User {
    Hand _table;
    Player _myPlayer;
    ArrayList<Player> _players = new ArrayList<Player>();
    SocketUser _socket;
    public User(Hand table, Player myPlayer, ArrayList<Player> players){
        _table = table;
        _myPlayer = myPlayer;
        _players = players;

        _socket = new SocketUser();
        _socket.start();
    }

    public void Update(Message message){

    }
}