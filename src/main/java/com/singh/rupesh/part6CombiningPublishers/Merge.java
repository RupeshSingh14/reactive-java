package com.singh.rupesh.part6CombiningPublishers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
Usage - for aggregating the data - hotel, flight aggregator website.
 */
public class Merge {

    public static void main(String[] args) {

        Flux<String> mergedFlux = Flux.merge(
                QatarFlights.getFLights(),
                Emirates.getFLights(),
                AirIndia.getFLights()
        );

        mergedFlux.subscribe(Util.subscriber());

        Util.sleepSeconds(10);

    }

}
/*
Simulating publishers
 */
class QatarFlights {

    public static Flux<String> getFLights() {
        return Flux.range(1, Util.faker().random().nextInt(1,5)) // simulating random amount of data to be generated
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "Qatar " + Util.faker().random().nextInt(100, 900))
                .filter(i -> Util.faker().random().nextBoolean());
        // filter() used here, randomly generates true or false, thus allowing random amount of data to be
        // published
    }

}

class Emirates {

    public static Flux<String> getFLights() {
        return Flux.range(1, Util.faker().random().nextInt(1,10))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "Emirates " + Util.faker().random().nextInt(100, 500))
                .filter(i -> Util.faker().random().nextBoolean());
    }

}


class AirIndia {

    public static Flux<String> getFLights() {
        return Flux.range(1, Util.faker().random().nextInt(1,7))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "AirIndia " + Util.faker().random().nextInt(200, 300))
                .filter(i -> Util.faker().random().nextBoolean());
    }

}
