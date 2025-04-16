package scripts.user;

import scripts.model.Card;
import scripts.model.Hand;
import scripts.model.Message;
import scripts.model.Player;

import java.util.ArrayList;

public class MessagesHandlerUser {
    Hand _table;
    Player _myPlayer;
    ArrayList<Player> _players = new ArrayList<Player>();
    int myNum;
    public MessagesHandlerUser(){

    }
    public void Parsing(Message message){
        if (message.GetType() == 0){
            _table = message.GetTable();
            _players = message.GetPlayers();
            _myPlayer = FindMyPlayer(_players);
            Print();
        }
        if (message.GetType() == 2){
            myNum = message.GetValue();
        }
    }
    public Message MakeMessage(String input){
        String[] parsingInput = input.split(" ");
        if (parsingInput.length > 1 && parsingInput[0].equals("join")){
            return new Message(0, 0, parsingInput[1]);
        }
        else if (parsingInput.length > 1 && parsingInput[0].equals("bet")){
            int money = 0;
            try {
                money = Integer.parseInt(parsingInput[1]);
            } catch (NumberFormatException e){
                return null;
            }
            return new Message(1, money, "");
        }
        return null;
    }

    private Player FindMyPlayer(ArrayList<Player> players){
        for (Player player : players){
            if (myNum == player.GetNumber()){
                return player;
            }
        }
        return new Player();
    }
    private void Print(){
        StringBuilder string = new StringBuilder();
        for (Card card : _table.GetCards()){
            string.append(card.GetValue()).append("(").append(card.GetSuit()).append(") ");
        }
        System.out.println("\nTable: " + string);

        string = new StringBuilder();
        for (Card card : _myPlayer.GetHand().GetCards()){
            string.append(card.GetValue()).append("(").append(card.GetSuit()).append(") ");
        }
        System.out.println("\nMy: " + string + "Bet: " + _myPlayer.GetBet() + " Money: " + _myPlayer.GetMoney());

        string = new StringBuilder();
        for (Player player : _players){
            if (!player.equals(_myPlayer)){
                string.append(player.GetName());
                string.append(": ");
                for (Card card : player.GetHand().GetCards()){
                    string.append(card.GetValue()).append("(").append(card.GetSuit()).append(") ");
                }
                string.append("Bet: ");
                string.append(player.GetBet());
                string.append(" Money: ");
                string.append(player.GetMoney());
                string.append(" ");
            }
        }
        System.out.println("\nPlayers: " + string);
    }
}
