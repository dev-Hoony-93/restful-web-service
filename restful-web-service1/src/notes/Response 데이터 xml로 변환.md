## Response 데이터를 xml로 변환

요청하는 request Header에

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

`Accept` 를 `application/xml` 로 요청한다.