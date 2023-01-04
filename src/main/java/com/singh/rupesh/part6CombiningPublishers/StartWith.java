package com.singh.rupesh.part6CombiningPublishers;

import com.singh.rupesh.utils.Util;

public class StartWith {

    public static void main(String[] args) {
        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("ram"));

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("sam"));

        nameGenerator.generateNames()
                .take(3)
                .subscribe(Util.subscriber("nam"));

        nameGenerator.generateNames()
                .filter(n -> n.startsWith("A"))
                .take(2)
                .subscribe(Util.subscriber("fam"));



    }
}
