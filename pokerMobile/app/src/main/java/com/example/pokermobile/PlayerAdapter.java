package com.example.pokermobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.Game.Card;
import org.Game.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Player> players;
    private Context context;

    public PlayerAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }

    public void UpdateData(List<Player> players){
        this.players = players;
        notifyDataSetChanged();
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.bind(player, context);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage, card1Image, card2Image;
        TextView nameText, betText, statusText, moneyText;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.avatarImage);
            card1Image = itemView.findViewById(R.id.card1Image);
            card2Image = itemView.findViewById(R.id.card2Image);
            nameText = itemView.findViewById(R.id.nameText);
            betText = itemView.findViewById(R.id.betText);
            moneyText = itemView.findViewById(R.id.moneyText);
            statusText = itemView.findViewById(R.id.statusText);
        }

        public void bind(Player player, Context context) {
            nameText.setText(player.GetName().length() > 6 ? player.GetName().substring(0, 6) + "..." : player.GetName());
            betText.setText("Bet: " + player.GetBet());
            moneyText.setText("Money: " + player.GetMoney());

            statusText.setText(player.IsActive() ? "Active" : "Fold");

            ArrayList<Card> cards = player.GetHand().GetCards();

            if (cards.get(0).GetValue() == 0){
                card1Image.setVisibility(View.GONE);
                card2Image.setVisibility(View.GONE);
            }
            else{
                card1Image.setVisibility(View.VISIBLE);
                card2Image.setVisibility(View.VISIBLE);
                int card1Res = context.getResources().getIdentifier(CardToNamePng(cards.get(0)), "drawable", context.getPackageName());
                int card2Res = context.getResources().getIdentifier(CardToNamePng(cards.get(1)), "drawable", context.getPackageName());

                card1Image.setImageResource(card1Res);
                card2Image.setImageResource(card2Res);
            }
        }
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