package org.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class BetsManager {
    private HashMap<Player, Integer> _preBets = new HashMap<>();
    private int _betPlayerIndexNow;
    private boolean _isFirstBetInStep;
    private int _betNow;
    private int _dealerPlayerIndexNow;
    private boolean _isWasPrebets;

    public BetsManager(){

    }
    public void NextStep(int type, int step, ArrayList<Player> players){
        if (type == 0){
            if (step == 0){
                Start(players);
            }
            else{
                _isFirstBetInStep = true;
                CleanBetStatuses(players);
                _betPlayerIndexNow = _dealerPlayerIndexNow;
            }
        }
    }
    public void DoBet(Player playerBet, int money){
        if (playerBet.IsActive()){
            _preBets.put(playerBet, money);
        }
    }
    public int TryPrebets (ArrayList<Player> players){
        _isWasPrebets = false;
        int num = GetNumActivePlayer(0, _betPlayerIndexNow, players);
        _betPlayerIndexNow = num;
        while (_preBets.containsKey(players.get(_betPlayerIndexNow)))
        {
            _isWasPrebets = true;
            Player playerBet = players.get(_betPlayerIndexNow);
            int money = _preBets.get(playerBet);
            if (_isFirstBetInStep){
                if (money < _betNow && money < playerBet.GetMoney()){
                    playerBet.SetBetStatus(1);
                    playerBet.SetActive(false);
                }
                else{
                    _isFirstBetInStep = false;
                    playerBet.SetBetStatus(2);
                    playerBet.SetBet(money);
                }
            }
            else if (money > _betNow && money <= playerBet.GetMoney()){
                CleanBetStatuses(players);
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
            num = GetNumActivePlayer(1, _betPlayerIndexNow, players);
            _preBets.remove(playerBet);
            _betNow = Math.max(_betNow, money);
            _betPlayerIndexNow = num;
            if (players.get(num).GetBetStatus() == 2){
                return 2;
            }
            if (_preBets.containsKey(players.get(_betPlayerIndexNow)) &&
                    _preBets.get(players.get(_betPlayerIndexNow)) < _betNow &&
                    _preBets.get(players.get(_betPlayerIndexNow)) != players.get(_betPlayerIndexNow).GetMoney()){
                _preBets.remove(players.get(_betPlayerIndexNow));
            }
        }
        return 1;
    }
    private void CleanBetStatuses(ArrayList<Player> players){
        for (Player player : players){
            player.SetBetStatus(0);
        }
    }
    private void Start(ArrayList<Player> players){
        _isFirstBetInStep = true;
        _betNow = 0;
        CleanBetStatuses(players);

        for (Player player : players){
            player.SetActive(true);
            player.ResetBet();
        }
        _dealerPlayerIndexNow = (_dealerPlayerIndexNow + 1) % players.size();
        _betPlayerIndexNow = _dealerPlayerIndexNow;

        int num = GetNumActivePlayer(1, _betPlayerIndexNow, players);
        players.get(num).SetBet(25);
        int num2 = GetNumActivePlayer(1, num, players);
        players.get(num2).SetBet(50);
        _betNow = Math.max(players.get(num).GetBet(), players.get(num2).GetBet());
    }
    public void Final(ArrayList<Player> players, ArrayList<Player> killedPlayers, Hand table, int maxCards){
        ComparatorCards comp = new ComparatorCards();
        ArrayList<Player> activePlayers = new ArrayList<>();
        for (Player player : players){
            if (player.IsActive())
                activePlayers.add(player);
        }
        if (activePlayers.isEmpty()){
            return;
        }
        ArrayList<Player> winners = comp.GetBestPlayers(activePlayers, table, maxCards);

        int bank = GetBank(players);
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

        for (Player player : players){
            if (!winners.contains(player) && !killedPlayers.contains(player)){
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
    private int GetNumActivePlayer(int increment, int startPoint, ArrayList<Player> players){
        int num = startPoint;
        for (int i = increment; i < players.size(); i++){
            num = (startPoint + i) % players.size();
            Player player = players.get(num);
            if (player.IsActive()) { break; }
        }
        return num;
    }
    public int GetBank(ArrayList<Player> players){
        int sum = 0;
        for (Player player : players){
            sum += player.GetBet();
        }
        return sum;
    }
    public boolean GetAndResetIsWasPrebets(){
        return _isWasPrebets;
    }
    public int GetBetNow(){
        return _betNow;
    }
    public int GetBetPlayerIndexNow(){
        return _betPlayerIndexNow;
    }
}
