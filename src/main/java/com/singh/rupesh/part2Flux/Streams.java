package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

/*
A Stream can only be operated once, hence it can cause below error in case of multiple usage:
stream has already been operated upon or closed
 */
public class Streams {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4,5);
        Stream<Integer> stream = list.stream();
        //stream.forEach(System.out::println); // running twice causes error

        //Flux<Integer> flux = Flux.fromStream(() -> stream); // this uses same reference to stream, hence
        //will fail the multiple exection on stream

        Flux<Integer> flux = Flux.fromStream(() -> list.stream()); // list.stream() creates fresh instance
        //every time when this assignment is called, enabling multiple usage

        flux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );

        flux.subscribe(
                i -> System.out.println("Printing the value : " + i),
                Util.onError(),
                Util.onComplete()
        );

    }
}
