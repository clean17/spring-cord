## 애플리케이션 개발 원칙
> SOLID 원칙을 지킨다<br>
하나의 책임만<br>
추상화에 의존<br>
확장에만 열려있도록<br>

# 스프링 프레임워크

### 스프링 코어
스프링의 핵심 기능 제공, IoC(Inversion of Control)와 DI(Dependency Injection)를 구현<br>
이를 통해 객체 간의 의존성을 효율적으로 관리

### 스프링 AOP (Spring Aspect-Oriented Programming)
관점 지향 프로그래밍을 지원, 로깅, 보안, 트랜잭션 관리 등 횡단 관심사를 모듈화

###  스프링 데이터
데이터 접근 계층을 단순화, 다양한 데이터베이스에 대한 리포지토리 추상화를 제공

### 스프링 MVC (Spring Web MVC)
웹 애플리케이션 개발을 지원하는 MVC(Model-View-Controller) 프레임워크를 제공

### 스프링 부트
스프링 기반 애플리케이션의 설정을 간소화, 빠른 개발 지원, 기본 설정 + 내장 톰캣

### 스프링 시큐리티
보안 프레임워크를 제공, 인증과 권한부여 처리

### 스프링 클라우드
마이크로서비스 아키텍처를 지원하는 다양한 도구와 라이브러리를 제공<br>
서비스 디스커버리, 분산 트랜잭션, 구성 관리 등 클라우드 네이티브 애플리케이션 개발에 필요한 기능을 지원

### 스프링 배치
배치 프레임워크를 제공, 대용량 데이터 처리, 트랜잭션 관리, 로깅 및 재시작 기능 등을 지원

### 스프링 REST
RESTful 웹 서비스를 쉽게 구현할 수 있도록 지원<br>
스프링 MVC와 함께 사용하여 REST API를 효율적으로 개발

### 스프링 AMQP
AMQP(Advanced Message Queuing Protocol) 기반의 메시징 애플리케이션을 개발할 수 있도록 지원<br>
주로 RabbitMQ와 함께 사용

# ApplicationContext
애플리케이션의 설정 정보를 제공한다<br>
스프링 애플리케이션에서 빈(Bean)을 생성하고 관리하는 핵심 인터페이스<br>
ApplicationContext는 스프링 IoC(Inversion of Control) 컨테이너의 고급 형태이다

Context는 특정 작업이나 환경에 대한 설정 정보, 상태, 리소스 등을 관리하는 객체를 의미한다

> 주요기능

- 빈 팩토리 (Bean Factory) - 
빈은 스프링 컨테이너에 의해 생성되고 관리된다
- 애플리케이션 이벤트를 리스너로 처리
- 메세지 소스
- 환경 변수 - 
외부 설정 파일이나 시스템 환경 변수에 접근해 애플리케이션의 환경 설정 정보를 제공한다
- 리소스 로딩  - 파일 시스템, 클래스패스, 리소스

> 구현 클래스
### GenericXmlApplicationContext
일반적인 XML 기반 애플리케이션 컨텍스트
```java
GenericXmlApplicationContext context = new GenericXmlApplicationContext();
context.load("classpath:beans.xml");
context.load("file:/path/to/another-beans.xml");  // 파일 시스템에서 XML 파일 로드
context.refresh();  // 컨텍스트 초기화
```
### AnnotationConfigApplicationContext
자바 애노테이션을 사용하여 구성 클래스를 통해 컨텍스트를 로드
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```
### WebApplicationContext
웹 애플리케이션에서 사용되며, 서블릿 컨텍스트와 통합, 웹 MVC에서 주로 사용
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl(); // 구현 클래스는 별도로 구성
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService = context.getBean(MyService.class);
        myService.perform(); // 구현 클래스의 메서드 호출
    }
}

```

## BeanFactory
Spring IoC(Inversion of Control) 컨테이너의 루트 인터페이스<br>
`BeanFactory`는 Spring의 모든 빈을 관리하고, 빈의 생성, 구성, 관리 및 생명주기 지원을 담당<br>

>주요 기능
- 빈 관리
- 지연 로딩 - 기본 Lazy Loding
- IoC 지원 - 의존성 주입으로 빈 간의 의존성 관리
- 다양한 스코프 지원 - 싱글톤, 프로토타입 등

> BeanFactory 구현체<br>
> 
일반적으로 `ApplicationContext`를 사용한다<br>
`BeanFactory`에서 여러기능을 추가한 것으로 차이점은 모든 싱글톤 빈을 즉시 주입한다

## 서블릿 컨테이너(Servlet Container)
서블릿을 실행하고 관리하는 자바 엔터프라이즈 환경의 구성 요소 (Apache Tomcat, Jetty, WildFly, GlassFish)
>주요 기능
> 
- 라이플 사이플 관리 (초기화-서비스-소멸) -
컨테이너는 서블릿 클래스의 인스턴스를 생성하고, 초기화 메서드를 호출하여 서블릿을 초기화
- 요청 및 응답 처리 - 요청 > 서블릿 > 응답, HttpServletRequest, HttpServletResponse 생성 및 관리
- 웹 애플리케이션 배포 - WAR(Web Application Archive) 파일로 패키징되어 배포
- 서블릿 컨텍스트 관리 - 서블릿 컨테이너는 ServletContext 객체를 생성하고 관리, 애플리케이션 내의 서블릿들은 ServletContext를 통해 서로 데이터를 공유
- 세션 관리 - HttpSession로 클라이언트의 상태를 저장
- 보안 관리
> 동작 방식

1. 배포될 때 서블릿 컨테이너는 web.xml 파일이나 애노테이션을 통해 서블릿을 구성하고 초기화<br>
2. HTTP 요청이 들어오면 서블릿 컨테이너는 적절한 서블릿 선택<br>
3. 요청 객체(HttpServletRequest)와 응답 객체(HttpServletResponse)를 생성하고, 서블릿의 service() 메서드를 호출<br>
4. 서블릿이 필요하지 않으면 destroy() 메서드 호출

```java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Hello, World!</h1>");
    }
}
```
## BeanDefinition 
IoC컨테이너가 관리하는 객체를 정의하는 메타데이터<br>
스프링은 xml파일, 자바 설정 클래스, 자바 어노테이션으로 `BeanDefinition`을 정의한다<br>
스프링 컨테이너는 `BeanDefinition`만 보고 빈 생성, 설정, 생명주기를 관리한다<br>
스프링은 `BeanDefinition`인터페이스를 구현하여 다양한 빈 객체를 관리한다

스프링의 `ApplicationContext`인터페이스는 멤벼 변수로 `BeanDefinitionReader`인터페이스를 가지고 있다.<br>
이 인터페이스를 통해 xml, 어노테이션, 클래스등에서 빈 정의를 읽고 `BeanDefinition`인터페이스를 정의한다

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

## ComponentScan
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

### @Autowired
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