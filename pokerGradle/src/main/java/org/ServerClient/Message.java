package org.ServerClient;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;

import org.Game.Hand;
import org.Game.Player;


import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    @XmlAttribute(name = "Type")
    int _type = 0; // 0 - update, 1 - bet, 2 - login, 3 - registration, 4 - quit
    @XmlAttribute(name = "Value")
    int _value = 0;
    @XmlAttribute(name = "Text")
    String _text = "";
    @XmlElement(name = "Table")
    Hand _table; //0 - table
    @XmlElement(name = "Players")
    ArrayList<Player> _players;

    public Message(){}

    public Message(int type, int value, String text) {
        _type = type;
        _value = value;
        _text = text;
    }
    public Message(int type, int value, String text, Hand table, ArrayList<Player> players) {
        _type = type;
        _value = value;
        _text = text;
        _table = table;
        _players = players;
    }
    public Message(String xml) throws JAXBException{
        Unmarshal(xml);
    }

    public String GetText() {return _text;}
    public int GetType(){
        return _type;
    }
    public int GetValue(){
        return _value;
    }
    public Hand GetTable(){
        return _table;
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

    public String MarshalJSON(){
        return new Gson().toJson(this);
    }

    private void UnmarshalInitial(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Message.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        Message messageXml = (Message) unmarshaller.unmarshal(reader);

        _type = messageXml._type;
        _value = messageXml._value;
        _table = messageXml._table;
        _players = messageXml._players;
    }

    public static Message Unmarshal(String string) throws JAXBException{
        if (!string.startsWith("<?xml")){
            return new Gson().fromJson(string, Message.class);
        }
        JAXBContext context = JAXBContext.newInstance(Message.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(string);

        return (Message) unmarshaller.unmarshal(reader);
    }
}
