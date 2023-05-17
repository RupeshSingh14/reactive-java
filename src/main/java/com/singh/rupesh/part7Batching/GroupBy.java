package com.singh.rupesh.part7Batching;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
It is like window but instead of making flux into FLux<Flux<T>> it makes a Flux<GroupedFlux<T>> by grouping the emitted data on defined category
(key) and processes the items in batch like flux ie..as and when emitted data is received. In case of window, flux is opened all the time continuously.
 */
public class GroupBy {
    public static void main(String[] args) {

        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(i -> i % 2)
                .subscribe(gf -> process(gf, gf.key()));

        Util.sleepSeconds(60);
    }

    private static void process(Flux<Integer> flux, int key) {
        System.out.println("called");
        flux.subscribe(i -> System.out.println("Key: " + key + ", Item : " + i));
    }

}