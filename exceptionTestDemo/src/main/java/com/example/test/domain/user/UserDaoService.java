package com.example.test.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserDaoService {

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(int id){
        return userRepository.findById(id);
    }

    public User save(User user) throws IOException {
        return userRepository.save(user);
    }

    public User saveWithCheckedException(User user) throws IOException {
        User savedUser = userRepository.save(user);
        if(1==1)throw new IOException();
        return savedUser;
    }


    public User saveWithUncheckedException(User user) throws NullPointerException {
        User savedUser = userRepository.save(user);
        if(1==1)throw new NullPointerException();
        return savedUser;
    }
}
