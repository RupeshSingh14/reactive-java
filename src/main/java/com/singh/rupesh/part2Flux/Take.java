package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

/*
Take take only the specified amount values and
then first calls cancel on producer and then OnComplete on the subscriber
 */
public class Take {

    public static void main(String[] args) {
        Flux.range(0,10)
                .log()
                .take(3)
                .log()
                .subscribe(Util.subscriber());
    }
}
