
JDBC - Hibernate - JPA - Spring Data JPA

###JPA

- Java Persistent API
- 자바 ORM 기술에 대한 API 표준 명세
- 자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스
- EntityManager를 통해 CRUD 처리

###Hibernate

- JPA의 구현체, 인터페이스를 직접 구현한 라이브러리
- 생산성, 유지보수, 비종속성

###Spring Data JPA

- Spring Module
- JPA를 추상화한 Repository 인터페이스 제공



---

# 설정하기

application.yml에

```yml
  jpa:
      show-sql: true
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:testdb

```

넣어준다.

pom.xml에는
h2-database와 spring-data-jpa의존성을 추가한다.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

----

### Entity

DTO에 @Entity 를 붙여주고,
```java
package com.example.restfulwebservice.user;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

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
}

```

미리 초기화 할 세팅할 data.sql을 작성

```sql
insert into user values (1,sysdate(), 'User1', 'test1111','707070-1111111')
insert into user values (2,sysdate(), 'User2', 'test2222','807070-2222222')
insert into user values (3,sysdate(), 'User3', 'test3333','909999-1111111')

```