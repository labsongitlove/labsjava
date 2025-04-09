package scripts.model;

import java.util.ArrayList;

public class Player {
    int _money = 0;
    int _number = 0;
    Hand _hand;
    String _name;
    boolean _isActive;
    public Player(int money, int number, Hand hand, ArrayList<User> players){
        _money = money;
        _number = number;
        _hand = hand;
    }
    public boolean IsActive(){
        return _isActive;
    }
    public String GetName(){
        return _name;
    }
    public Hand GetHand(){
        return _hand;
    }
    public int GetMoney(){
        return _money;
    }
}
