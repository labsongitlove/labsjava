package org.VMC;

import jakarta.xml.bind.JAXBException;
import org.Game.Hand;
import org.Game.Player;
import org.ServerClient.Client.MessagesHandlerUser;
import org.ServerClient.Client.SocketUser;
import org.ServerClient.Message;

import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private final Controller _controller;
    private boolean _isHaveChanges = false;
    private boolean _isQuit = false;

    private SocketUser _socket;
    private Thread _socketThread;
    private final MessagesHandlerUser _messagesHandlerUser = new MessagesHandlerUser();
    private Hand _table;
    private Player _myPlayer;
    private ArrayList<Player> _players = new ArrayList<Player>();

    public Model(Controller controller){
        _controller = controller;

        _socket = new SocketUser();
    }
    public void Update() throws IOException, JAXBException {
        String input = _controller.GetInput();
        if (input != null){
            SendMessage(input);
        }
        if (_socket.IsHaveMessages()){
            ReadMessage();
            _isHaveChanges = true;
        }
        else{
            _isHaveChanges = false;
        }
        if (_messagesHandlerUser.GetAndResetIsQuit() || _socket.ConnectionIsClosed()){
            CloseSocket();
            _isQuit = true;
        }
    }
    public Hand GetTable(){
        return _table;
    }
    public Player GetMyPlayer(){
        return _myPlayer;
    }
    public ArrayList<Player> GetPlayers(){
        return _players;
    }
    public boolean IsHaveChanges(){
        return _isHaveChanges;
    }
    public int GetBetNow(){
        int bet = 0;
        for (Player player : _players){
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

    private void SendMessage(String input) throws JAXBException{
        Message message = _messagesHandlerUser.MakeMessage(input);
        if (_socketThread == null || _socket.ConnectionIsClosed()){
            _socket = new SocketUser();
            _socketThread = new Thread(_socket);
            _socketThread.start();
        }
        _socket.AddMessage(message);
    }

    public void ReadMessage() throws JAXBException {
        _messagesHandlerUser.Parsing(_socket.ReadMessage());
        _table = _messagesHandlerUser.GetTable();
        _myPlayer = _messagesHandlerUser.GetMyPlayer();
        _players = _messagesHandlerUser.GetPlayers();
    }

    public void CloseSocket(){
        if (!_socket.ConnectionIsClosed()){
            _socketThread.interrupt();
        }
        _socketThread = null;
        _socket = new SocketUser();
    }
}
