package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.util.Locale;
/*
Flux.push also works like create but it is not thread safe like create. So push is only restricted to single thread usage.
 */
public class FluxCreate {

    public static void main(String[] args) {

        //only once instance of flux sink is provided which can be used multiple times
       Flux.create(fluxSink -> {
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.complete();
        }).subscribe(Util.subscriber());


        Flux.create(fluxSink -> {
            String country;

            do{
                country = Util.faker().country().name();
                fluxSink.next(country);
            }while(!country.equalsIgnoreCase("INDIA"));
            fluxSink.complete();
        }).subscribe(Util.subscriber());
    }
}
