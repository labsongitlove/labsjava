package scripts.model;

import jakarta.xml.bind.annotation.*;
import scripts.user.User;

import java.util.ArrayList;

@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    @XmlAttribute(name = "Money")
    int _money = 0;
    @XmlAttribute(name = "Number")
    int _number = 0;
    @XmlElement(name = "Hand")
    Hand _hand;
    @XmlAttribute(name = "Name")
    String _name;
    @XmlAttribute(name = "IsActive")
    boolean _isActive;
    public Player(){}
    public Player(String name, int money, int number, Hand hand){
        _name = name;
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
