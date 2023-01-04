package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OnError {
    public static void main(String[] args) {

        Flux.range(0,10)
                .log()
                .map(i -> 10 / (5 - i))
               // .onErrorReturn(-1) // when error happens, returns this value (-1), call cancel and complete.
               // .onErrorResume(throwable -> fallback())
                .onErrorContinue((throwable, o) -> {
                    System.out.println("the error message: " + throwable.getMessage());
                    System.out.println("the object which caused error: " + o.toString());
                })
                //onErrorContinue does not let the pipeline stop even upon getting error
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100,200));
    }
}
