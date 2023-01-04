package com.singh.rupesh.part1Mono;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/*
Just is used when data is already available. Supplier is used to get data from somewhere.
Supplier takes no input but emits output. Likewise Callable can also be used for such purpose
 */
public class MonoFromSupplier {

    public static void main(String[] args) {

        Supplier<String> stringSupplier = () -> getName();
        Mono<String> mono = Mono.fromSupplier(stringSupplier);
       // Mono<String> mono = Mono.fromSupplier(() -> getName());

        mono.subscribe(
                Util.onNext()
        );

        Callable<String> stringCallable = () -> getName();
        Mono<String> mono1 = Mono.fromCallable(stringCallable);
        mono1.subscribe(
                Util.onNext()
        );
    }

    private static String getName() {
        System.out.println("Generating name: ");
        return Util.faker().name().fullName();
    }
}