package com.civitasv.page.game;

import android.content.Context;

import com.civitasv.helper.Orientation;
import com.civitasv.model.Grid;
import com.civitasv.model.Position;
import com.civitasv.model.Tile;
import com.civitasv.model.Traversal;
import com.civitasv.model.Vector;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    private final int size; // 栅格宽度

    private Grid grid;
    // 游戏状态
    private boolean over;
    private boolean won;
    private boolean keepPlaying;

    // 初始数目
    private final int startTiles;

    // 分数
    private long score;
    private Gson gson;
    private OnScoreChangeListener onScoreChangeListener;
    private OnStateChangeListener onStateChangeListener;
    private GameStateManager gameStateManager;
    private GameRecordManager gameRecordManager;

    public GameManager(Context context, int size) {
        this.startTiles = 2;
        this.size = size;
        this.gameStateManager = new GameStateManager(context);
        this.gameRecordManager = new GameRecordManager(context);
        this.gson = new Gson();
        setup();
    }

    public GameManager(Context context, Grid grid, boolean over, boolean won, boolean keepPlaying, long score) {
        this.startTiles = 2;
        this.size = grid.getSize();
        this.grid = grid;
        this.over = over;
        this.won = won;
        this.keepPlaying = keepPlaying;
        this.score = score;
        this.gameStateManager = new GameStateManager(context);
        this.gameRecordManager = new GameRecordManager(context);
        this.gson = new Gson();
    }

    public void restart() {
        this.setup();
    }

    /**
     * 已经到达2048，但继续游戏
     */
    public void keepPlaying() {
        this.keepPlaying = true;

    }

    public boolean isGameTerminated() {
        return this.over || (this.won && !this.keepPlaying);
    }

    public void setup() {
        this.grid = new Grid(size);
        this.score = 0;
        this.over = false;
        this.won = false;
        this.keepPlaying = false;
        addStartTiles();
        this.gameStateManager.saveGameState(gson.toJson(grid));
        this.gameStateManager.saveOver(over);
        this.gameStateManager.saveWon(won);
        this.gameStateManager.saveKeepPlaying(keepPlaying);
        this.gameRecordManager.saveNow(score);
    }

    public void addStartTiles() {
        for (int i = 0; i < this.startTiles; i++) {
            this.addRandomTile();
        }
    }

    public void addRandomTile() {
        if (this.grid.hasAvailablePosition()) {
            int val = Math.random() < 0.9 ? 2 : 4;
            this.grid.insertTile(new Tile(this.grid.randomAvailablePosition(), val));
        }
    }

    public void moveTile(Tile tile, Position position) {
        Tile[][] tiles = this.grid.getNow();
        tiles[tile.getPosition().getRow()][tile.getPosition().getCol()] = null;
        tiles[position.getRow()][position.getCol()] = tile;
        tile.updatePosition(position);
    }

    public void move(Orientation orientation) {
        if (isGameTerminated()) return;
        // 1. 获得方向向量
        Vector vector = orientation.getVector();
        // 2. 根据方向向量获取遍历路径
        Traversal traversal = buildTraversals(vector);
        // 3. 设置移动标志
        boolean moved = false;
        // 4. 遍历
        for (int row : traversal.getRows()) {
            for (int col : traversal.getCols()) {
                Position position = new Position(row, col);
                // 获取该位置的格子
                Tile tile = grid.positionContent(position);
                if (tile != null) {
                    // 获取最近的有数据的网格
                    Map<String, Position> data = findFarthestPosition(position, vector);
                    Position nextPos = data.get("next");
                    if (nextPos == null) return;
                    Tile next = grid.positionContent(nextPos);
                    if (next != null && next.getVal() == tile.getVal()) {
                        Tile merged = new Tile(nextPos, tile.getVal() * 2);
                        // 删除之前的
                        grid.removeTile(tile);
                        // 添加这个
                        grid.insertTile(merged);
                        tile.updatePosition(nextPos);
                        this.score += merged.getVal();
                        if (onScoreChangeListener != null)
                            onScoreChangeListener.onChange(score);
                        if (merged.getVal() == 2048) {
                            won = true;
                            onStateChangeListener.onWon(score);
                        }
                    } else {
                        moveTile(tile, data.get("farthest"));
                    }
                    if (!position.equals(tile.getPosition()))
                        moved = true;
                }
            }
        }
        if (moved) {
            // save state
            addRandomTile();
            this.gameStateManager.saveGameState(gson.toJson(grid));
            this.gameStateManager.saveOver(over);
            this.gameStateManager.saveWon(won);
            this.gameStateManager.saveKeepPlaying(keepPlaying);
            this.gameRecordManager.saveNow(score);
            if (!moveAvailable()) {
                over = true;
                onStateChangeListener.onFail(score);
            }
        }
    }

    private boolean moveAvailable() {
        return grid.hasAvailablePosition() || tileMatchesAvailable();
    }

    private boolean tileMatchesAvailable() {
        Tile tile;
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                tile = grid.positionContent(new Position(row, col));
                if (tile != null) {
                    for (Orientation orientation : Orientation.values()) {
                        Position position = new Position(row + orientation.getVector().getX(), col + orientation.getVector().getY());
                        Tile other = grid.positionContent(position);
                        if (other != null && other.getVal() == tile.getVal())
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public Grid getGrid() {
        return grid;
    }

    private Traversal buildTraversals(Vector vector) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            rows.add(i);
            cols.add(i);
        }
        if (vector.getX() == 1) {
            // 向右
            Collections.reverse(cols);
        }
        if (vector.getY() == 1) {
            // 向下
            Collections.reverse(rows);
        }
        return new Traversal(rows, cols);
    }

    private Map<String, Position> findFarthestPosition(Position position, Vector vector) {
        Position previous, next = position;
        do {
            previous = next;
            next = new Position(previous.getRow() + vector.getY(), previous.getCol() + vector.getX());
        } while (grid.withinBounds(next) && grid.positionAvailable(next));
        Map<String, Position> res = new HashMap<>();
        res.put("farthest", previous);
        res.put("next", next);
        return res;
    }

    public void setOnScoreChangeListener(OnScoreChangeListener onScoreChangeListener) {
        this.onScoreChangeListener = onScoreChangeListener;
    }


    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
