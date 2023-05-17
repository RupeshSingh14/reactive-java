package com.singh.rupesh.part7Batching;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
It is like buffer but instead of making flux into List<Flux<T>> it makes a Flux<Flux<T>> and processes the items in batch like flux ie..
as and when emitted data is received.
 */
public class Window {

    private static AtomicInteger atomicInteger = new AtomicInteger(1); // for getting Integer values

    public static void main(String[] args) {
        eventStream()
                //.window(5)
                .window(Duration.ofMillis(300))
                .flatMap(flux -> saveEvents(flux)) //to change to Flux<Flux<T>> to Flux<T>
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event" + i);
    }

    private static Mono<Integer> saveEvents(Flux<String> flux) {
        return flux
                .doOnNext(e -> System.out.println("saving " + e))
                .doOnComplete(() -> {
                    System.out.println("saved this batch");
                    System.out.println("-----------------");
                })
                .then(Mono.just(atomicInteger.getAndIncrement()));
    }

}
