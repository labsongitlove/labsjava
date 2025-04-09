package scripts.model;

public class Card {
    private final int _value;
    private final int _suit;
    public Card(int suit, int value){
        _value = value;
        _suit = suit;
    }
    public int GetValue(){
        return _value;
    }
    public int GetSuit(){
        return _suit;
    }
}
