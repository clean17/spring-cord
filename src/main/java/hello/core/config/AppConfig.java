package hello.core.config;

import hello.core.repository.FirstRepository;
import hello.core.service.MyService;
import hello.core.service.MyServiceImpl;

public class AppConfig {

    /**
     * 관심사의 분리
     * 의존관계 주입의 역할만 한다
     * @return
     */
    public MyService myService(){
        return new MyServiceImpl(getFirstRepository());
    }

    /**
     * 소스코드(MyServiceImpl)를 수정할 필요없이 다른 기능으로 변경할 수 있게 된다
     * @return
     */
    private FirstRepository getFirstRepository() {
        return new FirstRepository();
        // return new SecondRepository();
    }
}
