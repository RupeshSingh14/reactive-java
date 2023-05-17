package com.singh.rupesh.part8RepeatAndRetries;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Retries {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        getIntegers()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3)))
                //retry spec contains 2 attempts to be retried with interval of 3 seconds each
                //.retry(2)
                //using only retry() will take this to indefinite loop
                .subscribe(Util.subscriber());

        Util.sleepSeconds(15);

    }

    private static Flux<Integer> getIntegers() {
        return Flux.range(1,3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("-- Completed"))
                .map(i -> i / (Util.faker().random().nextInt(1,5) > 3 ? 0 : 1))
                .doOnError(err -> System.out.println("-- error"));
    }

}
