package com.singh.rupesh.part6CombiningPublishers;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    List<String> list = new ArrayList<>();

    public Flux<String> generateNames() {
        return Flux.generate(stringSynchronousSink -> {
            System.out.println("generated fresh");
            Util.sleepSeconds(1);
            String name = Util.faker().name().firstName();
            list.add(name); //simulating a cache
            stringSynchronousSink.next(name);
        })
                .cast(String.class)
                .startWith(getFromCache()); // this makes the flux emit data stored in cache first
        // whenever called for the second time and onwards
    }

    private Flux<String> getFromCache() {
        return Flux.fromIterable(list);
    }
}