package com.singh.rupesh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ErrorTest {

    @Test
    public void test1() {
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("OOM error"));
        Flux<Integer> concat = Flux.concat(just, error);

        StepVerifier.create(concat)//Internally step verifier subscribes to the publisher
                .expectNext(1,2,3)
                .verifyError();
    }


    @Test
    public void test2() {
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("OOM error"));
        Flux<Integer> concat = Flux.concat(just, error);

        StepVerifier.create(concat)//Internally step verifier subscribes to the publisher
                .expectNext(1,2,3)
                .verifyError(RuntimeException.class);
                //.verifyError(IllegalStateException.class); fails in this case due to error type mismatch
    }

    @Test
    public void test3() {
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("OOM error"));
        Flux<Integer> concat = Flux.concat(just, error);

        StepVerifier.create(concat)//Internally step verifier subscribes to the publisher
                .expectNext(1,2,3)
                .verifyErrorMessage("OOM error");
        //.verifyError(IllegalStateException.class); fails in this case due to error type mismatch
    }

}
