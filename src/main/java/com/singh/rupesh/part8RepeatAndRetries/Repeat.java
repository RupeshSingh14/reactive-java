package com.singh.rupesh.part8RepeatAndRetries;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

/*
Repeat - to resubscribe after onComplete signal
Retry - to resubscribe after error signal
 */
public class Repeat {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        getIntegers()
                .repeat(() -> atomicInteger.get() < 14)
                //.repeat(2) // repeats subscribing the publisher 2 times.
                //using only repeat() will take this to indefinite loop
                .subscribe(Util.subscriber());

    }

    private static Flux<Integer> getIntegers() {
        return Flux.range(1,3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("-- Completed"))
                .map(i -> atomicInteger.getAndIncrement());
    }
}