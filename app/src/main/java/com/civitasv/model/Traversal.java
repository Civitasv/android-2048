package com.civitasv.model;

import java.util.List;

public class Traversal {
    private final List<Integer> rows;
    private final List<Integer> cols;

    public Traversal(List<Integer> rows, List<Integer> cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public List<Integer> getRows() {
        return rows;
    }

    public List<Integer> getCols() {
        return cols;
    }
}
