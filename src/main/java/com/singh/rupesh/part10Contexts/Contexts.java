package com.singh.rupesh.part10Contexts;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/*
Contexts are additional values (key-value pairs) like http headers set at downstream which are viewed
by all upstream pipeline operators
Contexts are thread safe and immutable
 */
public class Contexts {

    public static void main(String[] args) {


        getWelcomeMessage()
                .contextWrite(ctx -> ctx.put("user", ctx.get("user").toString().toUpperCase())) //updating the context
                .contextWrite(Context.of("user", "Rupesh")) //defining a context here
                .subscribe(Util.subscriber());

    }

    /*
    The publisher is using the downstream context as a filter condition to process the request
     */
    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")) {
                return Mono.just("Welcome " + ctx.get("user"));
            } else {
                return Mono.error(new RuntimeException("unauthenticated"));
            }
        });
    }

}
