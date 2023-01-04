package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
Conversion to flux from mono.
 */
public class FluxFromMono {
    public static void main(String[] args) {

        Mono<String> mono = Mono.just("Rupesh");
        Flux<String> flux = Flux.from(mono);

        doSomething(flux);
        flux.subscribe(Util.onNext());
    }

    public static void doSomething(Flux<String> flux) {
        System.out.println("Do something method called");
    }
}
