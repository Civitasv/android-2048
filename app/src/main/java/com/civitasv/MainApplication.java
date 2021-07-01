package com.civitasv;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.core.os.HandlerCompat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainApplication extends Application {
    public static MainApplication instance;
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    private static final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // Creates a thread pool manager
    private ThreadPoolExecutor threadPoolExecutor;

    private Handler mainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
            instance = this;
        threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue
        );
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }
    
    public static MainApplication getInstance() {
        return instance;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public Handler getMainThreadHandler() {
        return mainThreadHandler;
    }
}
