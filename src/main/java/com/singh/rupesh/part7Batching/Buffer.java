package com.singh.rupesh.part7Batching;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
Batching is process of collecting an amount of data and sending it to subscriber at once.
With Buffer, we collect a specified amount of emitted data at once and sent it to subscriber
as a repeated process defined by size or duration period
If count of emitted data is less than buffer size and an onComplete signal is sent than whatever
data is in buffer will be sent to subscriber but in case onComplete signal is not sent buffer will
wait for next data.
 */
public class Buffer {

    public static void main(String[] args) {
        eventStream()
                //.buffer(5) // this creates each buffer of 5 flux of data is converted into List<Flux<String>>
                //.buffer(Duration.ofSeconds(2))
                //this creates buffer with amount of data received during the set time interval
                //.bufferTimeout(5, Duration.ofSeconds(1))
                // a hybrid of both, size and duration, whichever happens first.
                .buffer(3, 5) // skip value can be less or more than size.
                //size of buffer will be 3 but it has to skip or drop last 5 data in next batch
                // this is used for sampling of data on long intervals, like to have data quality check
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<String> eventStream (){
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> "event" + i);
    }


}
