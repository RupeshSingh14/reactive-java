package com.singh.rupesh.part6CombiningPublishers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
/*
Concat is used for connecting data emission by them one after the other.
 */
public class Concat {

    public static void main(String[] args) {

        Flux<String> flux0 = Flux.just("a", "b");
        Flux<String> flux1 = Flux.error(new RuntimeException("Error"));  // simulating runtime error from a publisher emitting data
        Flux<String> flux2 = Flux.just("a", "b", "c");

        Flux<String> concatFlux1 = flux0.concatWith(flux2); // first flux0 data will be emitted followed by flux2
        concatFlux1.subscribe(Util.subscriber());

        Flux<String> concatFlux2 = Flux.concat(flux0, flux2); // this is a factory method for concat
        concatFlux2.subscribe(Util.subscriber());

        Flux<String> concatFLux3 = Flux.concatDelayError(flux0, flux1, flux2);  // this ensures the error is only shown after processing the entire pipeline.
        concatFLux3.subscribe(Util.subscriber());
    }




}
