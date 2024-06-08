package hello.core.autowired;

import hello.core.config.AppConfig;
import hello.core.repository.MyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @Test 가 포함된 클래스의 인스턴스를 잠깐 생성한다 -> 힙 메모리
 * static 메서드나 변수는 클래스 로드 시 메서드 영역에 저장된다
 * static 클래스는 외부 클래스와는 별도로 메서드 영역에 존재한다
 * static 클래스는 외부 클래스의 static 멤벼에 접근할 수 있고 외부 클래스가 존재하지 않아도 독립적인 인스턴스로 존재할 수 있다
 */
public class AllBeanTest {

    @Test
    void findAllbean() {
        /**
         * 파라미터로 들어온 AppConfig, MyService를 인스턴스로 생성한다
         * MyService 인스턴스를 생성할 때 AppConfig에서 등록한 Repository를 주입한다
         */
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class, MyService.class);
        MyService bean = ac.getBean(MyService.class);
        System.out.println(bean.makeWord("getFirstRepository"));
    }

    static class MyService {
        private final Map<String, MyRepository> repositoryMap;

         @Autowired
        public MyService(Map<String, MyRepository> repositoryMap) { // Map 타입의 의존성을 주입 받는다
            this.repositoryMap = repositoryMap;
            System.out.println("repositoryMap = " + repositoryMap);
        }

        // 다형성 (자식 빈들이 등록되었을 경우)
        public String makeWord(String disCountCode) {
             MyRepository myRepository = repositoryMap.get(disCountCode);
             return myRepository.makeWord();
        }
    }

}
