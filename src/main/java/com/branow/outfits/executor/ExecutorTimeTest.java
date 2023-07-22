package com.branow.outfits.executor;

import com.branow.outfits.timer.Timer;
import com.branow.outfits.timer.TimerResult;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorTimeTest {

    private static final int totalTime = 1000;
    private static final int num = 100;

    public static void main(String[] args) throws InterruptedException {
        List<Runnable> tasks = List.of(ExecutorTimeTest::line, ExecutorTimeTest::executor, ExecutorTimeTest::task);
        Timer timer = Timer.timer(tasks);
        TimerResult result = timer.start(5, 360, TimeUnit.SECONDS);
        System.out.println(result.toString(TimerResult.TimeScale.MILLISECONDS));
    }

    private static void line() {
        sleep(totalTime);
    }

    private static void executor() {
        FixedThreadPool fixedExecutor = new FixedThreadPool();
        int totalWork = totalTime;
        int parts = num;
        for (int i=0; i<parts; i++) {
            final int currentWork = totalWork / parts;
            if (fixedExecutor.canExecute()) {
                fixedExecutor.execute(() -> sleep(currentWork));
            } else {
                sleep(currentWork);
            }
        }
        try {
            fixedExecutor.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void task() {
        SleepTask task = new SleepTask(totalTime, num);
        try {
            task.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class SleepTask extends ParallelVoidTask {

        private final int totalWork;
        private final int partsAll;
        private final AtomicInteger partsStart = new AtomicInteger(0);

        public SleepTask(int totalWork, int partsAll) {
            this.totalWork = totalWork;
            this.partsAll = partsAll;
        }

        @Override
        protected void doSubtask() {
            partsStart.incrementAndGet();
            try {
                Thread.sleep(totalWork/partsAll);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected boolean hasSubtasks() {
            return partsStart.get() >= partsAll;
        }
    }

}
