### 참조

- [https://devuna.tistory.com/108](https://devuna.tistory.com/108) webflux 정리
- [https://devuna.tistory.com/120](https://devuna.tistory.com/120) mono와 flux 차이
- [https://alwayspr.tistory.com/44](https://alwayspr.tistory.com/44) 예제
- [http://www.devkuma.com/pages/1514](http://www.devkuma.com/pages/1514) 다른예제
- [https://wedul.site/569](https://wedul.site/569) webflux 다른설명
- [https://javacan.tistory.com/entry/spring-webflux-server-sent-event-1](https://javacan.tistory.com/entry/spring-webflux-server-sent-event-1) SSE구현예제

### Webflux란?

Spring WebFlux는 Spring 5에서 새롭게 추가된 모듈입니다.

WebFlux는 클라이언트, 서버에서 reactive 스타일의 어플리케이션 개발을 도와주는 모듈이며,

reactive-stack web framework이며 non-blocking에 reactive stream을 지원합니다.

장점 : 고성능, spring 과 완벽한 통합, netty 지원, 비동기 non-blocking 메세지 처리

단점 : 오류처리가 다소 복잡하다. Back Pressure 기능 없음

- Back Pressure ? 배압기능. 과도한 요청들어올시 `OutOfMemoryError` 일어날수 있음

### 테스트 코드

pom.xml에는

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
```

해당 디펜던시를 넣어줘야함.

---

## Webflux의 WebClient를 이용한 테스트.

3초 이상이 걸리는 api가 있다고 했을때

테스트코드를 작성해보았습니다.

```java
@Test
public voidtestBlock()throwsException{
    StopWatch stopWatch =newStopWatch();
    stopWatch.start();
		for(inti=0; i<3 ; i++){
			mockMvc.perform(MockMvcRequestBuilders.get("/3second"));
    }
    stopWatch.stop();
    System.out.println("blocking: "+stopWatch.getTotalTimeSeconds());
}
```

3초가 걸리는 api를 3번 호출했으니 9초 이상이 걸립니다.

blocking: 9.3592333

```java
@Test
public voidtestFlux()throwsException{
    CountDownLatch count =newCountDownLatch(1000);

    StopWatch stopWatch =newStopWatch();
    stopWatch.start();
		for(inti=0; i<100 ; i++){
		webClient.get().uri("http://localhost:8080/3second").retrieve()
                .bodyToFlux(User.class)
                .subscribe(
                        it -> count.countDown()
                );
    }
    count.await(10,TimeUnit.SECONDS);
    stopWatch.stop();
    System.out.println("nonBlocking: "+stopWatch.getTotalTimeSeconds());
}
```

WebFlux에서 제공하는 `WebClient`를 사용해서 위와 동일하게 3초가 걸리는 API를 호출하였다. for문 안의 변수인 `LOOP_COUNT`는 100으로 코드상에서 설정되어있다. 3초 걸리는 API를 100번 호출한다 하더라도 3.xx초 밖에 걸리지 않는다. 더 나아가서 `LOOP_COUNT`를 100으로 변경하더라도 필자의 컴퓨터에서는 4.xx초 밖에 걸리지 않는다. Blocking I/O와 비교해봤을 때 정말 효율적이라고 볼 수 있다.

만약, Blocking을 위처럼 많은 요청을 동시에 처리하려면 그 만큼의 Thread이 생성되어야 한다. 그러나 이렇게 처리한다 해도 Context Swiching에 의한 오버헤드가 존재할 것이다.

#### → 이유

Spring 5가 도입 되면서 클라이언트에 요청에 별도의 thread를 생성하지 않고 buffer를 사용해서 요청을 받고 뒤에서 처리하는 처리하는 thread는 여러개를 두어서 처리한다. 결국 node.js의 싱글스레드 논블로킹을 따라가는 것 같다.

그래서 Thread max를 50으로 두고 100번 돌렸는데도 문제 없었음!

---

## Webflux를 이용한 SSE구현

### SSE?

Server Sent Event로써 서버에서 클라이언트로 보내고 싶을때 사용한다. 이걸 Webflux를 이용해서 쉽게 구현이 가능하다.

`Controller`

```java
@GetMapping("/flux/{id}")
publicFlux<ServerSentEvent<User>> flux(@PathVariableintid)throwsException{
		return Flux.interval(Duration.ofSeconds(1))
            .map(i->service.findOne(id))
            .map(user -> ServerSentEvent.builder(user).build());
}
```

`service.findOne(id)`는 간단하게 id에 해당하는 `UserDto`를 리턴한다.

컨트롤러에 이렇게 구현하고.

curl -i [localhost:8088/flux/1](http://localhost:8088/flux/1) 요청을 날려보면

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/40c73037-7931-4fab-8d46-8bee732a6675/Untitled.png)

Content-Type이 text/event-stream 이고 1초마다 계속 데이터가 출력되는 것을 알수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3bcc6b9f-0391-46fb-8051-d11cc3098272/Untitled.png)

웹프라우저로 요청해도 해당화면처럼 중지버튼을 누르기 전까지 계속해서 데이터가 추가된다.