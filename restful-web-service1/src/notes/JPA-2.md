## JPA -2

### 1 : N 관계 구현 
- JPA에서 1:N 구현하려면 무엇이 메인이 되는가를 잘 설정해야함.


### Post Entity 설계

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @GeneratedValue @Id
    private Integer id;

    private String description;

    // User : Post -> 1 : (0~N) , Main : Sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
```
```java
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
```
user는 이렇게 수정했다.

---
### post 추가 구현하기

```java
@PostMapping("/users/{id}/posts")
public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){

    Optional<User> user = userRepository.findById(id);

    if(!user.isPresent()) throw new UserNotFoundException(String.format("ID[%s] not found",id));

    post.setUser(user.get());

    Post savedPost = postDaoService.createPost(post);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPost.getId())
            .toUri();

    return ResponseEntity.created(location).build();

}
```

이런식으로 구현했다.