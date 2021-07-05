package com.civitasv.page.game;

public interface OnStateChangeListener {
    void onWon(int score);
    void onFail(int score);
}
