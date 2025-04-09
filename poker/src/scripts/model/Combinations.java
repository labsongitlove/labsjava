package scripts.model;

import java.util.ArrayList;
import java.util.Collections;

public class Combinations {
    public static int GetComb(Hand handPlusTable){
        boolean isFlash = IsFlash(handPlusTable);
        boolean isStreet = IsStreet(handPlusTable);
        int maxCardWithOneValue = MaxCardWithOneValue(handPlusTable);
        int pairsCount = PairsCount(handPlusTable);
        int maxCardValue = MaxCardValue(handPlusTable);

        if (isFlash && isStreet && maxCardValue == 13){
            return 10;
        }
        if (isFlash && isStreet){
            return 9;
        }
        if (maxCardWithOneValue == 4){
            return 8;
        }
        if (maxCardWithOneValue == 3 && pairsCount == 1){
            return 7;
        }
        if (isFlash){
            return 6;
        }
        if (isStreet){
            return 5;
        }
        if (maxCardWithOneValue == 3){
            return 4;
        }
        if (pairsCount == 2){
            return 3;
        }
        if (pairsCount == 1){
            return 2;
        }
        return 1;
    }
    private static boolean IsFlash(Hand handPlusTable){
        ArrayList<Integer> cards = handPlusTable.GetCardsSuits();
        Collections.sort(cards);
        int counter = 1;
        int max = 1;
        for(int i = 0; i < cards.size() - 1; i++){
            if (cards.get(i) != cards.get(i + 1)){
                if (max < counter){
                    max = counter;
                }
                counter = 0;
            }
            counter++;
        }
        return max >= 5;
    }
    private static boolean IsStreet(Hand handPlusTable){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        int counter = 1;
        int max = 1;
        for(int i = 0; i < cards.size() - 1; i++){
            if (cards.get(i) == cards.get(i + 1)){
                counter--;
            }
            else if (cards.get(i) + 1 != cards.get(i + 1)){
                if (max < counter){
                    max = counter;
                }
                counter = 0;
            }
            counter++;
        }
        return max >= 5;
    }
    private static int MaxCardWithOneValue(Hand handPlusTable){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        int max = 1;
        int now = 1;
        for(int i = 0; i < cards.size() - 1; i++){
            if (cards.get(i) == cards.get(i + 1)){
                now++;
                if (max < now){
                    max = now;
                }
            }
            else {
                now = 1;
            }
        }
        return max;
    }
    private static int PairsCount(Hand handPlusTable){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        int max = 0;
        int now = 1;
        for(int i = 0; i < cards.size() - 1; i++){
            if (cards.get(i) == cards.get(i + 1)){
                now++;
            }
            else {
                if (now == 2){
                    max++;
                }
                now = 1;
            }
        }
        if (now == 2){
            max++;
        }
        return max;
    }
    private static int MaxCardValue(Hand handPlusTable){
        ArrayList<Integer> cards = handPlusTable.GetCardsValues();
        Collections.sort(cards);
        return cards.get(cards.size() - 1);
    }
}
