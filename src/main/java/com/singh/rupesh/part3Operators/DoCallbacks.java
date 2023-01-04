package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

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
                .doFirst(() -> System.out.println("doFirst"))
                .doOnNext(o -> System.out.println("doOnNext " + o))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe " + subscription))
                .doOnRequest(i -> System.out.println("doOnRequest " + i))
                .doOnError(throwable -> System.out.println("doOnError " + throwable.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doFinally(signalType -> System.out.println("doOnFinally " + signalType))
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
