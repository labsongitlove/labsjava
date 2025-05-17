package org.Game;

import java.util.ArrayList;
import java.util.Collections;

public class ChangeProcent {
    public static double Flash(Hand handPlusTable, int maxCards) {
        ArrayList<Integer> cards = handPlusTable.GetCardsSuits();
        Collections.sort(cards);
        int counter = 1;
        int max = 1;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (!cards.get(i).equals(cards.get(i + 1))) {
                max = Math.max(max, counter);
                counter = 0;
            }
            counter++;
        }
        max = Math.max(max, counter);
        double chance = 1;
        int iter = 0;
        for (int i = max; i < 5; i++){
            chance *= (double) (13 - i) * Math.max(0, maxCards - cards.size() - iter) / (52 - cards.size() - iter);
            iter++;
        }
        return chance;
    }
    public static double Street(Hand handPlusTable, int maxCards){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        int counter;
        int max = 1;
        for (int i = 0; i < cards.size(); i++) {
            counter = 1;
            for (int k = i + 1; k < cards.size(); k++){
                if (cards.get(k) >= cards.get(i) + 5)
                    break;
                if(cards.get(k).equals(cards.get(k - 1)))
                    counter--;
                counter++;
            }
            max = Math.max(max, counter);
        }
        double chance = 1;
        int iter = 0;
        for (int i = max; i < 5; i++){
            chance *= (double) 4 * Math.max(0, maxCards - cards.size() - iter) / (52 - cards.size() - iter);
            iter++;
        }
        return chance;
    }
    public static double ThreeCards(Hand handPlusTable, int maxCards){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        int max = 1;
        int counter = 1;
        for(int i = 0; i < cards.size() - 1; i++){
            if (cards.get(i).equals(cards.get(i + 1))){
                counter++;
                max = Math.max(max, counter);
            }
            else {
                counter = 1;
            }
        }
        double chance = 1;
        double iter = 0;
        for (int i = max; i < 3; i++){
            chance *= (double) (4 - i) * Math.max(0, maxCards - cards.size() - iter) / (52 - cards.size() - iter);
            iter++;
        }
        return chance;
    }
    public static boolean IsDouble(Hand handPlusTable, int maxCards){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        for(int i = 0; i < cards.size() - 1; i++){
            if (cards.get(i).equals(cards.get(i + 1))){
                return true;
            }
        }
        return false;
    }
}
