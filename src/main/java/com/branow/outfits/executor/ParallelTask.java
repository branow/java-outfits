package com.branow.outfits.executor;

public abstract class ParallelTask {

    private final int capacity;

    public ParallelTask() {
        this(Runtime.getRuntime().availableProcessors() - 1);
    }

    public ParallelTask(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    protected void doTask() throws InterruptedException {
        FixedThreadPool executor = new FixedThreadPool(capacity);
        while (hasSubtasks()) {
            if (executor.canExecute()) {
                executor.execute(this::doSubtasks);
            } else {
                doSubtask();
            }
        }
        executor.await();
    }

    protected void doSubtasks() {
        while (hasSubtasks()) {
            doSubtask();
        }
    }

    protected abstract void doSubtask();

    protected abstract boolean hasSubtasks();

}
