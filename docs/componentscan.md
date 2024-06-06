## @ComponentScan
스프링 프레임워크내에서 특정 패키지의 클래스들을 검색하여 컨테이너에 빈으로 등록해준다<br>
`@Component`, `@Configuration`, `@Service`, `@Repository`, `@Controller`을 찾아 빈으로 등록<br>

```java
@Configuration
@ComponentScan(basePackages = "com.example.app")
public class AppConfig {
}
```
`basePackages`속성으로 스캔할 패키지를 지정한다<br>
패키지 내부에서 `@Component`이 붙은 클래스를 자동으로 스프링 컨텍스트에 빈으로 등록한다<br>

- 필터 사용<br>
  컴포넌트 스캔에서 특정 조건을 만족하는 클래스만 포함하거나 제외하려면 필터를 사용할 수 있다
```java
@Configuration
@ComponentScan(
    basePackages = "com.example.app",
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = CustomAnnotation.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.example\\.app\\.exclude\\..*")
)
public class AppConfig {
}
```
`includeFilters`에 추가된 `@CustomAnnotation`어노테이션이 붙은 클래스도 빈으로 등록한다<br>
`excludeFilters`에 추가된 `com.example.app.exclude`패키지는 검색에서 제외한다<br>

범위를 지정하지 않으면 `@ComponentScan`클래스가 속한 패키지를 검색한다<br>
스프링 부트는 프로젝트 패키지 최상단 위치(`@SpringBootApplication)`에서 `@ComponentScan`을 사용한다<br>

`@Component`의 기본적인 빈 등록 전략은 클래스명의 앞글자만 소문자로 바꿔 사용한다<br>
특정 이름으로 직접 등록하고 싶다면 `@Component("customConponentNm")`처럼 지정해주면 된다

[Back to main README](../README.md)