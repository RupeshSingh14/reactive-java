package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

public class FluxToMono {
    public static void main(String[] args) {

        Flux.range(1,10)
                .next() //it will only let process 1 item (value = 1) and forward the control, making it a mono.
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        Flux.range(1,10)
                .filter(i -> i > 4)
                .next() //it will only process value 5
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }
}
