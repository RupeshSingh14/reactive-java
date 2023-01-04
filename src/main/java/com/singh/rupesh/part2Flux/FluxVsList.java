package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class FluxVsList {

    public static void main(String[] args) {
       // List<String> names = NameGenerator.getNames(5);
       // System.out.println(names);

        //this provides the data on real time ie.. as and when data is processed through pipeline
        NameGenerator.getNames2(5)
                .subscribe(Util.onNext());
    }
}

class NameGenerator {

    public static Flux<String> getNames2(int count){
        return Flux.range(0,5)
                .map(i -> getName());
    }

    // this method collects all the data first and presents the list at once after 5 seconds since thread
    //goes to sleep for 1 sec in each iteration we are don't get any output till the entire process is
    //completed.
    public static List<String> getNames(int count) {
        List<String> nameList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
           nameList.add(getName());
        }
        return nameList;
    }

    private static String getName() {
        Util.sleepSeconds(1);
        return Util.faker().name().fullName();
    }
}