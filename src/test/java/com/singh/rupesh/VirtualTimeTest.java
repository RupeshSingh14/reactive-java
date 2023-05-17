package com.singh.rupesh;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class VirtualTimeTest {

    @Test
    public void test1() {

        // this will block the execution till all data is emitted with real delay of time
        /*
        StepVerifier.create(timeConsumingFlux())
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();
         */

        //this will process the test ASAP with virtual delay as defined expected behaviour
        StepVerifier.withVirtualTime(() -> timeConsumingFlux())
                .thenAwait(Duration.ofSeconds(30))
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();
    }

    // when we want to test delay of the starting of events
    @Test
    public void test2() {
        StepVerifier.withVirtualTime(() -> timeConsumingFlux())
                .expectSubscription()             //subscription is an event
                .expectNoEvent(Duration.ofSeconds(4)) //this will pass since delay duration is of 5secs and we have passed here 4 secs
                //.expectNoEvent(Duration.ofSeconds(6)) //this will fail
                .thenAwait(Duration.ofSeconds(20))
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();
    }



    private Flux<String> timeConsumingFlux() {
        return Flux.range(1, 4)
                .delayElements(Duration.ofSeconds(5))
                .map(i -> i + "a");
    }




}
