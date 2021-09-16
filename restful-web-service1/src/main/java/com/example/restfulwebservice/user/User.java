package com.example.restfulwebservice.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String name;
    private Date joinDate;


}
