package com.singh.rupesh.part1Mono;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;

public class MonoEmptyOrError {

    public static void main(String[] args) {

        userRepository(1)
                .subscribe(
                        Util.onNext(),
                        Util.onError(),
                        Util.onComplete()
                );
    }

    // a method which can only give data for user id = 1
    // this method acts a publisher as its return type is Mono<String>
    private static Mono<String> userRepository(int userId) {
        if(userId == 1){
            return Mono.just(Util.faker().name().firstName());
        }else if (userId == 2) {
            return Mono.empty(); // empty is like optional and is better than passing null
        }else {
            return Mono.error(new RuntimeException("Not in the allowed range"));
        }
    }
}