## @Quilifier, @Primary
> 조회된 빈이 2개 이상일 때 해결방법

### @Autowird 필드명 매칭
멤버 변수의 필드 명을 실제 빈의 이름으로 변경한다
```java
// 기존 방법
@Autowired
private MyRepository myRepository;

// 필드명으로 자식 빈을 직접 매칭
@Autowird
private MyRepository secondRepository;
```

요약: `@Autowird`는 타입으로 매칭한다, 빈이 2개라면 빈 이름으로 매칭한다<br>

### @Quilifier
`@Autowired` 필드명 매칭의 방법보다 안전한 방법은 `@Quilifier`를 사용<br>
빈의 이름을 특정한 빈 이름으로 변경할 수 있고<br>
주입할때도 기존 빈의 이름 또는 지정한 빈의 이름으로 주입할 수 있다<br>
가장 먼저 `@Quilifier`로 지정된 빈을 찾고 없으면 컨테이너에서 빈 이름으로 찾는다<br>
```java
// @Autowired 필드 명 매칭만 사용할 경우
@Component
public class MyServiceConsumer {

    private final Service serviceImpl1;
    private final Service serviceImpl2;
    private final Service serviceImpl3;

    @Autowired
    public MyServiceConsumer(
            Service serviceImpl1,
            Service serviceImpl2,
            Service serviceImpl3) {
        this.serviceImpl1 = serviceImpl1;
        this.serviceImpl2 = serviceImpl2;
        this.serviceImpl3 = serviceImpl3;
    }

    public void performServices() {
        serviceImpl1.perform();
        serviceImpl2.perform();
        serviceImpl3.perform();
    }
}
```
```java
// @Quilifier 를 사용할 경우
@Component
public class MyServiceConsumer {

    private final Service serviceImpl1;
    private final Service serviceImpl2;
    private final Service serviceImpl3;

    @Autowired
    public MyServiceConsumer(
            @Qualifier("serviceImpl1") Service serviceImpl1,
            @Qualifier("serviceImpl2") Service serviceImpl2,
            @Qualifier("serviceImpl3") Service serviceImpl3) {
        this.serviceImpl1 = serviceImpl1;
        this.serviceImpl2 = serviceImpl2;
        this.serviceImpl3 = serviceImpl3;
    }

    public void performServices() {
        serviceImpl1.perform();
        serviceImpl2.perform();
        serviceImpl3.perform();
    }
}
```
요약: `@Autowired` 필드 명 매칭은 실수할 수 있으므로 `@Qualifier`를 사용하여 명확하고 가독성 있게 주입한다

### @Primary
`@Autowired`사용 시 여러 빈이 매칭될 경우 우선적으로 매칭될 빈을 지정할 때 사용된다<br>
우선순위는 `@Quilifier`로 자세하게 지정한 경우가 `@Primary`보다 높다
```java
@Service
@Primary
public class ServiceImpl1 implements Service {
    @Override
    public void perform() {
        System.out.println("ServiceImpl1 performing");
    }
}
```

### 어노테이션으로 컴파일 오류 체크
`@Quilifier`를 자주 사용할 때 이름을 틀려도 컴파일 오류가 발생하지 않는다<br>
이 때는 어노테이션을 만들어서 실수를 방지 할 수 있다<br>
```java
// 기존 코드
@Quilifier("secondRepository")
public class SecondRepository implements MyRepository{

    // ...
}
```
이름 철자를 틀릴 수 도 있으므로 어노테이션으로 방지한다<br>
`@Qualifier`어노테이션 정의 부분을 가져온 뒤 `@Qualifier`라인만 추가한다
```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("myPolicy")
public @interface MyPolicy {
}
```
```java
@MyPolicy
public class SecondRepository implements MyRepository{

    // ...
}
```
이제 해당 빈을 사용할 경우 커스텀 어노테이션을 사용해야 한다<br>
어노테이션 이름을 틀리면 컴파일 오류가 발생하고 개발자가 직접 빈 이름을 매번 입력할 필요가 없다

[Back to main README](../README.md)