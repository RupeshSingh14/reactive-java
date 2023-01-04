package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.ZoneOffset.UTC;

/*
A program to simulate the price of a stock which quits if price exceeds the allowed range (90-110)
 */
public class StockPricePublisher {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1); // initializing the latch value to 1.
        // with one countdown() executed, the value of latch becomes 0 which will then allow
        //main thread to no more await for any thread and complete the program.

        getPrice().subscribeWith(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE); //for unbounded limit
                this.subscription = subscription;

            }

            @Override
            public void onNext(Integer price) {
                System.out.println(LocalDateTime.now() + " : Price : " + price);
                if (price > 110 || price < 90){
                    System.out.println("Price crossed allowed range");
                    this.subscription.cancel();
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await(); // to keep the main thread not close the program unless latch countdown is 0.
    }


    public static Flux<Integer> getPrice() {
        AtomicInteger atomicInteger = new AtomicInteger(100);
        return Flux.interval(Duration.ofSeconds(1))  // interval makes the process executed on a different thread
                .map(i -> atomicInteger.getAndAccumulate(
                        Util.faker().random().nextInt(-5,5),
                        Integer::sum //(a,b) -> a + b
                ));
    }
}
