## @Configuration
`@Configuration`를 사용하면 해당 클래스가 하나 이상의 `@Bean`메서드를 정의하고 스프링 IoC컨테이너가 이들을 관리한다<br>
이때 `@Bean`메서드는 싱글톤 빈을 생성한다<br>
`@Bean`이 적용된 메서드를 여러번 호출하더라도 항상 동일한 싱글톤 인스턴스를 반환하게 된다

### 싱글톤 인스턴스를 반환하는 이유
스프링은 `@Configuration`클래스를 프록시로 감싼다<br>
이 프록시는 `@Bean`메서드를 호출할 때마다 이미 생성된 인스턴스를 반환하게 된다<br>
이 프록시가 실제 메서드 요청을 가로채어 한번이라도 이 메서드가 호출되었다면 스프링 컨테이너의 빈을 반환한다<br>

### 프록시 동작 과정
1. 스프링 컨테이너는 `@Configuration`이 적용된 클래스를 프록시로 감싸 서브클래스를 생성하고 `@Bean`메서드를 오버라이딩 한다<br>
2. `@Bean`메서드가 호출되면 프록시는 스프링 컨테이너에서 이 메서드가 한번 호출되어 생성된 빈이 있는지 검사한다<br>
3. 호출한적이 없다면 빈을 생성해 컨테이너에 저장하고 호출한 적이 있다면 컨테이너의 빈을 반환한다<br>

이렇게 프록시는 캐싱을 구현하여 싱글톤 특징을 유지해준다

예를들어 아래와 같은 코드가 있을 때
```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        System.out.println("Creating MyService instance");
        return new MyServiceImpl();
    }
}
```
빈은 최초 한번만 생성되므로 `myService()`메서드를 코드 전반적으로 여러번 호출하더라도  `Creating MyService instance`문구는 단 한번만 출력된다<br>

아래 코드는 프록시가 `@Configuration`의 서브 클래스를 만들어 `@Bean`메서드를 오버라이딩 하여 항상 싱글톤을 반환하는 과정을 설명해준다
```java
public class AppConfig$$EnhancerBySpringCGLIB extends AppConfig {
    private MyService myService;

    @Override
    public MyService myService() {
        if (this.myService == null) {
            this.myService = super.myService();
        }
        return this.myService;
    }
}
```
### EnhancerBySpringCGLIB
스프링 프레임워크가 빈을 프록시할 때 사용하는 CGLIB 라이브러리의 Enhancer 클래스를 통해 생성된 동적 프록시 클래스<br>
CGLIB는 코드 생성 라이브러리(Code Generation Library)로, 런타임에 클래스의 바이트코드를 조작하여 동적 프록시를 생성한다<br>
스프링은 이 프록시를 사용해서 싱글톤을 보장하게 된다<br>
CGLIB는 클래스 기반으로 프록시를 생성하기 때문에 `final`로 선언된 코드는 프록시로 생성할 수 없다<br>

[Back to main README](../README.md)