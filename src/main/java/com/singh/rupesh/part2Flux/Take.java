package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
Take(operator) takes only the specified amount values and
then first calls cancel on producer and then OnComplete on the subscriber
 */
public class Take {

    public static void main(String[] args) {
        Flux.range(0,10)
                .log()
                .skipUntil(integer -> integer == 5) // cancels the subscription after getting 3rd value and calls complete
                .log()
                .subscribe(Util.subscriber());
    }

    Mono<String> s1 = Mono.just("Rupesh");
    Mono<String> s2 = Mono.just("Ram");





}
