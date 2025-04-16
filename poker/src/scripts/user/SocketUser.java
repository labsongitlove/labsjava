package scripts.user;

import jakarta.xml.bind.JAXBException;
import scripts.model.Message;

import java.io.*;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class SocketUser implements Runnable {
    private ArrayDeque<String> _InputMessages = new ArrayDeque<>();
    private ArrayDeque<String> _OutputMessages = new ArrayDeque<>();
    public void run() {
        try(Socket socket = new Socket("localhost", 3345);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            while(!socket.isClosed()){
                if(!_InputMessages.isEmpty()){
                    String messageIn = _InputMessages.pollFirst();

                    output.write(messageIn + "\n");
                    output.flush();
                    System.out.println("Client get message " + messageIn + ".");
                }
                if(input.ready()){
                    String messageOut = input.readLine();
                    _OutputMessages.addLast(messageOut);
                }
            }
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void AddMessage(Message message) throws JAXBException {
        if (message != null)
            _InputMessages.addLast(message.Marshal());
    }
    public synchronized Message ReadMessage() throws JAXBException{
        return Message.Unmarshal(_OutputMessages.pollFirst());
    }
    public synchronized boolean IsHaveMessages(){
        return !_OutputMessages.isEmpty();
    }
}
