package com.singh.rupesh.part4Publishers;

import com.singh.rupesh.utils.Util;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class InventoryRevenueService {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        RevenueService revenueService = new RevenueService();
        InventoryService inventoryService = new InventoryService();

        orderService.orderStream()
                .subscribe(revenueService.subscriberOrderStream());

        orderService.orderStream()
                .subscribe(inventoryService.subscriberOrderStream());

        inventoryService.inventoryStream()
                .subscribe(Util.subscriber("Inventory"));

        revenueService.revenueStream()
                .subscribe(Util.subscriber("Revenue"));

        Util.sleepSeconds(120);
    }

}

class InventoryService {

    private Map<String, Integer> db = new HashMap<>();

    public InventoryService() {
        db.put("Kids", 100);
        db.put("Automotive", 100);
    }

    public Consumer<PurchaseOrder> subscriberOrderStream() {
        return p -> db.computeIfPresent(p.getCategory(), (k,v) -> v - p.getQuantity());
    }

    public Flux<String> inventoryStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString());
    }
}


class RevenueService {

    private Map<String, Double> db = new HashMap<>();

    public RevenueService() {
        db.put("Kids", 0.0);
        db.put("Automotive", 0.0);
    }

    public Consumer<PurchaseOrder> subscriberOrderStream() {
        return p -> db.computeIfPresent(p.getCategory(), (k,v) -> v + p.getPrice());
    }

    public Flux<String> revenueStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString());
    }
}


class OrderService {

    private Flux<PurchaseOrder> flux;

    public Flux<PurchaseOrder> orderStream() {
        if(Objects.isNull(flux))
            flux = getOrderStreams();
        return flux;
    }

    private Flux<PurchaseOrder> getOrderStreams() {
        return Flux
                .interval(Duration.ofMillis(100))
                .map(i -> new PurchaseOrder())
                .publish()
                .refCount(2);
    }

}


@Data
class PurchaseOrder {

    private String item;
    private double price;
    private String category;
    private int quantity;

    public PurchaseOrder() {
        this.item = Util.faker().commerce().productName();
        this.price = Double.parseDouble(Util.faker().commerce().price());
        this.category = Util.faker().commerce().department();
        this.quantity = Util.faker().random().nextInt(1,10);
    }
}