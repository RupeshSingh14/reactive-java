package com.singh.rupesh.part4Schedulers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
Publish on is for downstream
Publish on is used at the subscriber end for defining the scheduler thread pools so that we can
implement how the data sent from publisher will be consumed at subscriber end.
In case we have publish on as well as subscribe on defined, then data processed by publisher will use thread pool operator defined by the
the subscribe on and when it comes to subscriber it will use thread pool operator defined by the publish on to process the pipeline.
 */
public class PublishOnDemo {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 5 ; i++) {
                objectFluxSink.next(i);
            }
            objectFluxSink.complete();
        })
                .doOnNext(i -> printThreadName("next " + i));

        // we can have multiple publish on also, for the kind of task we want to achieve at each step
        flux
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next " + i))
                .publishOn(Schedulers.parallel())
                .subscribe(v -> printThreadName("sub " + v));

        // demo of publish on and subscribe on used.
        flux
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next " + i))
                .subscribeOn(Schedulers.parallel())
                .subscribe(v -> printThreadName("sub " + v));


        Util.sleepSeconds(5); //to prevent the execution of main thread to exit program
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread : " + Thread.currentThread().getName());
    }




}
