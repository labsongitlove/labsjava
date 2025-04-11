package scripts.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "Card")
@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    @XmlAttribute(name = "Value")
    private final int _value;
    @XmlAttribute(name = "Suit")
    private final int _suit;
    public Card(){
        _value = 0;
        _suit = 0;
    }
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
