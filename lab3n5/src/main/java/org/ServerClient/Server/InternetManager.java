package org.ServerClient.Server;

import jakarta.xml.bind.JAXBException;
import org.Game.Game;
import org.Game.Hand;
import org.Game.Player;
import org.ServerClient.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class InternetManager implements AutoCloseable {
    private SocketController _socketNext;
    private HashMap<Integer, SocketController> _tokenSocketTable = new HashMap<>();
    private HashMap<Integer, Player> _tokenPlayerTable = new HashMap<>();;
    private HashMap<Player, AI> _playerAITable = new HashMap<>();;
    private MessagesHandlerServer _messagesHandlerServer;
    private int _status = 0; //0 - nothing, 1 - new user, 2 - have message
    private ServerSocket _serverSocket;
    private int _nextAvailableNum = 0;

    public InternetManager(Game game) throws IOException {
        _serverSocket = new ServerSocket(3345);
        _socketNext = new SocketController(_serverSocket);
        new Thread(_socketNext).start();

        _messagesHandlerServer = new MessagesHandlerServer(game);
    }
    public void UpdateGameRule(int type){

        _messagesHandlerServer.UpdateGame(new Game(new ArrayList<Player>(_tokenPlayerTable.values()), type));
    }
    public void SetGame(Game game){
        _messagesHandlerServer.UpdateGame(game);
    }
    public Player FindPlayerForName(String name){
        for (Player player : _tokenPlayerTable.values()){
            if (player.GetName().equals(name)){
                return player;
            }
        }
        return null;
    }
    public Player FindPlayerForToken(int token){
        return _tokenPlayerTable.get(token);
    }
    public void Update() throws JAXBException{
        for (SocketController socket : _tokenSocketTable.values()){
            if (socket.IsHaveMessages()){
                CheckMessage(socket.ReadMessage(), socket.GetToken());
                _status = 2;
            }
        }
        if (_socketNext.IsHaveMessages()){
            _status = 1;
        }
    }
    public Player Registration() throws JAXBException{
        while (_tokenSocketTable.get(_socketNext.GetToken()) != null){
            _socketNext.ResetToken();
        }
        _tokenSocketTable.put(_socketNext.GetToken(), _socketNext);
        Player player = new Player(_messagesHandlerServer.GetNameInMessage(_socketNext.ReadMessage()), 10000, _nextAvailableNum, new Hand());
        _tokenPlayerTable.put(_socketNext.GetToken(), player);

        _socketNext.AddMessage(new Message(2, _nextAvailableNum, player.GetName()));
        _nextAvailableNum++;

        _socketNext = new SocketController(_serverSocket);
        new Thread(_socketNext).start();
        _status = 0;

        return player;
    }
    public void UpdateInfo() throws JAXBException{ //TODO make "for players" and make "send ID" in message
        SendAllSafely(0, 0, "");
    }

    private void CheckMessage(Message message, int token){
        Player player = _tokenPlayerTable.get(token);
        _messagesHandlerServer.Parsing(message, player);
    }

    public Integer FindTokenOfPlayer(Player playerNeed){
        for (SocketController socket : _tokenSocketTable.values()){
            int token = socket.GetToken();
            Player player = _tokenPlayerTable.get(token);
            if (player == playerNeed){
                return token;
            }
        }
        return null;
    }

    private void SendAllSafely(int type, int value, String text) throws JAXBException{
        for (Player player : _tokenPlayerTable.values()){
            if (!IsAI(player)){
                int token = FindTokenOfPlayer(player);
                _tokenSocketTable.get(token).AddMessage(_messagesHandlerServer.MakeSafelyMessage(player, type, value, text));
            }
        }
    }
    public void SendResult() throws JAXBException{
        for (Player player : _tokenPlayerTable.values()){
            if (!IsAI(player)){
                int token = FindTokenOfPlayer(player);
                _tokenSocketTable.get(token).AddMessage(_messagesHandlerServer.MakeMessage(0, 0, ""));
            }
        }
    }

    @Override
    public void close() throws IOException{
        _serverSocket.close();
    }

    public void AddAIInTable(Player player, AI ai) {
        _nextAvailableNum++;
        _playerAITable.put(player, ai);
    }

    public void SetStatus(int status) { _status = status; }

    public int GetStatus(){
        return _status;
    }

    public int GetNextAvailableNum(){
        return _nextAvailableNum;
    }

    public AI GetAI(Player player){
        return _playerAITable.get(player);
    }

    public boolean IsAI(Player player){
        return _playerAITable.containsKey(player);
    }
}
