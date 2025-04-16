package org.VMC;

import org.ServerClient.Client.User;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        /*ArrayList<Card> cards1 = new ArrayList<Card>();
        cards1.add(new Card(1, 4));
        cards1.add(new Card(1, 2));
        cards1.add(new Card(1, 3));
        cards1.add(new Card(1, 4));
        cards1.add(new Card(1, 5));

        ArrayList<Card> cards2 = new ArrayList<Card>();
        cards2.add(new Card(1, 4));
        cards2.add(new Card(1, 2));
        cards2.add(new Card(1, 3));
        cards2.add(new Card(1, 4));
        cards2.add(new Card(1, 5));

        ArrayList<Card> cards3 = new ArrayList<Card>();
        cards3.add(new Card(1, 4));
        cards3.add(new Card(1, 2));
        cards3.add(new Card(1, 3));
        cards3.add(new Card(1, 4));
        cards3.add(new Card(1, 5));

        Hand hand1 = new Hand(cards1);
        Hand hand2 = new Hand(cards2);
        Hand hand3 = new Hand(cards3);

        ArrayList<Card> table = new ArrayList<Card>();
        table.add(new Card(3, 13));
        table.add(new Card(4, 12));
        table.add(new Card(3, 11));
        table.add(new Card(4, 10));
        table.add(new Card(3, 9));

        Hand tableHand = new Hand(table);

        var comp = new ComparatorCards();
        var hands = new ArrayList<Hand>();
        hands.add(hand1);
        hands.add(hand2);
        hands.add(hand3);

        var wonHand = comp.GetBestHands(hands, tableHand).get(0);
        var wonComb = Combinations.GetComb(new Hand(wonHand.GetCards(), tableHand.GetCards()));
        System.out.println(MessageFormat.format("{0}, {1}", wonHand.GetCardsValues(), wonComb));



        Player player1 = new Player("Jone", 100, 1, hand1);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player1);
        Message message = new Message(0, 0, "Jone", new Hand(), players);

        String xml = message.Marshal();
        System.out.println(xml);

        Message messageXml = Message.Unmarshal(xml);

        System.out.println(messageXml.GetPlayers().get(0).GetHand().GetCardsValues());



        //new SocketsController().start();*/

        User user = new User();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            while(true){
                if (reader.ready()){
                    user.SendMessage(reader.readLine());
                }
                user.Update();
            }
        }
        catch (IOException ignore){}
    }
}