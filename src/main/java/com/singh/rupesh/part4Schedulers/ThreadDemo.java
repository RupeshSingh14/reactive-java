package com.singh.rupesh.part4Schedulers;

import reactor.core.publisher.Flux;

/*
This is how a thread executes sequentially in case of publisher and subscriber
Project reactor provides with Schedulers which help us to execute the code using efficient thread pools

Scheduler methods
boundedElastic -> for Network/time consuming calls - used mostly, as it has 10x times of no of cpu cores in machine as total threads available
in thread pool ie.. for machine with 4 cpu - it will allocate 40 threads. this will ensure that we won't run out of thread in most scenarios

parallel -> for CPU intensive tasks - it provides threads as no of cpu cores in machine ie.. 4 threads in pool for 4 cpu core machine.
single -> A single dedicated thread for one off tasks
immediate -> for current thread execution

 */
public class ThreadDemo {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(objectFluxSink -> {
            printThreadName("create");
            objectFluxSink.next(1);
        })
                .doOnNext(i -> printThreadName("next " + i));

        flux.subscribe(v -> printThreadName("sub " + v));
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread : " + Thread.currentThread().getName());
    }

}
