package com.singh.rupesh.part10Contexts;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
Implementing a rate limit functionality by using context
 */
public class ContextRateLimitImplementation {

    public static void main(String[] args) {

        //defining a pipeline to initiate the process of applying the context, filtering and applying retry logic on basis of it.
        BookService.getBook()
                .repeat(2)
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user", "riddhi"))
                //.contextWrite(Context.of("user", "raunak")) //standard category user
                .subscribe(Util.subscriber());
    }
}

class BookService {

    private static final Map<String, Integer> map = new HashMap<>();

    //adding a map with amount of retries possible for each category
    static {
        map.put("standard", 2);
        map.put("prime", 3);
    }

    //applying the below function on the pipeline where filtering is done on basis of context of allow value.
    public static Mono<String> getBook() {
        return Mono.deferContextual(ctx -> {
            if(ctx.get("allow")) {
                return Mono.just(Util.faker().book().title());
            }else {
                return Mono.error(new RuntimeException("not allowed"));
            }
        })
                .contextWrite(rateLimiterContext());
    }


    //a function which takes a context as an argument, deduces the category and its allowed attempts of retires, subtracts a retry and then
    // returns a context with added context of allowing or not allowing with boolean values.
    private static Function<Context, Context> rateLimiterContext () {
        return ctx -> {
            if(ctx.hasKey("category")){
                String category = ctx.get("category").toString();
                Integer attempts = map.get(category);
                if(attempts > 0) {
                    map.put(category, attempts - 1);
                    return ctx.put("allow", true);
                }
            }
            return ctx.put("allow", false);
        };
    }
}


class UserService {

    //declaring a map with users of standard and prime category to be used to apply context and filter
    private static final Map<String, String> map = Map.of(
            "raunak", "standard",
            "riddhi", "prime"
    );

    //a function which takes a context and returns a context with added value as category. This is applied in main class in subscriber.
    public static Function<Context, Context> userCategoryContext() {
        return ctx -> {
            String user = ctx.get("user").toString();
            String category = map.get(user);
            return ctx.put("category", category);
        };
    }
}