package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
/*
There are many type of call backs which can be defined. the flow of all these call backs start
from the subscriber to the publisher. So call backs defined nearer to the subscriber will get executed
executed first based on the kind of callback it is.
 */
public class DoCallbacks {

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
            System.out.println("inside create");
            for (int i = 0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
            System.out.println("-- Completed");
        })
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doFirst(() -> System.out.println("doFirst1"))  // this is takes the top priority to get executed among all callbacks
                .doOnNext(o -> System.out.println("doOnNext " + o))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe " + subscription)) // this will take priority among all callbacks except do first
                // This works opposite of doFirst ie.. one defined closer to publisher takes priority then the other.
                .doOnRequest(i -> System.out.println("doOnRequest " + i))//  this takes priority after doOnSubscribe()
                .doOnError(throwable -> System.out.println("doOnError " + throwable.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doFinally(signalType -> System.out.println("doOnFinally " + signalType))
                .doFirst(() -> System.out.println("doFirst2"))  // since this is nearer to subscriber this will get executed first
                .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard " + o))
               // .take(2)
                .subscribe(Util.subscriber());

    }

/*
    doFirst
doOnSubscribe reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber@275710fc
doOnRequest 9223372036854775807
inside create
doOnNext 0
Rupesh - Received : 0
doOnNext 1
Rupesh - Received : 1
doOnNext 2
Rupesh - Received : 2
doOnNext 3
Rupesh - Received : 3
doOnNext 4
Rupesh - Received : 4
doOnComplete
doOnTerminate
Rupesh - Completed
doOnFinally onComplete
 */

//When take is called
/*
doFirst
doOnSubscribe reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber@4de5031f
doOnRequest 9223372036854775807
inside create
doOnNext 0
Rupesh - Received : 0
doOnNext 1
Rupesh - Received : 1
doOnCancel
doOnFinally cancel
Rupesh - Completed
doOnDiscard 2
doOnDiscard 3
doOnDiscard 4
-- Completed
 */


}
