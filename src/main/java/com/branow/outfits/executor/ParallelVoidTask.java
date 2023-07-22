package com.branow.outfits.executor;

public abstract class ParallelVoidTask extends ParallelTask {

    public void run() throws InterruptedException {
        doTask();
    }

}
