package com.civitasv.page.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.civitasv.MainApplication;
import com.civitasv.helper.Orientation;
import com.civitasv.model.Tile;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable, OnSlideListener {
    private boolean running = false;

    private GameManager gameManager;
    private SurfaceHolder sfh;
    // 绘图
    private Canvas canvas;
    private Paint paint, textPaint;
    // 间隔
    private int gap;
    // 每一个格子长宽
    private int tileWidth, tileHeight;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    private void initView() {
        this.sfh = getHolder();
        this.sfh.addCallback(this);
        this.paint = new Paint();
        this.textPaint = new Paint();
        this.gameManager = new GameManager(4, this);
        this.gap = 20;
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
        setOnTouchListener(new CustomTouchListener(this));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 长宽
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        int size = this.gameManager.getGrid().getSize();
        int rectangleLength = Math.min(viewHeight, viewWidth);
        this.tileWidth = (rectangleLength - (size + 1) * gap) / size;
        this.tileHeight = (rectangleLength - (size + 1) * gap) / size;
        this.setMeasuredDimension(rectangleLength, rectangleLength);
    }

    @Override
    public void slide(Orientation orientation) {
        this.gameManager.move(orientation);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // 创建
        this.running = true;
        MainApplication.getInstance().getThreadPoolExecutor().execute(this);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // 改变
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // 销毁
        this.running = false;
    }

    private void drawGameView() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.parseColor("#bbada0"));
            paint.setStyle(Paint.Style.FILL);
            textPaint.setStrokeWidth(40);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(100);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            // 绘制格子
            Tile[][] tiles = gameManager.getGrid().getNow();
            for (int row = 0, size = gameManager.getGrid().getSize(); row < size; row++) {
                for (int col = 0; col < size; col++) {
                    float startX = col * tileWidth + (col + 1) * gap;
                    float startY = row * tileHeight + (row + 1) * gap;
                    float endX = (col + 1) * (tileWidth + gap);
                    float endY = (row + 1) * (tileHeight + gap);
                    Tile tile = tiles[row][col];
                    if (tile == null) {
                        paint.setColor(Color.parseColor("#cdc1b4"));
                        canvas.drawRect(startX, startY, endX, endY, paint);
                    } else {
                        textPaint.setColor(Color.parseColor("#776e65"));
                        switch (tile.getVal()) {
                            case 2:
                                paint.setColor(Color.parseColor("#eee4da"));
                                break;
                            case 4:
                                paint.setColor(Color.parseColor("#ede0c8"));
                                break;
                            case 8:
                                paint.setColor(Color.parseColor("#f2b179"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 16:
                                paint.setColor(Color.parseColor("#f59563"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 32:
                                paint.setColor(Color.parseColor("#f67c5f"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 64:
                                paint.setColor(Color.parseColor("#f65e3b"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 128:
                                paint.setColor(Color.parseColor("#edcf72"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 256:
                                paint.setColor(Color.parseColor("#edcc61"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 512:
                                paint.setColor(Color.parseColor("#edc850"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            case 1024:
                                paint.setColor(Color.parseColor("#edc53f"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                            default:
                                paint.setColor(Color.parseColor("#edc22e"));
                                textPaint.setColor(Color.parseColor("#f9f6f2"));
                                break;
                        }
                        canvas.drawRect(startX, startY, endX, endY, paint);
                        canvas.drawText(String.valueOf(tile.getVal()), (startX + endX) / 2, (startY + endY + textPaint.getTextSize() - textPaint.descent()) / 2, textPaint);
                    }
                }
            }
            // 绘制有数据的格子

        } catch (Exception ignore) {
        } finally {
            if (this.canvas != null) {
                this.sfh.unlockCanvasAndPost(this.canvas);
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            long start = System.currentTimeMillis();
            this.drawGameView();
            long end = System.currentTimeMillis();
            if (end - start < 100) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
