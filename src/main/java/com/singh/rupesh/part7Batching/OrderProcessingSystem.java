package com.singh.rupesh.part7Batching;

import com.singh.rupesh.utils.Util;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class OrderProcessingSystem {

    public static void main(String[] args) {

        Map<String, Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> map = Map.of(
                "Kids", OrderProcessor.kidsProcessing(),
                "Automotive", OrderProcessor.automotiveProcessing()
        );

        Set<String> set = map.keySet();

        OrderService.orderStream()
                .filter(p -> set.contains(p.getCategory()))
                .groupBy(PurchaseOrder::getCategory)
                .flatMap(gf -> map.get(gf.key()).apply(gf))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }

}


class OrderProcessor {
    public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> automotiveProcessing() {
        return flux -> flux
                .doOnNext(p -> p.setPrice(1.1 * p.getPrice()))
                .doOnNext(p -> p.setItem("Auto: " + p.getItem()));

    }

    public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> kidsProcessing() {
        return flux -> flux
                .doOnNext(p -> p.setPrice(0.5 * p.getPrice()))
                .flatMap(p -> Flux.concat(Mono.just(p), getFreeKidsOrder()));

    }

    private static Mono<PurchaseOrder> getFreeKidsOrder() {
        return Mono.fromSupplier(() -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setItem("Free: " + purchaseOrder.getItem());
            purchaseOrder.setPrice(0);
            purchaseOrder.setCategory("Kids");
            return purchaseOrder;
        });
    }
}


class OrderService {

    public static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> new PurchaseOrder());
    }
}


@Data
class PurchaseOrder {

    private String item;
    private double price;
    private String category;

    public PurchaseOrder() {
        this.item = Util.faker().commerce().productName();
        this.price = Double.parseDouble(Util.faker().commerce().price());
        this.category = Util.faker().commerce().department();
    }
}