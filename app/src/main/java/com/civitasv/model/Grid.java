package com.civitasv.model;

import com.civitasv.helper.GridHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 栅格布局图
 */
public class Grid {
    private int size; // n*n
    private Tile[][] now; // 目前的tile分布

    public Grid(int size) {
        this.size = size;
        this.now = new Tile[size][size];
    }

    public Position randomAvailablePosition() {
        List<Position> list = this.availablePositions();
        return list.size() > 0 ? list.get((int) (Math.random() * list.size())) : null;
    }

    public List<Position> availablePositions() {
        List<Position> list = new ArrayList<>();
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (now[row][col] == null) {
                    list.add(new Position(row, col));
                }
            }
        }
        return list;
    }

    public boolean hasAvailablePosition() {
        return this.availablePositions().size() > 0;
    }

    public boolean positionAvailable(Position position) {
        return this.positionContent(position) == null;
    }

    public boolean positionOccupied(Position position) {
        return this.positionContent(position) != null;
    }

    public Tile positionContent(Position position) {
        if (this.withinBounds(position)) {
            return this.now[position.getRow()][position.getCol()];
        } else {
            return null;
        }
    }

    public void insertTile(Tile tile) {
        this.now[tile.getPosition().getRow()][tile.getPosition().getCol()] = tile;
    }

    public void removeTile(Tile tile) {
        this.now[tile.getPosition().getRow()][tile.getPosition().getCol()] = null;
    }

    public boolean withinBounds(Position position) {
        return position.getRow() >= 0 && position.getRow() < this.size &&
                position.getCol() >= 0 && position.getCol() < this.size;
    }

    public int getSize() {
        return size;
    }

    public void setNow(Tile[][] now) {
        this.now = now;
    }

    public Tile[][] getNow() {
        return now;
    }

    @NotNull
    @Override
    public String toString() {
        return "Grid{" +
                "size=" + size +
                ", now=" + Arrays.toString(now) +
                '}';
    }
}