package com.singh.rupesh;

import com.github.javafaker.Book;
import com.singh.rupesh.utils.Util;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

// delay test
public class AssertTest {

    @Test
    public void test1() {

        Mono<BookOrder> mono = Mono.fromSupplier(() -> new BookOrder());

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .verifyComplete();
    }

    @Test
    public void test2() {

        Mono<BookOrder> mono = Mono.fromSupplier(() -> new BookOrder())
                .delayElement(Duration.ofSeconds(3));

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .verifyComplete(); // internally StepVerifier blocks the thread and does the work
    }

    @Test
    public void test3() {

        Mono<BookOrder> mono = Mono.fromSupplier(() -> new BookOrder())
                .delayElement(Duration.ofSeconds(3));

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .expectComplete()
                .verify(Duration.ofSeconds(4)); // to verify the exact time taken for the process
    }

}

@ToString
@Getter
class BookOrder {

    private String title;
    private String author;
    private String category;
    private double price;

    public BookOrder() {
        Book book = Util.faker().book();
        this.title = book.title();
        this.author = book.author();
        this.category = book.genre();
        this.price = Double.parseDouble(Util.faker().commerce().price());
    }

}
