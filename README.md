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

### ClassPathXmlApplicationContext
클래스패스에 있는 XML 파일에서 컨텍스트를 로드
```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```
### FileSystemXmlApplicationContext
파일 시스템 경로에 있는 XML 파일에서 컨텍스트를 로드
```java
ApplicationContext context = new FileSystemXmlApplicationContext("C:/path/to/applicationContext.xml");
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
        return new MyServiceImpl(); // 구현 클래스는 별도고 구성
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
