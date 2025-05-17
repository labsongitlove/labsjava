package org.Game;

import org.ServerClient.Server.Status;

import java.util.ArrayList;
import java.util.Random;

public class GameRules {
    private final int _type;
    private final Random _random;
    private int _maxCards;
    private ArrayList<Card> _cards;

    public GameRules(int type){
        _type = type;
        _random = new Random();
        SetParams();
    }
    public GameRules(int type, int seed){
        _type = type;
        _random = new Random(seed);
        SetParams();
    }

    private void SetParams(){
        if (_type == 0){
            _maxCards = 7;
        }
    }

    public Status NextStep(int stepNow, Hand table, ArrayList<Player> players, ArrayList<Player> killedPlayers){
        if (!IsActiveMoreOne(players) && stepNow != 0 || players.size() < 2)
            return Status.FINISHED;
        if (_type == 0){
            switch (stepNow){
                case 0:
                    return Start(table, players, killedPlayers);
                case 1:
                    table.SetCards(new ArrayList<>());
                    table.AddCard(CardChoice());
                    table.AddCard(CardChoice());
                    table.AddCard(CardChoice());
                    return Status.WAITING_BET;
                case 2, 3:
                    table.AddCard(CardChoice());
                    return Status.WAITING_BET;
                case 4:
                    return Status.FINISHED;
            }
        }
        return Status.NULL;
    }
    public Status Start(Hand table, ArrayList<Player> players, ArrayList<Player> killedPlayers) {
        if (_type == 0) {
            for (Player player : killedPlayers){
                players.remove(player);
            }
            killedPlayers.clear();

            table.SetCards(new ArrayList<>());
            _cards = new ArrayList<>();
            for (int suit = 1; suit < 5; suit++){
                for (int value = 1; value < 14; value++){
                    _cards.add(new Card(suit, value));
                }
            }
            for (int i = 0; i < players.size(); i++){
                ArrayList<Card> cards = new ArrayList<>();
                cards.add(CardChoice());
                cards.add(CardChoice());
                players.get(i).SetHand(new Hand(cards));
            }

            for (Player player : players){
                player.SetActive(true);
            }
            return Status.WAITING_BET;
        }
        return Status.NULL;
    }

    public Card CardChoice(){
        if (_cards.isEmpty()){ return null; }

        int num = _random.nextInt(_cards.size());
        Card card = _cards.get(num);
        _cards.remove(num);
        return card;
    }

    public int GetMaxCards(){
        return _maxCards;
    }
    private boolean IsActiveMoreOne(ArrayList<Player> players){
        int activePlayersNum = 0;
        for (Player player : players){
            if (player.IsActive())
                activePlayersNum++;
        }
        return activePlayersNum > 1;
    }
}
