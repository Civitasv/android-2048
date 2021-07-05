package com.civitasv.page.game;

import android.content.Context;
import android.content.SharedPreferences;

import com.civitasv.model.Grid;
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

    public Grid getGameState() {
        String state = sharedPreferences.getString("state", "");
        return gson.fromJson(state, Grid.class);
    }

    public void clearGameState() {
        sharedPreferences.edit().remove("state").apply();
    }

    public boolean getOver() {
        return sharedPreferences.getBoolean("over", false);
    }

    public void saveOver(boolean over) {
        sharedPreferences.edit().putBoolean("over", over).apply();
    }

    public boolean getWon() {
        return sharedPreferences.getBoolean("won", false);
    }

    public void saveWon(boolean won) {
        sharedPreferences.edit().putBoolean("won", won).apply();
    }

    public boolean getKeepPlaying() {
        return sharedPreferences.getBoolean("keepPlaying", false);
    }

    public void saveKeepPlaying(boolean keepPlaying) {
        sharedPreferences.edit().putBoolean("keepPlaying", keepPlaying).apply();
    }
}
