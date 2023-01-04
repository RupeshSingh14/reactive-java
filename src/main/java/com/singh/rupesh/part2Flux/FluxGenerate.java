package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
/*
generate method provides with synchronous sink which can only be called once and has ability to
call itself in loop until there is demand from subscriber.
 */
public class FluxGenerate {
    public static void main(String[] args) {

        //goes in loop itself unless criteria to cancel or complete or error is not defined.
        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().name().fullName());
        })
                .take(2) // ensures that emitting from producer is canceled after getting 2 values.
                .subscribe(Util.subscriber());

        //implementing method using synchronous link
        Flux.generate(synchronousSink -> {
            String country = Util.faker().country().name();
            System.out.println("\nemitting : " + country);
            synchronousSink.next(country);
            if (country.equalsIgnoreCase("India")) {
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());

        //implementing generate to have counter to check and cancel or complete.
        Flux.generate(
                () -> 1,  // for maintaining the counter
                (counter, sink) -> {
                    String country = Util.faker().country().name();
                    System.out.println("\nemitting country " + counter);
                    sink.next(country);
                    if(counter >= 10 || country.equalsIgnoreCase("India"))
                        sink.complete();
                    return counter + 1;  // counter is incremented and feed backed to the generate method.
                }
        ).subscribe(Util.subscriber());
    }
}
