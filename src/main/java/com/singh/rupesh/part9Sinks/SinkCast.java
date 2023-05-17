package com.singh.rupesh.part9Sinks;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/*
Sink types        Behaviour  Pub:Sub
one                Mono     1:N
many - unicast     Flux     1:1
many - multicast   Flux     1:N
many - replay      Flux     1:N (with replay of all values to late subscribers)

 Unicast Processor allows only a single Subscriber
 Sinks are thread safe if we use emitNext() and provide retry mechanism
 */

public class SinkCast {

    public static void main(String[] args) {

        //handle through which we can push items
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();

        //For multicast ie.. n number for subscriber
        Sinks.Many<Object> multiCastSink = Sinks.many().multicast().onBackpressureBuffer();

        Sinks.Many<Object> multiCastSink2 = Sinks.many().replay().all(); // for caching data and replaying
        // for all subscribers to this sink.


        //handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("Ram"));
//        flux.subscribe(Util.subscriber("Shyam")); // Error -  Unicast Processor allows only a single Subscriber

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");

        Flux<Object> flux1 = multiCastSink.asFlux();
        List<Object> list = new ArrayList<>();
        flux1.subscribe(list::add);

        for(int i= 0; i < 1000; i ++){
            final int j = i;
            CompletableFuture.runAsync(() -> {
                multiCastSink.emitNext(j, ((signalType, emitResult) -> true));
                //using emitNext() and providing retry mechanism ensures thread safety.
                    });
        }
        Util.sleepSeconds(3);
        System.out.println(list.size());
    }
}
