package com.civitasv.page.game;

public interface OnStateChangeListener {
    void onWon(long score);
    void onFail(long score);
}
