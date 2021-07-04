package com.civitasv.page.game;

import android.content.Context;
import android.content.SharedPreferences;

import com.civitasv.model.Tile;
import com.google.gson.Gson;

public class GameStateManager {
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public GameStateManager(Context context) {
        this.gson = new Gson();
        this.sharedPreferences = context.getSharedPreferences("game state", Context.MODE_PRIVATE);
    }

    public void saveGameState(String state) {
        sharedPreferences.edit().putString("state", state).apply();
    }

    public Tile[][] getGameState() {
        String state = sharedPreferences.getString("state", "");
        return gson.fromJson(state, Tile[][].class);
    }

    public void clearGameState() {
        sharedPreferences.edit().remove("state").apply();
    }
}
