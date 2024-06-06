## @Autowired Option

### required = false
해당 의존성이 없더라도 예외를 발생시키지 않고 그냥 넘어간다<br>
해당 필드나 메서드는 주입이 안된채로 남는다

```java
@Component
public class MyService {

    @Autowired(required = false)
    private OptionalDependency optionalDependency;

    // ...
}
```
```java
@Component
public class MyService {

    private OptionalDependency optionalDependency;

    @Autowired(required = false)
    public void setOptionalDependency(OptionalDependency optionalDependency) {
        this.optionalDependency = optionalDependency;
    }

    // ...
}
```
의존성이 없으면 멤버변수는 `null`인 상태로 남는다<br>
세터 매서드는 호출자체가 되지 않는다 (내부 코드 실행 X)<br>
사용 시 `null`체크를 통해 안전하게 사용할 수 있다
### Optional
자바 8의 `Optional`을 사용하여 의존성이 주입되지 않을때 안전하게 처리할 수 있다<br>
의존성이 없을 때 `Optional.empty()`가 주입된다
인수가 `null`이더라도 내부 코드는 실행된다<br>
```java
import java.util.Optional;

@Component
public class MyService {

    private final Optional<OptionalDependency> optionalDependency;

    @Autowired
    public MyService(Optional<OptionalDependency> optionalDependency) {
        this.optionalDependency = optionalDependency;
    }

    public void doSomething() {
        optionalDependency.ifPresentOrElse(
                OptionalDependency::performAction, // optionalDependency가 존재할 때 호출할 메서드
                () -> System.out.println("OptionalDependency is not available") // optionalDependency가 존재하지 않을 때 실행할 코드
        );
    }
}
```
`ifPresentOrElse`를 사용하여 존재할 때와 존재하지 않을 때 실행할 코드를 지정할 수 있다

### @Nullable
의존성이 주입되지 않더라도 `null`을 허용한다<br>
인수가 `null`이 더라도 내부 코드는 실행된다<br> 
```java
import org.springframework.lang.Nullable;

@Component
public class MyService {

    private final OptionalDependency optionalDependency;

    @Autowired
    public MyService(@Nullable OptionalDependency optionalDependency) {
        this.optionalDependency = optionalDependency;
    }

    // ...
}
```

[Back to main README](../README.md)