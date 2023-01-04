package com.singh.rupesh.part4Schedulers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


/*
in this demo, we see that only 1 thread executes all the 20 iterations
Schedulers != Parallel execution
All the operations are always executed sequentially
Data is processed one by one via 1 thread in the thread pool for a subscriber
Schedulers.parallel() is a thread pool for CPU tasks and does not mean parallel execution

Schedulers will have thread pool which will share threads among operations like a publisher sending
message to many subscribers, then a pool of threads will be shared among subscribers to do the
task.
 */
public class SubscribeOnMultipleItems {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 5 ; i++) {
                objectFluxSink.next(i);
            }
            objectFluxSink.complete();
        })
                .doOnNext(i -> printThreadName("next " + i));

//        flux
//                .subscribeOn(Schedulers.boundedElastic())
//                .subscribe(v -> printThreadName("sub " + v));

        // simulating multiple subscribers
        Runnable runnable = () -> flux
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("From runnable sub " + v));

        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(5); //to prevent the execution of main thread to exit program
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread : " + Thread.currentThread().getName());
    }
}
