
maven의 pom.xml에

디펜던시
```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.10.2</version>
</dependency>
```
를 추가하고

dto에

```java
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String name;
    private Date joinDate;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String ssd;
}
```
이런식으로 JsonIgnore로 가능함.

```java
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password","ssd"})
public class User {
    private Integer id;

    private String name;
    private Date joinDate;

    private String password;
    private String ssd;
}
```

이런 방법도 있다.

---
아예 없애는건 저렇게 하고
필터링을 해보도록 하겠다.
