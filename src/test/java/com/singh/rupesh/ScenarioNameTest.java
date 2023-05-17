package com.singh.rupesh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

// to add extra inputs or tags to tests, so that we have proper info screened upon failures
public class ScenarioNameTest {

    @Test
    public void test1() {

        Flux<String> flux = Flux.just("a", "b", "c");

        //adding a test name
        StepVerifierOptions scenarioName = StepVerifierOptions.create().scenarioName("alphabet-count-test");

        StepVerifier.create(flux, scenarioName)
                .expectNextCount(12)
                .verifyComplete();
    }


    @Test
    public void test2() {

        Flux<String> flux = Flux.just("a", "b1", "c");

        StepVerifier.create(flux)
                .expectNext("a")
                .as("a-test")   //adding an alias name for pipeline tests
                .expectNext("b")
                .as("b-test")
                .expectNext("c")
                .as("c-test")
                .verifyComplete();
    }

}
