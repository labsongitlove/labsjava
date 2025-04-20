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
    private int _step = 0;
    private int _status = 0; //0 - finished; 1 - waiting bet; 2 - waiting next step
    private ArrayList<Player> _killedPlayers = new ArrayList<>();
    private GameRules _gameRules;
    private BetsManager _betsManager = new BetsManager();

    public Game(){
        _players = new ArrayList<>();
        _table = new Hand();
        _gameRules = new GameRules(0);
    }
    public Game(ArrayList<Player> players, int type){
        _type = type;
        _players = players;
        _table = new Hand();
        _gameRules = new GameRules(type);
    }
    public Game(ArrayList<Player> players, int type, int seed){
        _type = type;
        _players = players;
        _table = new Hand();
        _gameRules = new GameRules(type, seed);
    }
    public void NextStep(){
        if (_type == 0){
            _status = _gameRules.NextStep(_step, _table, _players, _killedPlayers);
            if (_status == 0){
                _step = 0;
                _betsManager.Final(_players, _killedPlayers, _table, _gameRules.GetMaxCards());
            }
            else{
                _betsManager.NextStep(_type, _step, _players);
                _step++;
            }
        }
    }

    public void DoBet(Player playerBet, int money){
        _betsManager.DoBet(playerBet, money);
    }

    public boolean TryPrebets (){
        _status = _betsManager.TryPrebets(_players);
        return _betsManager.GetAndResetIsWasPrebets();
    }

    public boolean IsActiveMoreOne(){
        int activePlayersNum = 0;
        for (Player player : _players){
            if (player.IsActive())
                activePlayersNum++;
        }
        return activePlayersNum > 1;
    }

    public Player FindPlayer(String name){
        for (Player player: _players){
            if (player.GetName().equals(name)){
                return player;
            }
        }
        return null;
    }
    public void AddPlayer(Player player){
        player.SetActive(false);
        _players.add(player);
    }

    public Player KillPlayer(Player player){
        player.SetActive(false);
        if (_step == 3){
            _players.remove(player);
            return player;
        }
        _killedPlayers.add(player);
        player.PlusMoney(-player.GetBet());
        return player;
    }

    public ArrayList<Player> KillPlayers(ArrayList<Player> players){
        ArrayList<Player> playersKilled = new ArrayList<>();
        for (Player player : players){
            playersKilled.add(KillPlayer(player));
        }
        return playersKilled;
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
        return _players.get(_betsManager.GetBetPlayerIndexNow());
    }
    public int GetMaxCards(){
        return _gameRules.GetMaxCards();
    }
    public int GetBetNow(){
        return _betsManager.GetBetNow();
    }
}
