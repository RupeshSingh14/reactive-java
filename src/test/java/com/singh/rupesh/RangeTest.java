package com.singh.rupesh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class RangeTest {

    @Test
    public void test1() {
        Flux<Integer> range = Flux.range(1, 50);

        StepVerifier.create(range)//Internally step verifier subscribes to the publisher
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    public void test2() {
        Flux<Integer> range = Flux.range(1, 50);

        StepVerifier.create(range)
                .thenConsumeWhile(i -> i < 100)  // for indefinite or exact unknown amount of data to be emitted by publisher
                .verifyComplete();
    }




}
