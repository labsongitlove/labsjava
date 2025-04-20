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
    private Thread _socketNextThread;
    private HashMap<Integer, SocketController> _tokenSocketTable = new HashMap<>();
    private HashMap<Integer, Thread> _tokenThreadTable = new HashMap<>();
    private HashMap<Integer, Player> _tokenPlayerTable = new HashMap<>();
    private HashMap<Player, AI> _playerAITable = new HashMap<>();
    private MessagesHandlerServer _messagesHandlerServer;
    private int _status = 0; //0 - nothing, 1 - new user, 2 - have message, 3 - quited player
    private ServerSocket _serverSocket;
    private int _nextAvailableNum = 0;
    private ArrayList<Integer> _quitedTokens = new ArrayList<>();

    public InternetManager(Game game) throws IOException {
        _serverSocket = new ServerSocket(3345);
        _socketNext = new SocketController(_serverSocket);
        _socketNextThread = new Thread(_socketNext);
        _socketNextThread.start();

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
    public void Update() throws JAXBException, IOException{
        for (SocketController socket : _tokenSocketTable.values()){
            if (socket.IsHaveMessages()){
                Message message = socket.ReadMessage();
                CheckMessage(message, socket.GetToken());
                _status = 2;
                if (_messagesHandlerServer.IsQuitMessage(message)){
                    _quitedTokens.add(socket.GetToken());
                }
            }
            if (socket.GetConnectionLostTime() >= 10000){
                _quitedTokens.add(socket.GetToken());
            }
        }
        if (!_quitedTokens.isEmpty()){
            _status = 3;
        }
        if (_socketNext.IsHaveMessages()){
            if (_messagesHandlerServer.IsMessageRegistrationOrLogin(_socketNext.GetMessage()))
                _status = 1;
            //System.out.println(Long.toString(_socketNext.GetConnectionLostTime()) + " " + Long.toString(_socketNext.GetAfkTime()));
        }
        if (_socketNext.GetConnectionLostTime() >= 1000 || _socketNext.GetAfkTime() >= 1000){
            _socketNext.CloseClient();
            _socketNextThread.interrupt();
            _socketNext = new SocketController(_serverSocket);
            _socketNextThread = new Thread(_socketNext);
            _socketNextThread.start();
            System.out.println("New user is not connecting");
        }
    }
    public Player Registration() throws JAXBException{
        while (_tokenSocketTable.get(_socketNext.GetToken()) != null){
            _socketNext.ResetToken();
        }
        int token = _socketNext.GetToken();
        _tokenSocketTable.put(token, _socketNext);
        Player player = new Player(_messagesHandlerServer.GetTextInMessage(_socketNext.ReadMessage()), 10000, _nextAvailableNum, new Hand());
        _tokenPlayerTable.put(token, player);
        _tokenThreadTable.put(token, _socketNextThread);

        _socketNext.AddMessage(new Message(2, _nextAvailableNum, player.GetName()));
        _nextAvailableNum++;

        _socketNext = new SocketController(_serverSocket);
        _socketNextThread = new Thread(_socketNext);
        _socketNextThread.start();
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

    public void KillPlayer(Player player) throws IOException{
        _playerAITable.remove(player);
        Integer token = FindTokenOfPlayer(player);
        if (token != null){
            _tokenPlayerTable.remove(token);
            SocketController socket = _tokenSocketTable.get(token);
            socket.CloseClient();
            _tokenSocketTable.remove(token);
            Thread thread = _tokenThreadTable.get(token);
            thread.interrupt();
            _tokenThreadTable.remove(token);
        }
    }
    public void KillQuitedPlayersInWait() throws IOException{
        for (int token : _quitedTokens){
            KillPlayer(token);
        }
        _quitedTokens.clear();
    }
    private void KillPlayer(Integer token) throws IOException{
        if (token != null){
            _tokenPlayerTable.remove(token);
            SocketController socket = _tokenSocketTable.get(token);
            socket.CloseClient();
            _tokenSocketTable.remove(token);
            Thread thread = _tokenThreadTable.get(token);
            thread.interrupt();
            _tokenThreadTable.remove(token);
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
    public ArrayList<Player> GetQuitedPlayersInWait(){
        ArrayList<Player> players = new ArrayList<>();
        for (int token : _quitedTokens){
            players.add(_tokenPlayerTable.get(token));
        }
        return players;
    }
}
