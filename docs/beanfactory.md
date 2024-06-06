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

[Back to main README](../README.md)