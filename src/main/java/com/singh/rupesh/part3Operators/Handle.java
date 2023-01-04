package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

/*
Operators acts a decorators. They do operations on the flux or mono items, creating a new instance
every time and thus process the pipeline with required logic.
 */
// handle = filter + map
public class Handle {

    public static void main(String[] args) {

        Flux.range(1, 20)
                .handle((integer, synchronousSink) -> {
                    if(integer == 7)
                        synchronousSink.complete();
                    else
                        synchronousSink.next(integer);
                }).subscribe(Util.subscriber());

        Flux.generate(synchronousSink -> synchronousSink.next(Util.faker().country().name()))
                .map(Object::toString) //a -> a.toString()
                .handle((s, synchronousSink) -> {
                    synchronousSink.next(s);
                    if(s.equalsIgnoreCase("India"))
                        synchronousSink.complete();
                }).subscribe(Util.subscriber());
    }

}
