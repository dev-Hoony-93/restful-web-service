package com.hoony;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Service {
    public void solution(User [] arr){
        Arrays.asList(arr).stream()
                .sorted((u1,u2) -> u2.name.toLowerCase().compareTo(u1.name.toLowerCase()))
                .forEach(System.out::println);

    }

    public void solution2(User [] arr){
        List<User> list = new ArrayList<>();
        for ( User u: arr ) {
            list.add(u);
        }
        List<Integer> list1 = new ArrayList<>();

        Comparator<User> com = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u2.name.toLowerCase().compareTo(u1.name.toLowerCase());
            }
        };
        list.sort(com);
        list.stream().forEach(new Consumer<User>() {
            public void accept(User user) {
                System.out.println(user);
            }
        });
    }


    public static void main(String[] args) {
        Service s= new Service();
        User [] arr = {new User("hoon"),new User("Lee"), new User("bum"),new User("Song")};
        s.solution(arr);
        System.out.println();
        s.solution2(arr);
    }
}
