package com.singh.rupesh.part1Mono;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class MonoFromFuture {
    public static void main(String[] args) {
        Mono.fromFuture(getName())   //makes the below method as publisher and then subscribes to this publisher
                .subscribe(Util.onNext());
        Util.sleepSeconds(1);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> Util.faker().name().fullName());
    }
}
