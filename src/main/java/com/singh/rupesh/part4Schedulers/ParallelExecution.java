package com.singh.rupesh.part4Schedulers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
Flux Interval and delayed seconds methods internally implements schedulers parallel operator. So it they are applied to a stream and have
subscribe on with boundedElastic down the stream in pipeline, still flux interval's parallel will take precedence.
 */
public class ParallelExecution {

    public static void main(String[] args) {

        Flux.range(1,100)
                .parallel()   //.parallel(20) //count of threads we want to run in parallel
                .runOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next " + i))
                .sequential() //make the state back to normal flux of data so that we can apply subscribe on or publish on
                .subscribeOn(Schedulers.boundedElastic())// with parallel operator in stream this operator cant be applied unless we use sequential()
                .subscribe(v -> printThreadName("sub " + v));

        Util.sleepSeconds(5); //to prevent the execution of main thread to exit program
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\t\t: Thread : " + Thread.currentThread().getName());
    }

}
