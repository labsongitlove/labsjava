package scripts.user;

import jakarta.xml.bind.JAXBException;
import scripts.model.Hand;
import scripts.model.Message;
import scripts.model.Player;
import scripts.server.MessagesHandlerServer;

import java.util.ArrayList;

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