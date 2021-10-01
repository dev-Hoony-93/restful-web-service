`pom.xml`에
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
추가.

`Bean` 생성.


```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("hoony")
                .password("{noop}test1234") // 인코딩없이 사용할 수 있도록, 어떠한 동작도 하지않고 바로 사용할수록 noOperation
                .roles("USER");
    }
}
```
실제 서비스할때는 db에서 읽어온다던지 하면된다는듯.
