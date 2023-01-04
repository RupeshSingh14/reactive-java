package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

/*
This is flux way to iterate, like a for loop
 */
public class Range {

    public static void main(String[] args) {
        Flux.range(2, 10)
                .subscribe(
                        Util.onNext()
                );

        Flux.range(2, 10)
                .log() //logs all the process between subscriber and publisher
                .map(i -> Util.faker().name().fullName())
                .log()
                .subscribe(
                        Util.onNext()
                );
    }
}
