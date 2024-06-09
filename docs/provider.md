## ObjectProvider
싱글톤 빈에서 프로토타입 빈을 사용할 때, 프로토타입 빈의 새로운 인스턴스를 생성하는 방법 1<br>
싱글톤 빈이 매번 새로운 프로토타입 빈을 요청한다<br>

스프링 프레임워크에서 제공하는 유틸리티<br>
지연 로딩과 프로토타입 빈의 생성에 유용하다<br>

- 프로토타입 빈
```java
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    
    public PrototypeBean() {
        System.out.println("PrototypeBean created");
    }

    public void doSomething() {
        System.out.println("PrototypeBean doing something");
    }
}
```
- 싱글톤 빈
```java
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {

    private final ObjectProvider<PrototypeBean> prototypeBeanProvider;

    public SingletonBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
        this.prototypeBeanProvider = prototypeBeanProvider;
    }

    public void usePrototypeBean() {
        PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
        prototypeBean.doSomething();
    }
}
```
- 설정 클래스
```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```
- 실행 예제
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        SingletonBean singletonBean = context.getBean(SingletonBean.class);
        singletonBean.usePrototypeBean();
        singletonBean.usePrototypeBean();  // Each call creates a new PrototypeBean
        
        context.close();
    }
}
```

## JSR-330 Provider
싱글톤 빈에서 프로토타입 빈을 사용할 때, 프로토타입 빈의 새로운 인스턴스를 생성하는 방법 2<br>
싱글톤 빈이 매번 새로운 프로토타입 빈을 요청한다<br>

JSR-330에서 제공하는 Provider 인터페이스를 사용하여 프로토타입 빈을 주입받는다<br>
스프링 라이브러리가 아니므로 의존성 추가가 필요하다<br>
```gradle
    implementation 'javax.inject:javax.inject:1'
```

(프로토타입 빈 정의와 설정 클래스는 위와 동일)
- 싱글톤 빈
```java
import javax.inject.Provider;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {

    private final Provider<PrototypeBean> prototypeBeanProvider;

    public SingletonBean(Provider<PrototypeBean> prototypeBeanProvider) {
        this.prototypeBeanProvider = prototypeBeanProvider;
    }

    public void usePrototypeBean() {
        PrototypeBean prototypeBean = prototypeBeanProvider.get();
        prototypeBean.doSomething();
    }
}
```
- 실행 예제
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        SingletonBean singletonBean = context.getBean(SingletonBean.class);
        singletonBean.usePrototypeBean();
        singletonBean.usePrototypeBean();  // Each call creates a new PrototypeBean
        
        context.close();
    }
}
```

[Back to main README](../README.md)