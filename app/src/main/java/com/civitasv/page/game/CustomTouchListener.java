package com.civitasv.page.game;

import android.view.MotionEvent;
import android.view.View;

import com.civitasv.helper.Orientation;

public class CustomTouchListener implements View.OnTouchListener {
    private float startX, startY;
    private final int threshold = 8; // 阈值
    private final GameView gameView;

    public CustomTouchListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                float dx = event.getX() - startX;
                float dy = event.getY() - startY;
                if (Math.abs(dx) > threshold || Math.abs(dy) > threshold) {
                    // 获得方向，滑动
                    gameView.slide(getOrientation(dx, dy));
                }
        }
        return true;
    }

    private Orientation getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            // 说明横向运动
            return dx > 0 ? Orientation.RIGHT : Orientation.LEFT;
        } else {
            return dy > 0 ? Orientation.DOWN : Orientation.UP;
        }
    }
}
