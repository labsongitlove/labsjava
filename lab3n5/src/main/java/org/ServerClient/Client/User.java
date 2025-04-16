package org.ServerClient.Client;

import jakarta.xml.bind.JAXBException;

import org.ServerClient.Message;

public class User {
    SocketUser _socket;
    MessagesHandlerUser _messagesHandlerUser = new MessagesHandlerUser();
    public User(){
        _socket = new SocketUser();
        new Thread(_socket).start();
    }
    public void SendMessage(String input) throws JAXBException{
        Message message = _messagesHandlerUser.MakeMessage(input);
        _socket.AddMessage(message);
    }
    public void Update() throws JAXBException {
        if (_socket.IsHaveMessages())
            _messagesHandlerUser.Parsing(_socket.ReadMessage());
    }
}