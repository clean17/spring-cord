## @Autowired
`@Component`가 붙은 클래스의 기존 생성자가 다른 인스턴스를 필요로 할 때 `@Autowired`애너테이션을 사용하여 의존성을 주입할 수 있다(DI)
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    private final MyService myService;

    @Autowired
    public MyComponent(MyService myService) {
        this.myService = myService;
    }

    public void doWork() {
        myService.performService();
    }
}
```
기본적인 빈 주입 전략은 `@Autowired`가 적용된 멤버변수의 타입을 컨테이너에서 검색해 주입한다

## 의존성 주입 방법
- 생성자 주입
```java
@Service
public class MyService {

    private final MyRepository myRepository;

    @Autowired
    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
}
```
불변성 보장<br>
의존성이 변하지 않으므로 테스트 용이<br>
순환 참조 알림 (BeanCurrentlyInCreationException)<br>

- 필드 주입
```java
@Service
public class MyService {

    @Autowired
    private MyRepository myRepository;
}
```
간결성<br>
불변성 부족<br>
순환 참조 문제<br>
- 세터 주입
```java
@Service
public class MyService {

    private MyRepository myRepository;

    @Autowired
    public void setMyRepository(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
}
```
불변성 미보장<br>
순환 참조 완화 (빈을 먼저 생성하고 의존성을 나중에 주입)<br>

의존성이 불변하지 않다면 런타임중 어떤 동작이 나올지 보장할 수 없다<br>
멀티스레드가 동시에 접근할 경우 예상할 수 없는 결과가 나올 수 있다<br>
불변하지 않을 경우 테스트 중에도 변경할 수 있으므로 결과 예측이 힘들다<br>

### @RequiredArgsConstructor<br>
Lombok의 `@RequiredArgsConstructor`를 사용하면 생성자 주입을 동일하게 만들어준다<br>
`final`필드와 `@NonNull`필드에 대해 생성자를 자동으로 생성한다<br>
```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final MyRepository myRepository;
    
}
```
```java
@Service
public class MyService {
    private final MyRepository myRepository;

    @Autowired
    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
    
}
```

[Back to main README](../README.md)