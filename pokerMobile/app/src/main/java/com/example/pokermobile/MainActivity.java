package com.example.pokermobile;

import android.app.Activity;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.Game.Card;
import org.VMC.Model;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private Model model;
    private MenuController controller;

    TextView tableBet;
    TextView playerBet;
    ArrayList<ImageView> tableCards = new ArrayList<>();
    PlayerAdapter adapter;
    LinearLayout menu1;
    LinearLayout menu2_1;
    LinearLayout menu2_2;
    LinearLayout menu2_table;
    RecyclerView playersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //INITIAL ELEMENTS
        menu1 = findViewById(R.id.menu1Layout);
        menu2_1 = findViewById(R.id.menu2Layout);
        menu2_2 = findViewById(R.id.menu2Layout2);
        menu2_table = findViewById(R.id.tableCards);
        EditText nameText = findViewById(R.id.nameInput);
        EditText betValueText = findViewById(R.id.betInput);
        tableBet = findViewById(R.id.tableBetView);
        tableCards.add(findViewById(R.id.cardTable1Image));
        tableCards.add(findViewById(R.id.cardTable2Image));
        tableCards.add(findViewById(R.id.cardTable3Image));
        tableCards.add(findViewById(R.id.cardTable4Image));
        tableCards.add(findViewById(R.id.cardTable5Image));

        //INITIAL LOGIC MODULES
        model = new Model();
        controller = new MenuController(model, nameText, betValueText);

        playersRecyclerView = findViewById(R.id.playersRecyclerView);
        adapter = new PlayerAdapter(this, model.GetPlayers());
        playersRecyclerView.setAdapter(adapter);
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //SET ON CLICK EVENTS
        Button buttonConnection = findViewById(R.id.connectionButton);

        View.OnClickListener oclButtonConnection = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonPressed(Commands.CONNECT);

                menu1.setVisibility(View.GONE);
                menu2_1.setVisibility(View.VISIBLE);
                menu2_2.setVisibility(View.VISIBLE);
                menu2_table.setVisibility(View.VISIBLE);
                playersRecyclerView.setVisibility(View.VISIBLE);
            }
        };

        buttonConnection.setOnClickListener(oclButtonConnection);

        Button buttonExit = findViewById(R.id.exitButton);

        View.OnClickListener oclButtonExit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonPressed(Commands.EXIT);

                menu1.setVisibility(View.VISIBLE);
                menu2_1.setVisibility(View.GONE);
                menu2_2.setVisibility(View.GONE);
                menu2_table.setVisibility(View.GONE);
                playersRecyclerView.setVisibility(View.GONE);
            }
        };

        buttonExit.setOnClickListener(oclButtonExit);

        Button buttonFold = findViewById(R.id.foldButton);

        View.OnClickListener oclButtonFold = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonPressed(Commands.FOLD);
            }
        };

        buttonFold.setOnClickListener(oclButtonFold);

        Button buttonBet = findViewById(R.id.betButton);

        View.OnClickListener oclButtonBet = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonPressed(Commands.RAISE);
            }
        };

        buttonBet.setOnClickListener(oclButtonBet);

        Button buttonCall = findViewById(R.id.callButton);

        View.OnClickListener oclButtonCall = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonPressed(Commands.CALL);
            }
        };

        buttonCall.setOnClickListener(oclButtonCall);

        //UPDATING VIEW
        startPolling();
    }
    private final Handler handler = new Handler(Looper.getMainLooper());
    private void startPolling(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateGame();
                handler.postDelayed(this, 100);
            }
        }, 100);
    }
    private void UpdateGame(){
        runOnUiThread(() -> {
            try{
                model.Update();
            } catch (Exception ign){}

            if (model.GetAndResetIsQuit()){
                menu1.setVisibility(View.VISIBLE);
                menu2_1.setVisibility(View.GONE);
                menu2_2.setVisibility(View.GONE);
                menu2_table.setVisibility(View.GONE);
                playersRecyclerView.setVisibility(View.GONE);
            }

            tableBet.setText("Table bet: " + model.GetBetNow());

            if (model.GetTable() == null || model.GetTable().GetCards() == null
                    || model.GetTable().GetCards().isEmpty() || model.GetTable().GetCards().get(0).GetValue() == 0){
                for (ImageView image : tableCards){
                    image.setVisibility(View.GONE);
                }
            }
            else {
                ArrayList<Card> cards = model.GetTable().GetCards();
                int i = 0;
                for (Card card : cards){
                    tableCards.get(i).setVisibility(View.VISIBLE);
                    int cardRes = getResources().getIdentifier(CardToNamePng(card), "drawable", getPackageName());
                    tableCards.get(i).setImageResource(cardRes);
                    i++;
                }
            }

            adapter.UpdateData(model.GetPlayers());
        });
    }
    private static String CardToNamePng(Card card){
        String name = "c";
        int value = card.GetValue();
        if (value < 10){
            name += Integer.toString(card.GetValue() + 1);
        }
        else if (value == 10){
            name += "jack";
        }
        else if (value == 11){
            name += "queen";
        }
        else if (value == 12){
            name += "king";
        }
        else if (value == 13){
            name += "ace";
        }
        name += "_of_";
        int suit = card.GetSuit();
        if (suit == 1){
            name += "clubs";
        }
        else if (suit == 2){
            name += "diamonds";
        }
        else if (suit == 3){
            name += "hearts";
        }
        else if (suit == 4){
            name += "spades";
        }
        return name;
    }
}
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
*/
