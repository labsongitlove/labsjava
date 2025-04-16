package org.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class Game {
    private Hand _table;
    private ArrayList<Player> _players;
    private ArrayList<Card> _cards;
    private int _type = 0; //0 - texas
    private int _seed = 0;
    private int _step = 0;
    private int _betPlayerIndexNow = 0;
    private int _betNow = 0;
    private boolean _isFirstBetInStep = true;
    private HashMap<Player, Integer> _preBets = new HashMap<>();
    private int _status = 0; //0 - finished; 1 - waiting bet; 2 - waiting next step
    private int _dealerPlayerIndexNow = 0;
    private Random _random;
    //synchronizedList
    public Game(){
        _seed = new Random().nextInt();
        _players = new ArrayList<>();
        _table = new Hand();
        _random = new Random();
    }
    public Game(ArrayList<Player> players, int type){
        _type = 0;
        _players = players;
        _table = new Hand();
        _seed = new Random().nextInt();
        _random = new Random(_seed);
    }
    public Game(ArrayList<Player> players, int type, int seed){
        _type = 0;
        _players = players;
        _table = new Hand();
        _seed = seed;
        _random = new Random(_seed);
    }

    public void NextStep(int seed){
        Random random = _random;
        _random = new Random(seed);
        NextStep();
        _random = random;
    }
    public void NextStep(){
        if (_type == 0){
            switch (_step){
                case 0:
                    _table.SetCards(new ArrayList<>());
                    _table.AddCard(CardChoice());
                    _table.AddCard(CardChoice());
                    _table.AddCard(CardChoice());
                    _step++;
                    _isFirstBetInStep = true;
                    _status = 1;
                    CleanBetStatuses();
                    _betPlayerIndexNow = _dealerPlayerIndexNow;
                    break;
                case 1, 2:
                    _table.AddCard(CardChoice());
                    _step++;
                    _isFirstBetInStep = true;
                    _status = 1;
                    CleanBetStatuses();
                    _betPlayerIndexNow = _dealerPlayerIndexNow;
                    break;
                case 3:
                    Final();
                    break;
            }
        }
    }
    public void Start(int seed){
        Random random = _random;
        _random = new Random(seed);
        Start();
        _random = random;
    }
    public void Start(){
        _step = 0;
        _isFirstBetInStep = true;
        _status = 1;
        _betNow = 0;
        CleanBetStatuses();

        _table = new Hand();
        _cards = new ArrayList<>();
        for (int suit = 1; suit < 5; suit++){
            for (int value = 1; value < 14; value++){
                _cards.add(new Card(suit, value));
            }
        }
        if (_type == 0){
            for (int i = 0; i < _players.size(); i++){
                ArrayList<Card> cards = new ArrayList<>();
                cards.add(CardChoice());
                cards.add(CardChoice());
                _players.get(i).SetHand(new Hand(cards));
            }
        }
        for (Player player : _players){
            player.SetActive(true);
            player.ResetBet();
        }
        _dealerPlayerIndexNow = (_dealerPlayerIndexNow + 1) % _players.size();
        _betPlayerIndexNow = _dealerPlayerIndexNow;
    }
    public Card CardChoice(int seed){
        Random random = _random;
        _random = new Random(seed);
        Card card = CardChoice(_seed);
        _random = random;
        return card;
    }
    public Card CardChoice(){
        Card card = null;
        if (_cards.isEmpty()){ return null; }

        int num = _random.nextInt(_cards.size());
        card = _cards.get(num);
        _cards.remove(num);
        return card;
    }

    public void DoBet(Player playerBet, int money){
        if (playerBet.IsActive()){
            _preBets.put(playerBet, money);
        }
    }

    public void TryPrebets (){
        while (_preBets.containsKey(_players.get(_betPlayerIndexNow)))
        {
            Player playerBet = _players.get(_betPlayerIndexNow);
            int money = _preBets.get(playerBet);
            if (_isFirstBetInStep){
                _isFirstBetInStep = false;
                playerBet.SetBetStatus(2);
                playerBet.SetBet(money);
            }
            else if (money > _betNow && money <= playerBet.GetMoney()){
                CleanBetStatuses();
                _preBets.clear();
                playerBet.SetBetStatus(2);
                playerBet.SetBet(money);
            }
            else if ((money == _betNow && money <= playerBet.GetMoney()) || (money < _betNow && money > playerBet.GetMoney())){
                playerBet.SetBetStatus(1);
                playerBet.SetBet(money);
            }
            else if (money < _betNow && money < playerBet.GetMoney()){
                playerBet.SetBetStatus(1);
                playerBet.SetActive(false);
            }
            if (_players.get(((_betPlayerIndexNow + 1) % _players.size())).GetBetStatus() == 2){
                _status = 2;
                _preBets.remove(playerBet);
                _betNow = Math.max(_betNow, money);
                break;
            }
            int num = 0;
            for (int i = 1; i < _players.size(); i++){
                num = (_betPlayerIndexNow + i) % _players.size();
                Player player = _players.get(num);
                if (player.IsActive()) { break; }
            }
            _preBets.remove(playerBet);
            _betNow = Math.max(_betNow, money);
            _betPlayerIndexNow = num;
            if (_preBets.containsKey(_players.get(_betPlayerIndexNow)) &&
                    _preBets.get(_players.get(_betPlayerIndexNow)) < _betNow &&
                    _preBets.get(_players.get(_betPlayerIndexNow)) != _players.get(_betPlayerIndexNow).GetMoney()){
                _preBets.remove(_players.get(_betPlayerIndexNow));
            }
        }
    }

    public boolean IsActiveMoreOne(){
        int activePlayersNum = 0;
        for (Player player : _players){
            if (player.IsActive())
                activePlayersNum++;
        }
        return activePlayersNum > 1;
    }

    private void CleanBetStatuses(){
        for (Player player : _players){
            player.SetBetStatus(0);
        }
    }

    private void Final(){
        _status = 0;
        ComparatorCards comp = new ComparatorCards();
        ArrayList<Player> activePlayers = new ArrayList<>();
        for (Player player : _players){
            if (player.IsActive())
                activePlayers.add(player);
        }
        if (activePlayers.isEmpty()){
            return;
        }
        ArrayList<Player> winners = comp.GetBestPlayers(activePlayers, _table);

        int bank = GetBank();
        int bankDistribution = bank;
        for (Player winner : winners){
            int factor = _betNow > 0 ? (Math.min(1, winner.GetMoney() / _betNow)) : 1;
            int share = (bank / winners.size()) * factor;
            bankDistribution -= share;
            winner.PlusMoney(share - winner.GetBet());
        }
        bank = bankDistribution;
        ArrayList<Integer> bankForLosers = new ArrayList<>();
        ArrayList<Player> losers = new ArrayList<>();

        for (Player player : _players){
            boolean isWinner = false;
            for (Player winner : winners){
                if (winner == player){
                    isWinner = true;
                }
            }
            if (!isWinner){
                losers.add(player);
                bankForLosers.add(0);
            }
        }
        while (bank > losers.size()){
            for (int i = 0; i < losers.size(); i++){
                Player loser = losers.get(i);
                int factor = _betNow > 0 ? (Math.min(1, loser.GetMoney() / _betNow)) : 1;
                int share = (bank / winners.size()) * factor;
                bankDistribution -= share;
                bankForLosers.set(i, bankForLosers.get(i) + share);
            }
            bank = bankDistribution;
        }
        for (int i = 0; i < losers.size(); i++){
            Player loser = losers.get(i);
            loser.PlusMoney(bankForLosers.get(i) - loser.GetBet());
        }
    }

    public int GetBank(){
        int sum = 0;
        for (Player player : _players){
            sum += player.GetBet();
        }
        return sum;
    }

    public Player FindPlayer(String name){
        for (Player player: _players){
            if (player.GetName().toLowerCase(Locale.ROOT) == name){
                return player;
            }
        }
        return null;
    }

    public void SetPlayers(ArrayList<Player> players){
        _players = players;
    }
    public void AddPlayer(Player player){
        _players.add(player);
    }

    public Hand GetTable(){
        return _table;
    }
    public ArrayList<Player> GetPlayers(){
        return _players;
    }
    public int GetStatus(){
        return _status;
    }
    public Player BetPlayerNow(){
        return _players.get(_betPlayerIndexNow);
    }

    public int GetBetNow(){
        return _betNow;
    }
}
