package scripts.model;

import java.util.ArrayList;
import java.util.Collections;

public class ComparatorCards {
    private int IsFirstBest(Hand hand1, Hand hand2, Hand table){ //1 -- first >, 2 -- first ==, 3 -- first <
        Hand handPlusTable1 = new Hand(hand1.GetCards(), table.GetCards());
        Hand handPlusTable2 = new Hand(hand2.GetCards(), table.GetCards());
        int hand1Comb = Combinations.GetComb(handPlusTable1);
        int hand2Comb = Combinations.GetComb(handPlusTable2);
        if (hand1Comb == hand2Comb){
            return IsFirstBestValues(handPlusTable1, handPlusTable2);
        }
        if (hand1Comb > hand2Comb){
            return 1;
        }
        return 3;
    }
    private int IsFirstBestValues(Hand handPlusTable1, Hand handPlusTable2){ //1 -- first >, 2 -- first ==, 3 -- first <
        ArrayList<Integer> hand1Cards = handPlusTable1.GetCardsValues();
        ArrayList<Integer> hand2Cards = handPlusTable2.GetCardsValues();
        Collections.sort(hand1Cards);
        Collections.sort(hand2Cards);
        for (int i = hand1Cards.size() - 1; i >= 0; i--){
            if (hand1Cards.get(i) > hand2Cards.get(i)){
                return 1;
            }
            else if (hand1Cards.get(i) < hand2Cards.get(i)){
                return 3;
            }
        }
        return 2;
    }
    public ArrayList<Hand> GetBestHands(ArrayList<Hand> hands, Hand table){
        ArrayList<Hand> bestNow = new ArrayList<Hand>();
        bestNow.add(hands.get(0));
        for (int i = 1; i < hands.size(); i++){
            int result = IsFirstBest(bestNow.get(0), hands.get(i), table);
            if (result == 2){
                bestNow.add(hands.get(i));
            }
            if (result == 3){
                bestNow.clear();
                bestNow.add(hands.get(i));
            }
        }
        return bestNow;
    }
    public ArrayList<Player> GetBestPlayers(ArrayList<Player> players, Hand table){
        ArrayList<Player> bestPlayers = new ArrayList<>();
        ArrayList<Hand> hands = new ArrayList<>();
        for (Player player : players){
            hands.add(player.GetHand());
        }
        var bestHands = GetBestHands(hands, table);
        for (Hand hand : bestHands){
            for (Player player : players){
                if(player.GetHand() == hand){
                    bestPlayers.add(player);
                }
            }
        }
        return bestPlayers;
    }
}
