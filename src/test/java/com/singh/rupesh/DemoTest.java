package com.singh.rupesh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class DemoTest {

    @Test
    public void test1() {
        Flux<Integer> just = Flux.just(1, 2, 3);

        StepVerifier.create(just)//Internally step verifier subscribes to the publisher
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
                //.expectComplete()
                //.verify()
    }

    @Test
    public void test2() {
        Flux<Integer> just = Flux.just(1, 2, 3);

        StepVerifier.create(just)//Internally step verifier subscribes to the publisher
                .expectNext(1,2,3) // we can pass multiple arguments too
                .verifyComplete();
    }




}
