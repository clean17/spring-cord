package hello.core.beanfilnd;

import hello.core.config.AppConfig;
import hello.core.service.MyService;
import hello.core.singleton.SingletonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = "+beanDefinitionName + " Object = "+bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            /**
             * ROLE_APPLICATION    = 0 개발자가 등록한 빈
             * ROLE_INFRASTRUCTURE = 2 스프링 내부에서 사용하는 빈
             */
            if (beanDefinition.getRole() == beanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = "+beanDefinitionName + " Object = "+bean);
            }
        }
    }

    @Test
    @DisplayName("빈 출력")
    void findBeanByName() {
        MyService myService = ac.getBean(MyService.class);
        assertThat(myService).isInstanceOf(MyService.class);
    }

    @Test
    @DisplayName("없는 빈 조회")
    void findNoBean() {
        // 특정한 예외가 발생하는지 테스트
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("abc", MyService.class));
    }

    @Test
    @DisplayName("같은 타입의 모든 빈 출력")
    void findAllBeanTypes() {
        Map<String, MyService> beansOfType = ac.getBeansOfType(MyService.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = "+key + " value = "+ beansOfType);
        }
    }

    @Test
    @DisplayName("싱글톤 비교")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);
    }
}
