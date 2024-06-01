package hello.core.service;

import hello.core.config.AppConfig;
import hello.core.repository.SecondRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyServiceImplTest {
    
    // MyService myService = new MyServiceImpl(new SecondRepository()); 
    MyService myService;

    /**
     * 생성자 주입 역할을 넘긴다
     */
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        myService = appConfig.myService();
    }

    @Test
    @DisplayName("테스트 케이스 1")
    public void firstCase() {
        //given

        //when
        String str = myService.makeWord();

        //then
        assertThat(str).isEqualTo("FirstWord");
    }
}