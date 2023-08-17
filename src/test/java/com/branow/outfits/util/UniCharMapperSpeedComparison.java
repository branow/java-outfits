package com.branow.outfits.util;

import com.branow.outfits.timer.Timer;
import com.branow.outfits.timer.TimerResult;
import org.junit.jupiter.api.Assertions;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class UniCharMapperSpeedComparison {


    public static void main(String[] args) {
        Timer timer = new Timer(timeLines());
        try {
            TimerResult tr = timer.start(3);
            System.out.println(tr.toString(TimerResult.TimeScale.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static void runUniCharMapper(byte[] bytes, Charset charset) {
        ByteBuffer buffer = ByteBuffer.wrap(new String(bytes, charset).getBytes(charset));
        UniCharMapper mapper = new UniCharMapper(buffer, charset);
        while (mapper.hasNext()) {
            mapper.next();
        }
    }

    private static void runUniCharMapperStream(byte[] bytes, Charset charset) {
        ByteBuffer buffer = ByteBuffer.wrap(new String(bytes, charset).getBytes(charset));
        UniCharStreamMapper mapper = new UniCharStreamMapperTest.UniCharStreamMapperImp(buffer, charset);
        while (mapper.hasNext()) {
            mapper.next();
        }
    }


    private static List<Timer.TimeLine> timeLines() {
        return List.of(
                new Timer.TimeLine("UniCharMapper 100", () -> runUniCharMapper(randomBytes(100), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharStreamMapper 100", () -> runUniCharMapperStream(randomBytes(100), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharMapper 10_000", () -> runUniCharMapper(randomBytes(10_000), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharStreamMapper 10_000", () -> runUniCharMapperStream(randomBytes(10_000), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharMapper 1_000_000", () -> runUniCharMapper(randomBytes(1_000_000), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharStreamMapper 1_000_000", () -> runUniCharMapperStream(randomBytes(1_000_000), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharMapper 10_000_000", () -> runUniCharMapper(randomBytes(10_000_000), StandardCharsets.UTF_8)),
                new Timer.TimeLine("UniCharStreamMapper 10_000_000", () -> runUniCharMapperStream(randomBytes(10_000_000), StandardCharsets.UTF_8))
        );
    }

    private static byte[] randomBytes(int size) {
        Random random = new Random();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}
