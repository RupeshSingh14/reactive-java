package com.singh.rupesh.part1Mono;

import reactor.core.publisher.Mono;

/*
Since streams are lazy evaluated, nothing happens unless a publisher is subscribed
 */
public class JustMono {
    public static void main(String[] args) {

        //publisher emitting only 1 as value
        //Easiest way to create a mono when we already have the data
        Mono<Integer> mono = Mono.just(1);

        System.out.println(mono); // OP: MonoJust

        //subscriber has subscribed to publisher and stated an action
        mono.subscribe(i -> System.out.println("Received : " + i)); // OP: Received : 1

    }
}
