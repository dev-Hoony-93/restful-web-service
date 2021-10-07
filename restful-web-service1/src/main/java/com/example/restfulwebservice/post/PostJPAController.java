package com.example.restfulwebservice.post;

import com.example.restfulwebservice.user.User;
import com.example.restfulwebservice.user.UserDaoService;
import com.example.restfulwebservice.user.UserNotFoundException;
import com.example.restfulwebservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class PostJPAController {

    @Autowired
    PostDaoService postDaoService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){

        return postDaoService.postList(id);
    }


    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) throw new UserNotFoundException(String.format("ID[%s] not found",id));

        post.setUser(user.get());

        Post savedPost = postDaoService.createPost(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
