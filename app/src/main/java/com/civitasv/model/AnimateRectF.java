package com.civitasv.model;

import android.graphics.Rect;
import android.graphics.RectF;

public class AnimateRectF extends RectF {
    public AnimateRectF() {
        super();
    }

    public AnimateRectF(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    public AnimateRectF(RectF r) {
        super(r);
    }

    public AnimateRectF(Rect r) {
        super(r);
    }

    public void setTop(float top){
        this.top = top;
    }
    public void setBottom(float bottom){
        this.bottom = bottom;
    }
    public void setRight(float right){
        this.right = right;
    }
    public void setLeft(float left){
        this.left = left;
    }
}
