package scripts.model;

import java.util.ArrayList;

public class User {
    Hand _table;
    Player _myPlayer;
    ArrayList<Player> _players = new ArrayList<Player>();
    public User(Hand table, Player myPlayer, ArrayList<Player> players){
        _table = table;
        _myPlayer = myPlayer;
        _players = players;
    }

    public void Update(Message message){

    }
}
