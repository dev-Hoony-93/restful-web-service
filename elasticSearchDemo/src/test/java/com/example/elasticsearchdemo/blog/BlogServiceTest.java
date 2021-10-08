package com.example.elasticsearchdemo.blog;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlogServiceTest {

    @Autowired
    BlogService blogService;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Test
    public void searchTest(){
        //blogService.searchBlog();

    }

    @Test
    public void searchTest2(){

       // blogService.searchBlog()
        //blogService.searchBlog();

    }
}