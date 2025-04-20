package org.VMC;

import jakarta.xml.bind.JAXBException;
import org.Game.Hand;
import org.Game.Player;
import org.ServerClient.Client.User;

import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private final Controller _controller;
    private final User _user;
    private boolean _isHaveChanges = false;
    private boolean _isQuit = false;

    public Model(Controller controller){
        _controller = controller;
        _user = new User();
    }
    public void Update() throws IOException, JAXBException {
        String input = _controller.GetInput();
        if (input != null){
            _user.SendMessage(input);
        }
        if (_user.IsHaveMessages()){
            _user.ReadMessage();
            _isHaveChanges = true;
        }
        else{
            _isHaveChanges = false;
        }
        if (_user.GetAndResetIsQuit() || _user.IsConnectionClosed()){
            _user.CloseSocket();
            _isQuit = true;
        }
    }
    public ArrayList<Player> GetPlayers(){
        return _user.GetPlayers();
    }
    public Player GetMyPlayer(){
        return _user.GetMyPlayer();
    }
    public Hand GetTable(){
        return _user.GetTable();
    }
    public boolean IsHaveChanges(){
        return _isHaveChanges;
    }
    public int GetBetNow(){
        int bet = 0;
        for (Player player : _user.GetPlayers()){
            if (player.IsActive()){
                bet = Math.max(bet, player.GetBet());
            }
        }
        return bet;
    }
    public boolean GetAndResetIsQuit(){
        boolean isQuit = _isQuit;
        _isQuit = false;
        return isQuit;
    }
}
