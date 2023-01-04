package com.singh.rupesh.part4Schedulers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
Subscribe is for upstream
Schedulers come in action upon use of operator like subscribe on or publish on
The main thread executes the process, once it sees the subscribe on operator, the control is passed to the defined scheduler process to execute
the code from there on.
In case of multiple subscribers the one closest to the publisher will take precedence once the control reaches to the scheduler operator defined
in that subscribe on

 */
public class SubscribeOnDemo {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
            printThreadName("create");
            objectFluxSink.next(1);
        })
                //.subscribeOn(Schedulers.newParallel("Rupesh")) // for showing demo of multiple subscribe on
                .doOnNext(i -> printThreadName("next " + i));

        // the main thread first executes the do first 1 and then goes upstream to execute do first 2 but before that it gets on subscribe on
        // and so passes the control to bounded elastic thread to execute
        flux
                .doFirst(() -> printThreadName("doFirst 2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("doFirst 1"))
                .subscribe(v -> printThreadName("sub " + v));


        // makes the function executed by 2 separate threads
        // simulating multiple subscribers
        Runnable runnable = () -> flux
                .doFirst(() -> printThreadName("From runnable doFirst 2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("From runnable doFirst 1"))
                .subscribe(v -> printThreadName("From runnable sub " + v));

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(5); //to prevent the execution of main thread to exit program
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread : " + Thread.currentThread().getName());
    }

}
