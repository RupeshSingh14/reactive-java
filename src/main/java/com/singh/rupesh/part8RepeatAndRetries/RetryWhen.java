package com.singh.rupesh.part8RepeatAndRetries;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class RetryWhen {

    public static void main(String[] args) {

        orderService(Util.faker().business().creditCardNumber())
                .retryWhen(Retry.from(  // advanced usage of retry simulating real time scenarios
                        flux -> flux
                        .doOnNext(rs -> {
                            System.out.println(rs.totalRetries());
                            System.out.println(rs.failure());
                        })
                        .handle((retrySignal, synchronousSink) -> {
                            if(retrySignal.failure().getMessage().equals("500"))
                                synchronousSink.next(1);
                            else
                                synchronousSink.error(retrySignal.failure());
                        }).delayElements(Duration.ofSeconds(1))
                ))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }

    //order service
    private static Mono<String> orderService(String ccNumber) {
        return Mono.fromSupplier(() -> {
            processPayment(ccNumber);
            return Util.faker().idNumber().valid();
        });
    }

    //payment service
    private static void processPayment(String ccNumber) {
        int random = Util.faker().random().nextInt(1, 10);
        if (random < 8)
            throw new RuntimeException("500");
        else if (random < 10)
            throw new RuntimeException("404");
    }







}
