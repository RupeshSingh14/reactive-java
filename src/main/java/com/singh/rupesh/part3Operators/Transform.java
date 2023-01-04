package com.singh.rupesh.part3Operators;

import com.singh.rupesh.utils.Util;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/*
Transform is used to abstract out set of pipe line operations into a method to apply it using tranform()
 */
public class Transform {

    public static void main(String[] args) {
        getPerson()
                //switch on first checks for the first data for the set condition, if it passes it allows
                //all the data through pipeline as is else sends all the data from defined else pipeline
                .switchOnFirst((signal, personFlux) -> {
                    System.out.println("inside switch on first");
                   return signal.isOnNext() && signal.get().getAge() > 10 ? personFlux : applyFilterMap().apply(personFlux);
                })
               // .transform(applyFilterMap()) //commented to test switchOnFirst
                .subscribe(Util.subscriber());
    }

    public static Flux<Person> getPerson() {
        return Flux.range(1, 10)
                .map(integer -> new Person());
    }

    // the function which is used to add custom set of operations and passed as argument to transform
    public static Function<Flux<Person>, Flux<Person>> applyFilterMap() {
        return flux -> flux.filter(person -> person.getAge() > 10)
                //for operation on data which passes the condition
                .doOnNext(person -> person.setName(person.getName().toUpperCase()))
                //for operation on data which fails the condition
                .doOnDiscard(Person.class, person -> System.out.println("Not allowing : " + person));
    }

}

@Data
class Person {

    String name;
    int age;

    public Person() {
        this.name = Util.faker().name().firstName();
        this.age = Util.faker().random().nextInt(1, 30);
    }
}