package com.singh.rupesh.part1Mono;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;

/*
Runnable does not accepts any arguments neither it given any output. It is used with Mono to mark
the completion of any task which can be used as signal to start further tasks.
 */
public class MonoFromRunnable {
    public static void main(String[] args) {
        Mono.fromRunnable(timeConsumingProcess())
                .subscribe(Util.onNext(),
                        Util.onError(),
                        () -> {
                            System.out.println("Process is done. Sending emails...");
                        });
    }

    private static Runnable timeConsumingProcess() {
        return () -> {
            Util.sleepSeconds(2);
            System.out.println("Operation completed");
        };
    }
}