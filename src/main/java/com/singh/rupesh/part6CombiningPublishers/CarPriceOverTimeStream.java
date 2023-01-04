package com.singh.rupesh.part6CombiningPublishers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
/*
Program to simulate dynamic price of a car bought over the period of time where car price deprecates
for 100 every month but is also affected by the demand factor in that month to calculate the price.
 */
public class CarPriceOverTimeStream {

    public static void main(String[] args) {

        final int carPrice = 10000;
        Flux.combineLatest(monthStream(), demandStream(), (month, demand) -> {
           return (carPrice - (month * 100)) * demand;
        })
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);

    }

    //simulating depreciation every month of car price but kept it here at interval of seconds for demo
    private static Flux<Long> monthStream() {
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(1));
    }

    private static Flux<Double> demandStream() {
        return Flux.interval(Duration.ofSeconds(3))
                .map(i -> Util.faker().random().nextInt(80,120) / 100d)
                .startWith(1d);
    }

}
