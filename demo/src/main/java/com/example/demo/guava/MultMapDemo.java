package com.example.demo.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author lee
 */
public class MultMapDemo {
    public static void main(String[] args) {
        Multimap<Object, Object> listMultimap = ArrayListMultimap.create();
        listMultimap.put(1, 3);
        listMultimap.put(1, 3);
        listMultimap.put(1, 33);
        listMultimap.get(1).forEach(x -> System.out.println(x));

    }
}
