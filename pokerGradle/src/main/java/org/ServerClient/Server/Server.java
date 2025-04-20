package org.ServerClient.Server;

import jakarta.xml.bind.JAXBException;
import org.Game.*;

import java.io.IOException;
/*
add AI name:Bob money:1000000 type:2
add AI name:Gabe money:1000000 type:3
start
 */
//TODO: концепцию пользователей, ии, модель обновляет состояние и содержит user, view узнает у модели состояние и в зависимости от него изменяет визуал, контролер только ввод.
public class Server {
    public static void main(String[] args) throws JAXBException, IOException, InterruptedException {
        Game game = new Game();
        long time = 0;
        try(InternetManager internetManager = new InternetManager(game); ServerTerminal serverTerminal = new ServerTerminal(game, internetManager)){
            while (true){
                serverTerminal.Uodate();
                if (serverTerminal.IsGameUpdated()){
                    game = serverTerminal.GetGame();
                }
                int status = game.GetStatus();
                if (status == 0 && game.GetPlayers() != null && game.GetPlayers().size() > 1){
                    if (time == 0){
                        internetManager.SendResult();
                        time = System.currentTimeMillis();
                    }
                    else if (System.currentTimeMillis() - time >= 10000){
                        game.Start();
                        System.out.println("Game was started");
                        internetManager.UpdateInfo();
                        time = 0;
                    }
                }
                else if (status == 1){
                    if (!game.IsActiveMoreOne()){
                        game.NextStep();
                        System.out.println("Skip step");
                        internetManager.UpdateInfo();
                    }
                    else{
                        Player player = game.BetPlayerNow();
                        if (!player.IsActive()){
                            game.DoBet(player, 0);
                            System.out.println("Bet skip");
                        }
                        else if (internetManager.IsAI(player)){
                            game.DoBet(player, internetManager.GetAI(player).DoBet(player, game));
                            System.out.println("Bot do bet");
                            internetManager.UpdateInfo();
                        }
                    }
                    if (game.TryPrebets()){
                        internetManager.UpdateInfo();
                    }
                }
                else if (status == 2){
                    game.NextStep();
                    System.out.println("Next step");
                    internetManager.UpdateInfo();
                }
                status = internetManager.GetStatus();
                if (status == 1){
                    Player player = internetManager.Registration();
                    game.AddPlayer(player);
                    System.out.println("New player");
                    internetManager.UpdateInfo();
                }
                else if (status == 2){
                    internetManager.UpdateInfo();
                    internetManager.SetStatus(0);
                }
                else if (status == 3){
                    game.KillPlayers(internetManager.GetQuitedPlayersInWait());
                    internetManager.KillQuitedPlayersInWait();
                    System.out.println("Quited player(s)");

                    internetManager.UpdateInfo();
                    internetManager.SetStatus(0);
                }
                internetManager.Update();
            }
        }
    }
}
