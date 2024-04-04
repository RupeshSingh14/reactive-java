package com.singh.rupesh.utils;

import java.util.HashMap;
import java.util.Map;

public class CountingWordsInASentence {

    public static void main(String[] args) {

        String str = "Gaya gaya gaya gaya hi reh gya.";
        String str1 = str.toUpperCase();
        Map<String, Integer> map = new HashMap<>();

        String[] words = str1.split(" ");

        for(String word: words){
            if(map.containsKey(word)){
                map.put(word, map.get(word) + 1);
            }else
                map.put(word, 1);
        }
        System.out.println(map);

        String string = String.format("I have %,.2f bugs to fix", 47658.09876);
        System.out.println(string);
        //I have 47,658.10 bugs to fix
    }
}
