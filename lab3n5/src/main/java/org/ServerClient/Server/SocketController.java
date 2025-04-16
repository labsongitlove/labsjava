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

    public SocketController(ServerSocket server){
        _server = server;
    }

    @Override
    public void run() {
        try {
            Socket client = _server.accept();
            _token = new Random().nextInt();
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("New user try connect");
            while(!client.isClosed()){
                if(!_InputMessages.isEmpty()){
                    String messageIn = _InputMessages.pollFirst();

                    output.write(messageIn + "\n");
                    output.flush();
                    System.out.println("Server get message " + messageIn + ".");
                }
                if(input.ready()){
                    String messageOut = input.readLine();
                    _OutputMessages.addLast(messageOut);
                }
            }

            input.close();
            output.close();

            client.close();
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
    public synchronized boolean IsHaveMessages(){
        return !_OutputMessages.isEmpty();
    }
    public synchronized int GetToken(){
        return _token;
    }
    public synchronized void ResetToken(){
        _token = new Random().nextInt();
    }
}
