package com.civitasv.model;

import android.graphics.RectF;

/**
 * 每一块瓦片
 */
public class Tile {
    private final Position position; // 位置
    private final int val; // 值

    public Tile(Position position, int val) {
        this.position = position;
        this.val = val;
    }

    public void updatePosition(Position position) {
        this.position.setRow(position.getRow());
        this.position.setCol(position.getCol());
    }

    public int getVal() {
        return val;
    }

    public Position getPosition() {
        return position;
    }
}
