package com.example.restfulwebservice.user;


import com.example.restfulwebservice.post.Post;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue
    private Integer id;

    private String name;
    private Date joinDate;

    private String password;
    private String ssn;

    @OneToMany(mappedBy = "user") // user테이블과 연결.
    private List<Post> posts;
}
