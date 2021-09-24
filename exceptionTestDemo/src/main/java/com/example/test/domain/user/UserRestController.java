package com.example.test.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserRestController {

    @Autowired
    private UserDaoService service;




    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        Optional<User> user = service.findOne(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return user.get();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws IOException{ // form or json 같은 것을 받으려면 매개변수 타입을 정해줘야한다.
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("/saveWithCheckedException")
    public ResponseEntity<User> saveWithCheckedException(@Valid @RequestBody User user) throws IOException{ // form or json 같은 것을 받으려면 매개변수 타입을 정해줘야한다.
        User savedUser = service.saveWithCheckedException(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @PostMapping("/saveWithUncheckedException")
    public ResponseEntity<User> saveWithUncheckedException(@Valid @RequestBody User user) { // form or json 같은 것을 받으려면 매개변수 타입을 정해줘야한다.
        User savedUser = service.saveWithUncheckedException(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }




    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }







}
