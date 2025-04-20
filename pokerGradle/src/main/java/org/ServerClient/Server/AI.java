package org.ServerClient.Server;


import org.Game.*;

import java.util.ArrayList;

public class AI {
    int _type;

    public AI(){
        _type = 0;
    }
    public AI(int type){
        _type = type;
    }
    public int DoBet(Player player, Game game) {
        if (_type == 0){
            return 50;
        }
        else if (_type == 1){
            return game.GetBetNow();
        }
        else if (_type == 2){
            Hand handPlusTable = new Hand(game.GetTable().GetCards(), player.GetHand().GetCards());
            double max = Math.max(Math.max(ChangeProcent.Flash(handPlusTable), ChangeProcent.Street(handPlusTable)), ChangeProcent.ThreeCards(handPlusTable));
            if (max >= 0.5){
                double betMult = max * 5 / 3 + 1;
                return (int) Math.max(game.GetBetNow(), player.GetBet() * betMult);
            }
            if (max >= 0.2){
                double betMult = max * 5 / 3 + 1;
                return (int) Math.max(game.GetBetNow(), player.GetBet() + 100 * betMult);
            }
            else if (max >= 0.05 || game.GetBetNow() == player.GetBet() || ChangeProcent.IsDouble(handPlusTable)){
                return game.GetBetNow();
            }
            return 0;
        }
        else if (_type == 3){
            Hand handPlusTable = new Hand(game.GetTable().GetCards(), player.GetHand().GetCards());
            double max = Math.max(Math.max(ChangeProcent.Flash(handPlusTable), ChangeProcent.Street(handPlusTable)), ChangeProcent.ThreeCards(handPlusTable));
            if (max >= 0.5){
                double betMult = max * 5 / 3 + 1;
                return (int) Math.max(game.GetBetNow(), player.GetBet() + 100 * betMult);
            }
            else if (game.GetBetNow() == player.GetBet() || ChangeProcent.IsDouble(handPlusTable)){
                return game.GetBetNow();
            }
            return 0;
        }
        return 0;
    }
}
