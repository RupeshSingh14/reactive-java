package com.singh.rupesh.part1Mono;

import java.util.stream.Stream;

/*
Streams are lazy in behaviour ie.. unless a terminal operation is not called
upon it, it won't get executed
 */
public class LazyStreams {

    public static void main(String[] args) {

        Stream<Integer> integerStream = Stream.of(1)
                .map(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i * 2;
                });

        System.out.println(integerStream); // OP: java.util.stream.ReferencePipeline$3@7f63425a
        integerStream.forEach(System.out::println); // forEach is a terminal operator for stream

    }
}
