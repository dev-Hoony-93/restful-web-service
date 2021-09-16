package com.example.restfulwebservice.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8080"})
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    WebClient webClient = WebClient.create();

    @BeforeTestClass
    public void setup(){
        System.setProperty("reactor.netty.ioWorkerCount", "1");
    }

    @Test
    public void testBlock() throws  Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i=0; i<3 ; i++){
            mockMvc.perform(MockMvcRequestBuilders.get("/3second"));
        }
        stopWatch.stop();
        System.out.println("blocking: "+stopWatch.getTotalTimeSeconds());
    }

    @Test
    public void testFlux() throws Exception{
        CountDownLatch count = new CountDownLatch(1000);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i=0; i<100 ; i++){
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

}