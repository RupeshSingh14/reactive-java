package com.singh.rupesh.part1Mono;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;

public class MonoSubscriber {
    public static void main(String[] args) {

        //publisher
        Mono<String> mono = Mono.just("Rupesh");

       mono.subscribe(); //Nothing happens since consumer has no action stated but the mono pipeline will be processed

        mono.subscribe(
                item -> System.out.println(item), //onNext
                throwable -> System.out.println(throwable.getMessage()), //onError
                () -> System.out.println("Completed") //OnComplete
        );

        //using operators defined in Util class
        mono.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );
    }
}