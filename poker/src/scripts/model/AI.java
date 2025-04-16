package scripts.model;


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
        if (_type == 1){
            return game.GetBetNow();
        }
        return 0;
    }
}
