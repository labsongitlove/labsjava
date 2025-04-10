package scripts.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Card")
@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    private final int _value;
    private final int _suit;
    public Card(){
        _value = 0;
        _suit = 0;
    }
    public Card(int suit, int value){
        _value = value;
        _suit = suit;
    }
    @XmlElement(name = "Value")
    public int getValue(){
        return _value;
    }
    @XmlElement(name = "Suit")
    public int getSuit(){
        return _suit;
    }
}
