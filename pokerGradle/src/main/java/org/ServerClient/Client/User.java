package org.ServerClient.Client;

import jakarta.xml.bind.JAXBException;

import org.Game.Hand;
import org.Game.Player;
import org.ServerClient.Message;

import java.util.ArrayList;

public class User {
    private SocketUser _socket;
    private Thread _socketThread;
    private final MessagesHandlerUser _messagesHandlerUser = new MessagesHandlerUser();
    private Hand _table;
    private Player _myPlayer;
    private ArrayList<Player> _players = new ArrayList<Player>();

    public User(){
        _socket = new SocketUser();
    }
    public void SendMessage(String input) throws JAXBException{
        Message message = _messagesHandlerUser.MakeMessage(input);
        if (_socketThread == null || _socket.ConnectionIsClosed()){
            _socket = new SocketUser();
            _socketThread = new Thread(_socket);
            _socketThread.start();
        }
        _socket.AddMessage(message);
    }
    public boolean IsHaveMessages(){
        return _socket.IsHaveMessages();
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
    public Hand GetTable(){
        return _table;
    }
    public Player GetMyPlayer(){
        return _myPlayer;
    }
    public ArrayList<Player> GetPlayers(){
        return _players;
    }
    public boolean GetAndResetIsQuit(){
        return _messagesHandlerUser.GetAndResetIsQuit();
    }
    public boolean IsConnectionClosed(){
        return _socket.ConnectionIsClosed();
    }
}