package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class ArraysAndList {

    public static void main(String[] args) {

        List<String> stringList = Arrays.asList("a", "d", "e", "z");
        Flux.fromIterable(stringList)
                .subscribe(Util.onNext());

        Integer[] arr = {1,4,6,9};
        Flux.fromArray(arr).subscribe(Util.onNext());
    }
}
