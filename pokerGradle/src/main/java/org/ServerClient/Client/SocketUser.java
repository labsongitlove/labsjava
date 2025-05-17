package org.ServerClient.Client;

import jakarta.xml.bind.JAXBException;
import org.ServerClient.Message;

import java.io.*;
import java.net.SocketException;
import java.net.Socket;
import java.util.ArrayDeque;

public class SocketUser implements Runnable {
    private final ArrayDeque<String> _InputMessages = new ArrayDeque<>();
    private final ArrayDeque<String> _OutputMessages = new ArrayDeque<>();

    private long _lastPingTime = System.currentTimeMillis();
    private boolean _connectionIsClosed = false;

    public void run() {
        try(Socket socket = new Socket("10.0.2.2", 3345);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            while(!socket.isClosed()){
                if(!_InputMessages.isEmpty()){
                    String messageIn = _InputMessages.pollFirst();

                    output.write(messageIn + "\n");
                    output.flush();
                }
                if(input.ready()){
                    String messageOut = input.readLine();
                    _OutputMessages.addLast(messageOut);
                }
                if (System.currentTimeMillis() - _lastPingTime >= 500){
                    _lastPingTime = System.currentTimeMillis();
                    output.write("ping\n");
                    output.flush();
                }
            }
        } catch (SocketException e){
            _connectionIsClosed = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public synchronized void AddMessage(Message message) {
        if (message != null)
            _InputMessages.addLast(message.MarshalJSON());
    }
    public synchronized Message ReadMessage() throws JAXBException{
        if (!_OutputMessages.isEmpty())
            return Message.Unmarshal(_OutputMessages.pollFirst());
        return null;
    }
    public synchronized boolean IsHaveMessages(){
        return !_OutputMessages.isEmpty();
    }
    public synchronized boolean ConnectionIsClosed() { return _connectionIsClosed; }
}
