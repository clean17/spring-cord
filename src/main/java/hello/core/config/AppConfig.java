package hello.core.config;

import hello.core.repository.FirstRepository;
import hello.core.repository.MyRepository;
import hello.core.repository.SecondRepository;
import hello.core.service.MyService;
import hello.core.service.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 관심사의 분리
 * 의존관계 주입의 역할만 한다
 * @return
 */
@Configuration
public class AppConfig {

    /**
     * 빈의 이름은 중복되지 않도록 만든다 = 메서드명
     * @return
     */
    @Bean
    public MyService myService(){
        return new MyServiceImpl(getFirstRepository());
    }

    /**
     * 소스코드(MyServiceImpl)를 수정할 필요없이 다른 기능으로 변경할 수 있게 된다
     * @return
     */
    @Bean
    public MyRepository getFirstRepository() {
//        return new FirstRepository();
         return new SecondRepository();
    }
}
