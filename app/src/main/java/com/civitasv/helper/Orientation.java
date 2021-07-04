package com.civitasv.helper;

import com.civitasv.model.Vector;

public enum Orientation {
    UP(new Vector(0, -1)), DOWN(new Vector(0, 1)), LEFT(new Vector(-1, 0)), RIGHT(new Vector(1, 0));

    private final Vector vector;

    Orientation(Vector vector) {
        this.vector = vector;
    }

    public Vector getVector() {
        return vector;
    }
}
