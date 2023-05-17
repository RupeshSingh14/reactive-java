package com.singh.rupesh.part9Sinks;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/*
Sinks are alternates processor ie.. acts as a publisher as well as subscriber.
 */
public class SinkOne {

    public static void main(String[] args) {

        // mono type of sink. can emit 1 value or empty or an error
        Sinks.One<Object> sink = Sinks.one();

        //converting sink into a mono publisher
        Mono<Object> mono = sink.asMono();

        //subscribing to the publisher
        mono.subscribe(Util.subscriber());
        mono.subscribe(Util.subscriber("Sam")); // multiple subscriber

        //sink.tryEmitValue("Hi");
        // Instead both the below can be used to emit an empty mono or an error
        // sink.tryEmitEmpty(); sink.tryEmitError(new RuntimeException("error"));

        // emit methods have a second argument for writing retry mechanism if first emit fails. Based on signal type and emit result we can
        // write the logic which returns a boolean to decide whether to retry or not.
        sink.emitValue("hi", ((signalType, emitResult) -> {
            System.out.println(signalType.name());
            System.out.println(emitResult.name());
            return false;
        }));

    }





}
