### 참조

- [https://devuna.tistory.com/108](https://devuna.tistory.com/108) webflux 정리
- [https://devuna.tistory.com/120](https://devuna.tistory.com/120) mono와 flux 차이
- [https://alwayspr.tistory.com/44](https://alwayspr.tistory.com/44) 예제
- [http://www.devkuma.com/pages/1514](http://www.devkuma.com/pages/1514) 다른예제

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

그래서 Thread max를 50으로 두고 100번 돌렸는데도 문제 없었음!

---