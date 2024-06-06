package hello.core.scan;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 자동 스캔한 빈 이름이 중복되면 스프링에서 익셉션을 발생시킨다
 * 수동으로 등록한 빈과 스캔이름이 동일하다면 수동으로 등록한 빈이 우선권을 가진다 (수동이 오버라이딩함)
 * 스프링 부트는 기본적으로 오버라이딩 하지 않는다 (익셉션 발생)
 * 스프링 부트에서 오버라이딩 하고 싶다면 application.properties 에 아래 설정 추가
 * spring.main.allow-bean-definition-overriding=true // 기본값은 false
 */
public @interface MyComponent {
}
