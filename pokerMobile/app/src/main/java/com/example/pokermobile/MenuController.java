package com.example.pokermobile;

import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import org.VMC.Model;

public class MenuController {
    private Model model;
    private EditText nameText;
    private EditText betValueText;

    public MenuController(Model model, EditText nameText, EditText betValueText) {
        this.model = model;
        this.nameText = nameText;
        this.betValueText = betValueText;
    }

    public void onButtonPressed(Commands commands) {
        try {
            switch (commands) {
                case CONNECT:
                    model.UpdateFromController("join " + nameText.getText());
                    break;
                case FOLD:
                    model.UpdateFromController("bet 0");
                    break;
                case RAISE:
                    model.UpdateFromController("bet " + betValueText.getText());
                    break;
                case CALL:
                    model.UpdateFromController("bet " + model.GetBetNow());
                    break;
                case EXIT:
                    model.UpdateFromController("quit");
                    break;
            }
        } catch (Exception ign) {}
    }

}
