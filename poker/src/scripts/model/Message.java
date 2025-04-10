package scripts.model;

import scripts.user.User;

import java.util.ArrayList;

public class Message {
    int _type = 0;
    int _value = 0;
    ArrayList<Hand> _hands; //0 - table, 1 - your
    ArrayList<User> _players;

    public Message(int type, int value) {
        _type = type;
        _value = value;
    }
    public Message(int type, int value, ArrayList<Hand> hands, ArrayList<User> players) {
        _type = type;
        _value = value;
        _hands = hands;
        _players = players;
    }

    public int GetType(){
        return _type;
    }
    public int GetValue(){
        return _value;
    }
    public ArrayList<Hand> GetHands(){
        return _hands;
    }
    public ArrayList<User> GetPlayers(){
        return _players;
    }
}
