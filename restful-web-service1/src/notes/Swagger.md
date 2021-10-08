# Swagger

전체 API 목록을 조회하기 위한 거

pom.xml에 이거 추가
```xml
<dependency>

    <groupId>io.springfox</groupId>

    <artifactId>springfox-boot-starter</artifactId>

    <version>3.0.0</version>

</dependency>
```
- 추가만 해도 /swagger-ui/index.html에 확인할 수 있다.
- 이것은 /v2/api-docs 기반으로 만들어지는 UI임.

## Swagger 구현

- 이것을 원하는대로 구현이 가능하다.

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact("hoony" ,
            "https://github.com/dev-Hoony-93","hoon7566@naver.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Title",
            "My User management RETS API Service" , "1.0" , "urn:tos",
            DEFAULT_CONTACT,"Apache 2.0","http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json" , "application/xml"));


    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

}
```
- 이렇게 Config를 만들면 contact정보와 api에 대한정보, Produce, 그리고 Consume 에 대한 정보들을 담을 수 있다.

- 다음 단계로 dto에 `@APiModel`을 붙여보겠다.

```java
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    @Id @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    private String name;

    @ApiModelProperty(notes = "사용자의 등록일을 입력해주세요.")
    private Date joinDate;

    @ApiModelProperty(notes = "사용자 패스워드")
    private String password;
    @ApiModelProperty(notes = "사용자 주민번호")
    private String ssn;

    @OneToMany(mappedBy = "user") // user테이블과 연결.
    private List<Post> posts;
}
```
- 이렇게 구현하면 설명을 덧붙일 수 있다.