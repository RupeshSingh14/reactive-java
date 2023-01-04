package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

public class Just {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1,2,3,4); // Emits only Integers

        Flux<Object> flux1 = Flux.just(1,2, 'a', "Rupesh", Util.faker().name().fullName()); //Emits all objects

        flux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );

        flux.subscribe(i -> System.out.println("using multiple subscribers : " + i));

        Flux<Integer> evenFlux = flux.filter(i -> i % 2 == 0);
        evenFlux.subscribe(
                Util.onNext()
        );

    }
}
