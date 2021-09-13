#Spring validation

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>


<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
</dependency>
```
디팬던시 추가후

`dto`에

---

```java
package com.example.restfulwebservice.user;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
private Integer id;

    @Size(min = 2)
    private String name;
    @Past
    private Date joinDate;
}
```


이런식으로 추가한다.

`Controller`에는 
```java
 @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){ // form or json 같은 것을 받으려면 매개변수 타입을 정해줘야한다.
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

```
이렇게 `@Valid` 어노테이션을 붙인다.


valid에 걸려서 badRequest여도 아무 body메시지가 없으니

`CustomizedResponseEntityExceptionHandler`에 해당부분을 오버라이딩 하여 Valid시 exception을 재정의 
```java
 @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getMessage(),ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    


```
