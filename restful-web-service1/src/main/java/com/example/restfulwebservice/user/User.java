package com.example.restfulwebservice.user;


import com.example.restfulwebservice.post.Post;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    @Id @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    private String name;

    @ApiModelProperty(notes = "사용자의 등록일을 입력해주세요.")
    private Date joinDate;

    @ApiModelProperty(notes = "사용자 패스워드")
    private String password;
    @ApiModelProperty(notes = "사용자 주민번호")
    private String ssn;

    @OneToMany(mappedBy = "user") // user테이블과 연결.
    private List<Post> posts;
}
