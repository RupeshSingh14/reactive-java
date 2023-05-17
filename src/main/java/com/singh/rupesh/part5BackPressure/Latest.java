package com.singh.rupesh.part5BackPressure;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Latest {

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
            //for (int i = 1; i < 201; i++) {
            for (int i = 1; i < 201 && !fluxSink.isCancelled(); i++) { // the isCancelled condition checks on error
                fluxSink.next(i);
                System.out.println("Pushed : " + i);
                Util.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureBuffer(20) // to process only 20 items
                //.onBackpressureError() // for error, we have put a condition above to check if flux is not cancelled and then only emit data
               // .onBackpressureLatest() // works just like drop but stores the the last element instead of dropping it as cache for processing
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> {
                    Util.sleepMillis(10); //simulating time consuming processing
                })
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

}
