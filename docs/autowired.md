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
하나의 생성자에서 모든 멤버변수를 DI 해준다

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

DI 프레임워크가 없으면 아무것도 할 수 없다<br>
외부 테스트에 사용하기 위해 변경을 할 수 없다 -> 결국 세터 주입을 사용하게 된다<br>
`@SpringBootTest`안에서 `@Autowired`로 가져와야 테스트 할 수 있다<br>
테스트 할 때마다 스프링 컨테이너에 빈을 적재하므로 시간이 다소 소모된다

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
한번에 하나의 멤벼변수만 DI 해준다

의존성이 불변하지 않다면 런타임중 어떤 동작이 나올지 보장할 수 없다<br>
멀티스레드가 동시에 접근할 경우 예상할 수 없는 결과가 나올 수 있다<br>
불변하지 않을 경우 테스트 중에도 변경할 수 있으므로 결과 예측이 힘들다<br>

- 세터 주입은 빈 객체가 생성된 이후 호출된다<br>
아래코드를 살펴보자
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    // 생성자 주입
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 세터 주입
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
애플리케이션이 실행되면 가장 먼저 컴포넌트 스캔 실행 -> 컴포넌트 빈 등록 -> 생성자 호출을 한다<br>
`OrderServiceImpl`의 멤버 변수 `memberRepository`, `discountPolicy`가 먼저 주입된다<br>
빈이 컨테이너에 등록된 이후 세터 메서드가 `@Autowired`에 의해서 호출된다(세터 주입)<br>
이때 `memberRepository`, `discountPolicy`가 다시 한번 더 주입된다

### @RequiredArgsConstructor<br>
Lombok의 `@RequiredArgsConstructor`를 사용하면 생성자 주입을 동일하게 만들어준다<br>
`final`필드와 `@NonNull`필드에 대해 생성자를 자동으로 생성한다<br>
결국 코드를 `final` + `@Autowired`생성자를 생성한다
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
### 생성자 주입을 권장하는 이유
1. 불변성 보장
2. 명확한 의존성
3. 테스트 시 의존성이 없으면 컴파일 오류를 발생 시킨다(세터 주입은 런타임 오류)
4. 의존성을 주입받는 방법이 명확하므로 단위 테스트 시 쉽게 의존성을 주입할 수 있다<br>
   (세터 주입은 객체 생성 시 필수 의존성 없이 생성할 수 있으므로 오류날 가능성)
```java
public class OrderServiceImplTest {

    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;
    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        memberRepository = new InMemoryMemberRepository(); // 테스트용 구현체
        discountPolicy = new FixedDiscountPolicy(); // 테스트용 구현체
        orderService = new OrderServiceImpl();
        orderService.setMemberRepository(memberRepository);
        orderService.setDiscountPolicy(discountPolicy);
    }

    @Test
    public void testProcessOrder() {
        // 테스트 코드
    }
}

```
[Back to main README](../README.md)