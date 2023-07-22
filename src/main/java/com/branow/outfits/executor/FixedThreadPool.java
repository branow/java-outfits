package com.branow.outfits.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class FixedThreadPool implements Executor {

    private final BlockingQueue<Thread> threads;
    private final int capacity;

    public FixedThreadPool() {
        this(Runtime.getRuntime().availableProcessors() - 1);
    }

    public FixedThreadPool(int capacity) {
        this.capacity = capacity;
        this.threads = new ArrayBlockingQueue<>(capacity);
    }


    public boolean canExecute() {
        return free() > 0;
    }

    public boolean isRunning() {
        return running() > 0;
    }


    public int free() {
        return capacity - threads.size();
    }

    public int running() {
        return threads.size();
    }


    public List<Thread> getThreads() {
        return new ArrayList<>(threads);
    }

    public void await() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (free() == 0) throw new IllegalArgumentException("There is not space for new thread");
        Thread thread = wrap(command);
        threads.add(thread);
        thread.start();
    }

    private Thread wrap(Runnable runnable) {
        return new Thread() {
            public void run() {
                runnable.run();
                remove(this);
            }
        };
    }

    private void remove(Thread thread) {
        threads.remove(thread);
    }

}