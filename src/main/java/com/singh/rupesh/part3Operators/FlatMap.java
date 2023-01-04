package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlatMap {

    public static void main(String[] args) {

        UserService.getUsers()
                .log()
                .flatMap(user -> OrderService.getAllOrders(user.getUserId()))
                .subscribe(Util.subscriber());

    }
}

@Data
class User {

    private int userId;
    private String name;

    public User(int userId) {
        this.userId = userId;
        this.name = Util.faker().name().fullName();
    }
}

@Data
class Order {

    private String item;
    private String price;
    private int userId;

    public Order(int userId) {
        this.userId = userId;
        this.item = Util.faker().commerce().productName();
        this.price = Util.faker().commerce().price();
    }
}

class OrderService {

    private static Map<Integer, List<Order>> db = new HashMap<>();

    static {
        List<Order> list1 = Arrays.asList(
                new Order(1),
                new Order(1),
                new Order(1)
        );

        List<Order> list2 = Arrays.asList(
                new Order(2),
                new Order(2)
        );

        db.put(1, list1);
        db.put(2, list2);
    }

    public static Flux<Order> getAllOrders(int userId) {
        return Flux.create(orderFluxSink -> {
            System.out.println("I am in getAllOrders");
            db.get(userId).forEach(orderFluxSink::next);
            orderFluxSink.complete();
        });
    }
}

class UserService {

    public static Flux<User> getUsers() {
        return Flux.range(1,2)
                .map(integer -> new User(integer));
    }

}
