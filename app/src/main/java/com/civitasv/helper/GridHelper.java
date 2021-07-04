package com.civitasv.helper;

import com.civitasv.model.Tile;

public class GridHelper {
    public static void empty(Tile[][] tiles) {
        for (int row = 0, rows = tiles.length; row < rows; row++) {
            for (int col = 0, cols = tiles[0].length; col < cols; col++) {
                tiles[row][col] = null;
            }
        }
    }
}
