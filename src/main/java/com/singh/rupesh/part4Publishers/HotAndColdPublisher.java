package com.singh.rupesh.part4Publishers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/*
Cold Publisher - When each subscriber subscribes to publisher on its own channel,  and publisher emits exclusively for each subscriber.
Hot Publisher - Publisher emits common data to be consumed by all subscribers (multicast) at any instant of time.
 */
public class HotAndColdPublisher {

    public static void main(String[] args) {

        Flux<String> movieStream = Flux.fromStream(() -> getMovie())
                .delayElements(Duration.ofSeconds(2))
                .publish()
                //.autoConnect(0) // this makes the stream start with count of minimum subscriber 0 and when any subscriber(s) joins they
                // only get data which is currently being emitted
                //.cache(2) //Turns this Flux into a hot source and cache last emitted signals (as per provided values) for further Subscriber.
                .refCount(2);
                //.share(); // this function when applied makes the hot publisher ie.. multicast the same content to all subscribers.
        //share() = publish().refCount(1);
        // which means publish the data when you have at least 1 subscriber subscribed to publisher
        // publish().refCount(2)
        // means do not publish any data unless you have at least 2 subscribers.
        // if refCount value is 1 and one subscriber finishes consuming the data but still publisher is available to emit and some other
        //subscriber subscribes, publisher will emit data back from starting.

        movieStream.subscribe(Util.subscriber("Ram"));

        Util.sleepSeconds(5);

        movieStream.subscribe(Util.subscriber("Raunak"));

        Util.sleepSeconds(30);

    }

    private static Stream<String> getMovie() {
        System.out.println("Got the movie streaming request");
        return Stream.of(
                "Scene 1",
                "Scene 2",
                "Scene 3",
                "Scene 4",
                "Scene 5",
                "Scene 6",
                "Scene 7"
        );
    }
}
