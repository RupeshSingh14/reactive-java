package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
emitting and sending data can be controlled delayed. The data requested by the operator has default
value set to 32 and then upon 75% data is consumed by subscriber, another 75% of 32 is requested
 */
public class Delay {
    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.x", "9"); // sets the default request size as 9.

        Flux.range(0,100)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }
}
