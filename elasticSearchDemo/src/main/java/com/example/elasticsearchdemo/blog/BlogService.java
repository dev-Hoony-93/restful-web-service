package com.example.elasticsearchdemo.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    BlogEsRepository blogEsRepository;

    public Blog insertBlog(Blog blog){
        Blog savedBlog =  blogEsRepository.save(blog);
        return savedBlog;
    }

    public Blog getBlog(String id){
        Optional<Blog> blog =  blogEsRepository.findById(id);
        return blog.get();
    }

    public Iterable<Blog> getAllBlog(){
        Iterable<Blog> blogs =  blogEsRepository.findAll();
        return blogs;
    }


    public Iterable<Blog> searchBlog(String title){
        Iterable<Blog> blogs =  blogEsRepository.findByTitle(title, new QPageRequest(0,1));
        return blogs;
    }
}
