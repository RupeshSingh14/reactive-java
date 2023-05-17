package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

/*
defaultIfEmpty() and SwitchIfEmpty()
 */
public class IfEmpty {

    public static void main(String[] args) {

        getOrderNumbers()
                .filter(i -> i > 10)
                .defaultIfEmpty(-1) // if subscriber gets nothing from pipeline than give a default value
                .subscribe(Util.subscriber());

        getOrderNumbers()
                .filter(i -> i > 10)
                .switchIfEmpty(fallback()) // if subscriber gets nothing from pipeline than it switches
                // to get data from other sources.
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1,10);
    }

    private static Flux<Integer> fallback() {
        return Flux.range(20,5);
    }
}
