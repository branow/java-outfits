package com.branow.outfits.timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Timer {

    public static Timer timer(List<Runnable> runnables) {
        List<TimeLine> timeLines = new ArrayList<>();
        for (int i=1; i<=runnables.size(); i++) {
            timeLines.add(new TimeLine("algorithm - " + i, runnables.get(i-1)));
        }
        return new Timer(timeLines);
    }

    private final List<TimeLine> timeLines;
    private List<Runnable> algorithms;
    private TimerResult result;

    public Timer(List<TimeLine> list) {
        timeLines = list;
    }


    public TimerResult start(int repeat) throws InterruptedException  {
        return start(repeat, 10, TimeUnit.SECONDS);
    }

    public TimerResult start(int repeat, long timeout, TimeUnit unit) throws InterruptedException  {
        result = new TimerResult(timeLines.stream().map(TimeLine::getName).toList());
        algorithms = wrap(timeLines);
        for (int i=0; i<repeat; i++) {
            Collections.shuffle(algorithms);
            ExecutorService exe = Executors.newCachedThreadPool(r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });
            algorithms.forEach(exe::execute);
            exe.shutdown();
            exe.awaitTermination(timeout, unit);
        }
        return result;
    }

    private List<Runnable> wrap(List<TimeLine> list) {
        return list.stream().map(this::wrap).collect(Collectors.toList());
    }

    private Runnable wrap(TimeLine thread) {
        return () -> {
            long start = System.nanoTime();
            thread.getRunnable().run();
            long time = System.nanoTime() - start;
            result.add(thread.name, time);
        };
    }


    public static class TimeLine {

        private String name;
        private Runnable runnable;

        public TimeLine(String name, Runnable runnable) {
            this.name = name;
            this.runnable = runnable;
        }

        public String getName() {
            return name;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }

}
