package com.singh.rupesh.part1Mono;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/*
actual business logic is inside supplier which will be lazy evaluated ie..
the pipe line will be built and checked by compiler but it will only get executed upon calling
the subscribe method.
 */
public class SupplierRefactoring {
    public static void main(String[] args) {
        getName();
        // getName().subscribe(Util.onNext()); // this has blocking nature as it gets executed on main thread
        getName().subscribeOn(Schedulers.boundedElastic()) // this executes in async way
                .subscribe(Util.onNext());
        getName();

        String name = getName()
                .subscribeOn(Schedulers.boundedElastic())
                .block(); // this also internally implements subscribe by use of threads
        // but should be avoided for use and be used for testing purpose
        System.out.println(name);

        Util.sleepSeconds(4);
    }

    /*acts as a publisher
    actual business logic is inside the supplier so that it gets lazily executed, so unless a subscriber
    subscribe to it, it wont get executed*/
    public static Mono<String> getName() {
        System.out.println("Entered get name method");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name...");
            Util.sleepSeconds(2);
            return Util.faker().name().fullName();
        }).map(String::toUpperCase);
    }
}