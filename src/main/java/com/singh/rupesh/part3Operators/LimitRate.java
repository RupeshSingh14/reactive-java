package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

/*
limiting the rate at which emitted data is sent to subscriber
 */
public class LimitRate {

    public static void main(String[] args) {

        Flux.range(0, 1000)
                .log()
               // .limitRate(100) // requests 100 items initially from publisher and
                // when 75% data (out of initial request) is consumed by subscriber,
                // another 75% of data (out of initial request) is requested and so on
                .limitRate(100, 90)
                //requests 100 data and asks for another 90% of data (out of 100) upon sending
                // 90% of data
                .subscribe(Util.subscriber());

    }

}
