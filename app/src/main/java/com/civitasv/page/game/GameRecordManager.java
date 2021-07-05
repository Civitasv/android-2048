package com.civitasv.page.game;

import android.content.Context;
import android.content.SharedPreferences;

public class GameRecordManager {
    private SharedPreferences sharedPreferences;

    public GameRecordManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("highest score", Context.MODE_PRIVATE);
    }

    public void saveHighest(long score) {
        sharedPreferences.edit().putLong("highest", score).apply();
    }

    public long getHighest() {
        return sharedPreferences.getLong("highest", 0);
    }

    public void saveNow(long score) {
        sharedPreferences.edit().putLong("now", score).apply();
    }

    public long getNow() {
        return sharedPreferences.getLong("now", 0);
    }
}
