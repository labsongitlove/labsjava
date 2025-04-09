package scripts.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> _cards;
    public Hand(){}
    public Hand(ArrayList<Card> cards){
        _cards = cards;
    }
    public Hand(ArrayList<Card> cards1, ArrayList<Card> cards2){
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.addAll(cards1);
        cards.addAll(cards2);
        _cards = cards;
    }
    public void SetCards(ArrayList<Card> cards){
        _cards = cards;
    }
    public ArrayList<Card> GetCards(){
        return _cards;
    }
    public ArrayList<Integer> GetCardsValues(){
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (Card card : _cards){
            values.add(card.GetValue());
        }
        return values;
    }
    public ArrayList<Integer> GetCardsSuits(){
        ArrayList<Integer> suits = new ArrayList<Integer>();
        for (Card card : _cards){
            suits.add(card.GetSuit());
        }
        return suits;
    }
}
