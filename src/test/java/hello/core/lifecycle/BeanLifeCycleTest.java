package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void lifeCycleTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient(); // 객체 인스턴스만 생성, 아직 컨테이너에 등록하지 않음
            networkClient.setDbUrl("http://hello-db.dev");
            return networkClient; // 여기서 빈 컨테이너에 삽입, 내부의 초기화 콜백 메서드가 있으면 실행 후 삽입
        }
    }
}
