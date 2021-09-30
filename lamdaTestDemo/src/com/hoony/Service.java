package com.hoony;

import java.util.*;

public class Service {
    public void sol(){
        List<User> list = Arrays.asList(new User("hoon"),new User("Lee"), new User("bum"),new User("Song"));
        Comparator<User> com = (u1,u2) -> u2.name.toLowerCase().compareTo(u1.name.toLowerCase());
        list.sort(com);
        list.stream().forEach(System.out::println);
    }

    public void solution(User [] arr){
        List<User> list = new ArrayList<>();
        User [] arr = {new User("hoon"),new User("Lee"), new User("bum"),new User("Song")};
        for ( User u: arr ) {
            list.add(u);
        }
        Comparator<User> com = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u2.name.toLowerCase().compareTo(u1.name.toLowerCase());
            }
        };
        list.sort(com);
        for ( User u: list  ) {
            System.out.println(u);
        }

    }


    public static void main(String[] args) {
        Service s= new Service();
        s.sol();
        s.sol2();
    }
}
