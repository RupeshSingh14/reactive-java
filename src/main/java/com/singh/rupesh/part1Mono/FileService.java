package com.singh.rupesh.part1Mono;

/*
read file and return content
create file and write content
delete file
 */

import com.singh.rupesh.utils.Util;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    private static final Path PATH = Paths.get("src/main/resources/part1");

    public static void main(String[] args) {

        FileService.read("file1.txt")
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

       // FileService.write("file3.txt", "This is file 3.")
         //       .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        FileService.delete("file3.txt")
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

    }

    public static Mono<String> read(String fileName) {
        return Mono.fromSupplier(() -> readFile(fileName));
    }

    public static Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> writeFile(fileName, content));
    }

    public static Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> deleteFile(fileName));
    }

    private static String readFile(String fileName) {
        try {
            return Files.readString(PATH.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFile(String fileName, String content) {
        try {
            Files.writeString(PATH.resolve(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteFile(String fileName){
        try {
            Files.delete(PATH.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
