package hello.core;

import hello.core.config.AppConfig;
import hello.core.merber.Grade;
import hello.core.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		System.out.println(Grade.BASIC.getLevel()); // 1
		System.out.println(Grade.VIP.getLevel());   // 2

		// 개발자가 직접 DI 제어
//		AppConfig appConfig = new AppConfig();
//		MyService myService = appConfig.myService();

		/**
		 * 스프링 컨테이너(ApplicationContext)에 빈을 등록하고(@Configuration, @Bean)
		 * 재사용(getBean메서드)
		 * 제어의 역전
		 */
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MyService myService = applicationContext.getBean("myService", MyService.class); // 메서드명, 리턴타입 (메서드명 생략 가능)
		myService.print();
		System.out.println(myService.makeWord());
	}

}
