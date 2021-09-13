#HTTP Status Code 제어

- .fromCurrentRequest 현재 요청받은 리퀘스트 그대로 쓴다는뜻
- .path url변경
- .path("/{id}") 이렇게 되어있을때 ..buildAndExpand(savedUser.getId())로 가변변수값 넣을수잇음


@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user){ // form or json 같은 것을 받으려면 매개변수 타입을 정해줘야한다.
User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //
                .buildAndExpand(savedUser.getId())
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

ResponseEntity.created로 상태값이 200으로 된다.
