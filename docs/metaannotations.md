## Meta-Annotations
애너테이션의 적용 범위, 유지 정책, 문서화 여부 등을 지정
### @Target
애너테이션이 적용될 수 있는 자바 요소
- TYPE: 클래스, 인터페이스(애너테이션 포함), 열거형에 적용
- FIELD: 필드(멤버 변수)에 적용
- METHOD: 메서드에 적용
- PARAMETER: 메서드 파라미터에 적용
- CONSTRUCTOR: 생성자에 적용
- LOCAL_VARIABLE: 로컬 변수에 적용
- ANNOTATION_TYPE: 다른 애너테이션에 적용
- PACKAGE: 패키지에 적용
```java
@Target(ElementType.METHOD)
```
### @Retention
애너테이션의 유지 정책(RetentionPolicy)을 지정
- SOURCE: 소스 코드에만 존재하고 컴파일 시 제거
- CLASS: 컴파일된 클래스 파일에 존재하지만 런타임에는 사용 불가능
- RUNTIME: 런타임 시에도 JVM에 의해 참조 가능, 리플렉션으로 접근
```java
@Retention(RetentionPolicy.RUNTIME)
```
### @Documented
해당 애너테이션이 Javadoc에 포함되도록 지정
```java
@Documented
```
### @Inherited
애너테이션이 자식 클래스에 상속되도록 지정 (extends)

[Back to main README](../README.md)