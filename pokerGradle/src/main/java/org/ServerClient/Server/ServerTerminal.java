package org.ServerClient.Server;

import jakarta.xml.bind.JAXBException;
import org.Game.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerTerminal implements AutoCloseable{
    private Game _game;
    private boolean _isGameUpdated = false;
    private InternetManager _internetManager;
    private BufferedReader _input;

    public ServerTerminal(Game game, InternetManager internetManager){
        _game = game;
        _internetManager = internetManager;
        _input = new BufferedReader(new InputStreamReader(System.in));
    }

    public void Uodate() throws IOException, JAXBException { //TODO поток в данной ситуации не нужен
        if (_input.ready()) {
            Execute(_input.readLine());
        }
    }

    public void Execute(String command) throws JAXBException {
        command = command;
        String[] parsedCommand = command.split(" ");
        if (parsedCommand[0].equals("gamerule")){
            System.out.println(CommandGamerule(parsedCommand));
        }
        if (parsedCommand[0].equals("set")){
            System.out.println(CommandSet(parsedCommand));
        }
        if (parsedCommand[0].equals("start")){
            _game.Start();
        }
        if (parsedCommand[0].equals("add")){
            System.out.println(CommandAdd(parsedCommand));
        }
        _internetManager.UpdateInfo();
    }
    private String CommandGamerule(String[] parsedCommand){
        StringBuilder output = new StringBuilder("Gamerules now: ");
        int type = 0;

        for (String rule: parsedCommand){
            String[] parsedRule = rule.split(":");
            if (parsedRule[0].equals("type")){
                type = Integer.parseInt(parsedRule[1]);
            }
        }
        _internetManager.UpdateGameRule(type);
        _isGameUpdated = true;
        return output.toString();
    }

    private String CommandSet(String[] parsedCommand){
        StringBuilder output = new StringBuilder("Set ");
        if (parsedCommand.length > 4 && parsedCommand[1].equals("player")){
            output.append("player ");
            Player player = _internetManager.FindPlayerForName(parsedCommand[2]);
            if (player != null) {
                for (String rule: parsedCommand){
                    String[] parsedRule = rule.split(":");
                    if (parsedRule.length > 1 && parsedRule[0].equals("money")){
                        int money = 0;
                        try {
                            money = Integer.parseInt(parsedRule[1]);
                            player.SetMoney(money);
                            output.append("money = " + parsedRule[1]);
                        } catch (NumberFormatException e){
                            output.append("money can't because it's not number.");
                        }
                    }
                }
            }
        }
        return output.toString();
    }
    public String CommandAdd(String[] parsedCommand){
        StringBuilder output = new StringBuilder("Add ");
        if (parsedCommand.length > 2 && parsedCommand[1].equals("AI")){
            output.append("AI ");
            String name = "AI";
            Hand hand = new Hand();
            int money = 10000;
            for (String rule: parsedCommand){
                String[] parsedRule = rule.split(":");
                if (parsedRule.length > 1 && parsedRule[0].equals("money")){
                    try {
                        money = Integer.parseInt(parsedRule[1]);
                        output.append("money = " + parsedRule[1] + " ");
                    } catch (NumberFormatException e){
                        output.append("money can't because it's not number, ");
                    }
                }
                else if (parsedRule.length > 1 && parsedRule[0].equals("name")){
                    name = parsedRule[1];
                    output.append("name = " + parsedRule[1] + " ");
                }
            }
            Player player = new Player(name, money, _internetManager.GetNextAvailableNum(), hand);
            AI ai = new AI();
            _game.AddPlayer(player);
            _internetManager.AddAIInTable(player, ai);
        }
        return output.toString();
    }

    public boolean IsGameUpdated(){
        return _isGameUpdated;
    }
    public Game GetGame(){
        return _game;
    }
    public void SetGame(Game game){
        _game = game;
    }
    @Override
    public void close() throws IOException{
        _input.close();
    }
}
