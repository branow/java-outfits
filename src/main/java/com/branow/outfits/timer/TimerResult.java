package com.branow.outfits.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TimerResult {

    public enum TimeScale {
        MINUTES(60_000_000_000L), SECONDS(1_000_000_000), MILLISECONDS(1_000_000), MICROSECONDS(1_000), NANOSECONDS(1);

        private final long value;

        TimeScale(long value) {
            this.value = value;
        }

        long getValue() {
            return value;
        }
    }

    public static final String DEFAULT_NAME = "algorithm - ";

    private final Map<String, List<Long>> map;
    private boolean usingIds;

    public TimerResult(List<String> names) {
        this.map = getEmptyMap(names);
    }

    public TimerResult(int size) {
        this.map = getEmptyMap(size);
        usingIds = true;
    }

    public void add(String key, Long value) {
        map.get(key).add(value);
    }

    public void add(int id, Long value) {
        if (!usingIds) throw new IllegalStateException();
        add(DEFAULT_NAME + id, value);
    }

    public List<Long> get(int id) {
        if (usingIds) throw new IllegalStateException();
        return new ArrayList<>(map.get(DEFAULT_NAME + id));
    }

    public List<Long> get(String name) {
        return new ArrayList<>(map.get(name));
    }

    public Map<String, List<Long>> getResult() {
        return new HashMap<>(map);
    }

    public String toString(TimeScale scale) {
        return map.entrySet().stream().sorted((e1, e2) -> CharSequence.compare(e1.getKey(), e2.getKey()))
                .map(e -> e.getKey() + " -> " + e.getValue().stream().map(l -> l/scale.getValue()).toList() + " " + average(e.getValue())/scale.value)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return map.entrySet().stream().sorted((e1, e2) -> CharSequence.compare(e1.getKey(), e2.getKey()))
                .map(e -> e.getKey() + " -> " + e.getValue() + " " + average(e.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private Long average(List<Long> values) {
        return values.stream().collect(Collectors.averagingLong(e -> e)).longValue();
    }

    private Map<String, List<Long>> getEmptyMap(List<String> names) {
        Map<String, List<Long>> map = new HashMap<>();
        for (String name : names) {
            map.put(name, new ArrayList<>());
        }
        return map;
    }

    private Map<String, List<Long>> getEmptyMap(int size) {
        Map<String, List<Long>> map = new HashMap<>();
        for (int i=0; i<size; i++) {
            map.put(DEFAULT_NAME + i, new ArrayList<>());
        }
        return map;
    }

}
