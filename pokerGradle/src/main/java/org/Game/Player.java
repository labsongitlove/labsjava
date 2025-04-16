package org.Game;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    @XmlAttribute(name = "Money")
    private int _money = 0;
    @XmlAttribute(name = "Number")
    private int _number = 0;
    @XmlElement(name = "Hand")
    private Hand _hand = new Hand();
    @XmlAttribute(name = "Name")
    private String _name = "";
    @XmlAttribute(name = "Bet")
    private int _bet = 0;
    @XmlAttribute(name = "BetStatus")
    private int _betStatus = 0; //0 - nothing, 1 - call, 2 - raise
    @XmlAttribute(name = "IsActive")
    private boolean _isActive = true;

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
    public int GetBet() {return _bet;}
    public int GetBetStatus() {return _betStatus; }
    public int GetNumber() {return _number; }

    public void SetHand(Hand hand) { _hand = hand; }
    public void SetMoney(int money) { _money = money; }
    public void SetBet(int bet) { _bet = Math.min(_money, Math.max(bet, _bet)); }
    public void ResetBet() { _bet = 0; }
    public void SetActive(boolean isActive) { _isActive = _money != 0 && isActive; }
    public void SetBetStatus(int betStatus) { _betStatus = betStatus; }
    public void PlusMoney(int money){ _money += money; }

    public Player MakeSafelyCopy(){
        Player player = new Player(_name, _money, _number, new Hand());
        player.SetBet(_bet);
        player.SetBetStatus(_betStatus);
        player.SetActive(_isActive);
        return player;
    }
}
