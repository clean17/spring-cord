## Bean Lifecycle callback
빈의 생성, 초기화, 소멸 과정에서 특정 동작을 수행하기 위해 제공되는 메서드<br>
이러한 콜백을 통해 빈이 컨테이너에 의해 관리되는 동안 특정 작업을 자동으로 수행할 수 있다<br>
### 빈 생명주기 단계
1. 빈 생성: 스프링 컨테이너는 빈의 인스턴스를 생성
2. 의존성 주입: 빈의 프로퍼티 및 의존성을 설정
3. 초기화 콜백: 빈이 초기화될 때 특정 메서드를 호출
4. 빈 사용: 빈이 실제 애플리케이션에서 사용
5. 소멸 콜백: 애플리케이션 컨텍스트가 종료되거나 빈이 더 이상 필요하지 않을 때 특정 메서드를 호출
### 초기화 및 소멸 콜백 설정 방법
1. `InitializingBean`, `DisposableBean` 인터페이스 구현
2. `@PostConstruct`, `@PreDestroy` 애노테이션 사용
3. `init-method`, `destroy-method` 속성 사용

###  InitializingBean, DisposableBean 인터페이스 구현
스프링의 전용 인터페이스를 구현하는 방법<br>
`InitializingBean` 인터페이스의 `afterPropertiesSet()` 메서드,<br>
`DisposableBean` 인터페이스의 `destroy()` 메서드를 구현
```java
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        // 초기화 작업 수행, 스프링 컨테이너에 등록되기 직전 호출
        System.out.println("MyBean is initialized");
    }

    @Override
    public void destroy() throws Exception {
        // 소멸 작업 수행
        System.out.println("MyBean is destroyed");
    }
}
```
스프링에 의존적이므로 거의 사용되지 않는 방법이다
###  @PostConstruct, @PreDestroy 애노테이션 사용
표준 자바 애노테이션을 사용하여 초기화, 소멸 콜백을 처리하는 방법<br>
`@PostConstruct` 애노테이션을 초기화 메서드에, `@PreDestroy` 애노테이션을 소멸 메서드에 사용<br>
가장 많이 사용하고 권장되는 방법<br>
외부 라이브러리에 설정할 수 없다는 단점이 있다
```java
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @PostConstruct
    public void init() {
        // 초기화 작업 수행
        System.out.println("MyBean is initialized");
    }

    @PreDestroy
    public void destroy() {
        // 소멸 작업 수행
        System.out.println("MyBean is destroyed");
    }
}
```
###  init-method, destroy-method 속성 사용
XML 설정 파일이나 자바 설정 파일에서 빈 정의 시 `init-method`와 `destroy-method` 속성을 사용<br>
초기화, 종료 메서드 이름을 자유롭게 지정할 수 있다<br>
스프링 빈이 스프링 코드에 의존하지 않는다<br>
외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다는 장점이 있다<br>
```xml
<bean id="myBean" class="com.example.MyBean" init-method="init" destroy-method="destroy" />
```
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyBean myBean() {
        return new MyBean();
    }
}
```
```java
public class MyBean {

    public void init() {
        // 초기화 작업 수행
        System.out.println("MyBean is initialized");
    }

    public void destroy() {
        // 소멸 작업 수행
        System.out.println("MyBean is destroyed");
    }
}
```
대부분의 라이브러리는 종료 메서드의 이름으로 `close`, `shutdown`등을 사용한다<br>
`@Bean`의 `destroyMethod`속성은 내부적으로 `"(inferred)"`로 등록되어 있다<br>
이는 추론 기능으로 개발자가 특별히 종료 메서드를 지정하지 않아도 라이브러리의 종료 메서드를 추론하여 호출한다<br>
추론 기능을 사용하지 않으려면 `destroyMethod=""`로 공백을 지정해준다<br>

[Back to main README](../README.md)