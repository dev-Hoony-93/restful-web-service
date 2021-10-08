package com.example.elasticsearchdemo.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class BlogRestController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blog")
    public Iterable<Blog> findBlogAll(){
        return blogService.getAllBlog();
    }

    @GetMapping("/blog/{id}")
    public Blog findBlogById(@PathVariable String id){
        return blogService.getBlog(id);
    }
    @PostMapping("/blog")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog){

        Blog savedBlog = blogService.insertBlog(blog);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBlog.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/blog/search/{title}")
    public Iterable<Blog> findBlogAll(@PathVariable String title){
        return blogService.searchBlog(title);
    }



}
