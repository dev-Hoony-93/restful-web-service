package com.hoony;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        Stream<Integer> stream = Stream.iterate(1, a -> a+1).limit(11);
        List<Integer> list = stream.collect(Collectors.toList());
        list.stream().mapToInt(Integer::valueOf).toArray();
        System.out.println(list);
    }

}
