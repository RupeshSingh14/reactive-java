package com.singh.rupesh.part5BackPressure;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
/*
While the subscriber takes time to process the data pipeline, the subscriber keeps emitting the data
which is stored in memory and later processed by the subscriber. In certain cases, this can lead
to OOM error at some instance of time.
 */
public class Demo {

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
            for (int i = 1; i < 501; i++) {
                fluxSink.next(i);
                System.out.println("Pushed : " + i);
            }
            fluxSink.complete();
        })
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> {
                    Util.sleepMillis(10); //simulating time consuming processing
                })
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);


    }
}
