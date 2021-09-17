package com.example.restfulwebservice.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@RestController
public class UserRestController {

    @Autowired
    private UserDaoService service;




    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){ // form or json 같은 것을 받으려면 매개변수 타입을 정해줘야한다.
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

    }



    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }



    @GetMapping("/flux/{id}")
    public Flux<ServerSentEvent<User>> flux(@PathVariable int id) throws Exception{
        return Flux.interval(Duration.ofSeconds(1))
                .map(i-> service.findOne(id))
                .map(user -> ServerSentEvent.builder(user).build());
    }


    private static final List<User> list =new ArrayList<>();

    @PostMapping("/users/{id}")
    public void createUser ( @PathVariable int id){
        User user = new User(id,"창훈",new Date());
        service.save(user);
        list.clear();
        list.add(user);
    }


    @GetMapping("/flux/subscribe")
    public Flux<ServerSentEvent<User>> subscribe() throws Exception{
        return Flux.fromStream(list.stream().map(user -> ServerSentEvent.builder(user).build()));
    }





}
