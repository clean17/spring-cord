package hello.core.singleton;

/**
 * 싱글톤 문제
 * 코드추가됨
 * 클라이언트가 구체 클래스 의존
 * 상속이 힘들어 유연하지 않음
 */
public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {}
}
