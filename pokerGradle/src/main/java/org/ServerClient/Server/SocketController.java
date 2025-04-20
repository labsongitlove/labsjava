package org.ServerClient.Server;

import jakarta.xml.bind.JAXBException;
import org.ServerClient.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Random;

public class SocketController implements Runnable {
    private ArrayDeque<String> _InputMessages = new ArrayDeque<>();
    private ArrayDeque<String> _OutputMessages = new ArrayDeque<>();
    private int _token;
    private ServerSocket _server;
    private long _afkTime = 0;
    private long _connectionLostTime = 0;

    private long _afkStartTime = 0;
    private long _connectionLostStartTime = 0;

    Socket _client;

    public SocketController(ServerSocket server){
        _server = server;
    }

    @Override
    public void run() {
        try {
            _client = _server.accept();
            _token = new Random().nextInt();
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(_client.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(_client.getInputStream()));
            System.out.println("New user try connect");
            while(!_client.isClosed()){
                if(!_InputMessages.isEmpty()){
                    String messageIn = _InputMessages.pollFirst();

                    output.write(messageIn + "\n");
                    output.flush();
                    //System.out.println("Server send message " + messageIn + ".");
                }
                if(input.ready()){
                    String messageOut = input.readLine();
                    if (messageOut.equals("ping"))
                        _connectionLostTime = 0;
                    else{
                        System.out.println(messageOut);
                        _OutputMessages.addLast(messageOut);
                        _afkTime = 0;
                    }
                } else {
                    if (_connectionLostTime == 0){
                        _connectionLostStartTime = System.currentTimeMillis();
                        _connectionLostTime = 1;
                    }
                    else
                        _connectionLostTime = System.currentTimeMillis() - _connectionLostStartTime;
                }
                if (_afkTime == 0){
                    _afkStartTime = System.currentTimeMillis();
                    _afkTime = 1;
                }
                else
                    _afkTime = System.currentTimeMillis() - _afkStartTime;
                //System.out.println(Long.toString(_afkTime) + " " + Long.toString(_connectionLostTime));
            }

            input.close();
            output.close();

            _client.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public synchronized void AddMessage(Message message) throws JAXBException {
        _InputMessages.addLast(message.Marshal());
    }
    public synchronized Message ReadMessage() throws JAXBException{
        var xml = _OutputMessages.pollFirst();
        return Message.Unmarshal(xml);
    }
    public synchronized Message GetMessage() throws JAXBException{
        var xml = _OutputMessages.getFirst();
        return Message.Unmarshal(xml);
    }
    public synchronized boolean IsHaveMessages(){
        return !_OutputMessages.isEmpty();
    }
    public synchronized int GetToken(){
        return _token;
    }
    public synchronized void ResetToken(){
        _token = new Random().nextInt();
    }
    public synchronized long GetAfkTime() { return _afkTime; }
    public synchronized long GetConnectionLostTime() { return _connectionLostTime; }
    public synchronized void ResetAfkTime() {_afkTime = 0; }
    public synchronized void CloseClient() throws IOException { if (_client != null && !_client.isClosed()) _client.close();}
}
