package scripts.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;
import scripts.user.User;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    @XmlAttribute(name = "Type")
    int _type = 0;
    @XmlAttribute(name = "Value")
    int _value = 0;
    @XmlElement(name = "Hands")
    ArrayList<Hand> _hands; //0 - table, 1 - your
    @XmlElement(name = "Players")
    ArrayList<Player> _players;

    public Message(){}

    public Message(int type, int value) {
        _type = type;
        _value = value;
    }
    public Message(int type, int value, ArrayList<Hand> hands, ArrayList<Player> players) {
        _type = type;
        _value = value;
        _hands = hands;
        _players = players;
    }
    public Message(String xml) throws JAXBException{
        Unmarshal(xml);
    }

    public int GetType(){
        return _type;
    }
    public int GetValue(){
        return _value;
    }
    public ArrayList<Hand> GetHands(){
        return _hands;
    }
    public ArrayList<Player> GetPlayers(){
        return _players;
    }

    public String Marshal() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(this, writer);

        return writer.toString();
    }

    private void UnmarshalInitial(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Message.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        Message messageXml = (Message) unmarshaller.unmarshal(reader);

        _type = messageXml._type;
        _value = messageXml._value;
        _hands = messageXml._hands;
        _players = messageXml._players;
    }

    public static Message Unmarshal(String xml) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Message.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);

        return (Message) unmarshaller.unmarshal(reader);
    }
}
