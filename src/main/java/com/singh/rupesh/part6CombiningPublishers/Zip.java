package com.singh.rupesh.part6CombiningPublishers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;


/*
Usage - for order complete status depending on several orders
 */
public class Zip {

    public static void main(String[] args) {
        Flux.zip(getBody(), getEngine(), getTires())
                .subscribe(Util.subscriber());
    }

    // simulating publishers which when joined together form the correct data to pass to any subscriber
    // total of data produced from such pipeline will be the least count of data emitted by any of the zipped publisher.
    private static Flux<String> getBody() {
        return Flux.range(1,5)
                .map(i -> "body");
    }

    private static Flux<String> getEngine() {
        return Flux.range(1,2)
                .map(i -> "engine");
    }

    private static Flux<String> getTires() {
        return Flux.range(1,6)
                .map(i -> "tires");
    }
}
