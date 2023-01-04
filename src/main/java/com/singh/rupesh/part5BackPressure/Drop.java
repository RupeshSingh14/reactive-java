package com.singh.rupesh.part5BackPressure;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;

/*
Back pressure/ Over flow strategy includes
buffer, drop, latest, error
Internally it is implemented as queue under reactor.util.concurrent.Queues.
The value #System.getProperty("reactor.bufferSize.small", "256") is set to 256 ie..
only 256 emitted values from publisher will be stored in the queue and every thing else will be
dropped. This can be changed by setting manually in program.
 */
public class Drop {

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");
        /*
        Once 75% of 16 values stored in queue ie.. 12 values are consumed by subscriber
        another lot of 75% value of 16 is filled with only the current subsequent values being
        emitted by the publisher at that moment of time (in between emitted values are dropped)
        and is then consumed by subscriber and like so.
        The dropped data can be used to make up resiliency, retry or fallback by creating another sink out of it and sending it to some other
        publisher, or writing it to some file or db like so to be used as per requirement
         */

        List<Object> list = new ArrayList<>();

        Flux.create(fluxSink -> {
            for (int i = 1; i < 501; i++) {
                fluxSink.next(i);
                System.out.println("Pushed : " + i);
                Util.sleepMillis(1);
            }
            fluxSink.complete();
        })
                //.onBackpressureDrop() // this makes the subscriber process only limited data stored in memory
                .onBackpressureDrop(list::add) // for simulating storage of dropped data
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> {
                    Util.sleepMillis(10); //simulating time consuming processing
                })
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);

        System.out.println(list);

    }
}