package org.VMC;

import org.Game.Card;
import org.Game.Hand;
import org.Game.Player;

import java.util.ArrayList;

public class View {
    private final Model _model;

    public View(Model model){
        _model = model;
    }
    public void Update(){
        if (_model.IsHaveChanges()){
            Print();
        }
        if (_model.GetAndResetIsQuit()){
            System.out.println("Connection is closed.");
        }
    }
    public void Print(){
        Hand table = _model.GetTable();
        Player myPlayer = _model.GetMyPlayer();
        ArrayList<Player> players = _model.GetPlayers();

        StringBuilder string;
        System.out.println("\n\n\n\n\n\n");
        if (table != null){
            string = StringBuilderForHand(table);
            System.out.println("\nTable: " + string + ". Bet now: " + _model.GetBetNow());
        }
        if (myPlayer != null){
            string = StringBuilderForPlayer(myPlayer);
            System.out.println("\nMy: " + string);
        }
        if (players != null && !players.isEmpty()){
            string = new StringBuilder();
            for (Player player : players){
                if (!player.equals(myPlayer)){
                    StringBuilder stringInfo = StringBuilderForPlayer(player);
                    string.append("\n").append(player.GetName()).append(": ").append(stringInfo);
                }
            }
            System.out.println("\nOther Players: " + string);
        }
    }
    private String FormatCardPrint(Card card){
        if (card.GetSuit() == 0 && card.GetValue() == 0){
            return "-";
        }
        StringBuilder string = new StringBuilder();
        String color = switch (card.GetSuit()) {
            case 1 -> "\033[4;31m";
            case 2 -> "\033[4;32m";
            case 3 -> "\033[4;33m";
            case 4 -> "\033[4;34m";
            default -> "";
        };
        int valueInt = card.GetValue();
        String value = switch (valueInt){
            case 10 -> "J";
            case 11 -> "Q";
            case 12 -> "K";
            case 13 -> "A";
            default -> Integer.toString(valueInt + 1);
        };
        string.append(color).append(value).append("\033[0m");
        return string.toString();
    }
    private StringBuilder StringBuilderForPlayer(Player player){
        StringBuilder string = StringBuilderForHand(player.GetHand());
        if (!player.IsActive())
            string.append("\033[0;105m");

        string.append("Bet: ").append(player.GetBet()).append(" Money: ").append(player.GetMoney());
        if (!player.IsActive())
            string.append("\033[0m");

        return string;
    }
    private StringBuilder StringBuilderForHand(Hand hand){
        StringBuilder string = new StringBuilder();
        for (Card card : hand.GetCards()){
            string.append(FormatCardPrint(card)).append(" ");
        }
        return string;
    }
}
