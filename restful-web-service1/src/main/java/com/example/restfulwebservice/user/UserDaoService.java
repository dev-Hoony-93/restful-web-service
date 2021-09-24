package com.example.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
//        for (int i =0 ;i< 10000;i++) {
//            users.add(new User(i, "hoony"+i , new Date()));
//        }

        users.add(new User(1,"hoony",new Date()));
        users.add(new User(2,"Alice",new Date()));
        users.add(new User(3,"Elena",new Date()));
    }

    public List<User> findAll(){
        return  users;
    }

    public User findOne(int id){
        for ( User user : users){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    public User save(User user) throws IOException {
        if (user.getId() == null){
            user.setId(++usersCount);
        }

        users.add(user);

        return user;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();

       while (iterator.hasNext()){
           User user = iterator.next();
           if (user.getId() == id){
               iterator.remove();
               return user;
           }
       }

       return null;
    }

}
