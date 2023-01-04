package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
Timeout helps to set response time for any request/ publisher to emit data.
 */
public class TimeOut {
    public static void main(String[] args) {
        getOrderNumbers()
                .log()
                //.timeout(Duration.ofSeconds(2))
                //without fallback mechanism
                .timeout(Duration.ofSeconds(2), fallback())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }

    public static Flux<Integer> getOrderNumbers() {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(3));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100,10)
                .delayElements(Duration.ofMillis(500));
    }

}
