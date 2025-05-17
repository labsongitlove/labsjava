package org.VMC;

public class Main {
    public static void main(String[] args) throws Exception {
        Model model = new Model();
        try(Controller controller = new Controller(model)){
            View view = new View(model);
            while(true){
                controller.Update();
                model.Update();
                view.Update();
            }
        }
    }
}

/*ArrayList<Card> cards1 = new ArrayList<>();
        cards1.add(new Card(1, 8));
        cards1.add(new Card(2, 6));
        cards1.add(new Card(1, 9));
        cards1.add(new Card(3, 6));
        cards1.add(new Card(4, 4));
        Hand hand1 = new Hand(cards1);
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(3, 12));
        cards2.add(new Card(2, 8));
        Hand hand2 = new Hand(cards2);
        ArrayList<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(3, 9));
        cards3.add(new Card(4, 5));
        Hand hand3 = new Hand(cards3);
        ArrayList<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(3, 7));
        cards4.add(new Card(3, 10));
        Hand hand4 = new Hand(cards4);

        ComparatorCards comparatorCards = new ComparatorCards();
        ArrayList<Hand> hands = new ArrayList<>();
        hands.add(hand2);
        hands.add(hand3);
        hands.add(hand4);
        System.out.println(comparatorCards.GetBestHands(hands, hand1).get(0).GetCardsValues());*/