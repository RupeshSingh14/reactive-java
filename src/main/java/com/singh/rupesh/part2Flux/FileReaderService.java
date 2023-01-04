package com.singh.rupesh.part2Flux;

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/*
A file reader service to which reads a file and prints it to console
 */
public class FileReaderService {

    public static void main(String[] args) {
        FileReaderImplementation fileReader = new FileReaderImplementation();
        Path path = Paths.get("src/main/resources/part1/file3.txt");
        fileReader.read(path)
                .take(20)
                .subscribe(Util.subscriber());
    }

}

class FileReaderImplementation {
    private Callable<BufferedReader> openReader(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    private BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return (br, sink) -> {
            try {
                String line = br.readLine();
                if(Objects.isNull(line))
                    sink.complete();
                else
                    sink.next(line);
            } catch (IOException e) {
                sink.error(e);
            }
            return br;
        };
    }

    private Consumer<BufferedReader> closeReader() {
        return br -> {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public Flux<String> read(Path path) {
        return Flux.generate(
                openReader(path),
                read(),
                closeReader()
        );
    }
}

