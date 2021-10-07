package com.example.restfulwebservice.post;

import com.example.restfulwebservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostDaoService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public List<Post> postList(int id){
        List<Post> postList = userRepository.findById(id).get().getPosts();

        return postList;
    }

    public Post createPost(Post paramPost){
        Post post = postRepository.save(paramPost);

        return post;

    }
}
