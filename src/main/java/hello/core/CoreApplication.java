package hello.core;

import hello.core.config.AppConfig;
import hello.core.merber.Grade;
import hello.core.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		System.out.println(Grade.BASIC.getLevel()); // 1
		System.out.println(Grade.VIP.getLevel());   // 2

		AppConfig appConfig = new AppConfig();
		MyService myService = appConfig.myService();
		myService.print();
		System.out.println(myService.makeWord());
	}

}
