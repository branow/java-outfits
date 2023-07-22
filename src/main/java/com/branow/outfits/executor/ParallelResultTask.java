package com.branow.outfits.executor;

public abstract class ParallelResultTask<R> extends ParallelTask {

    public R run() throws InterruptedException {
        doTask();
        return getResult();
    }

    protected abstract R getResult();


}
