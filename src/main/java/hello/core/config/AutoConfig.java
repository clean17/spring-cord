package hello.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
/**
 * @Configuration이 등록된 클래스를 컴포넌트 스캔에서 제외한다
 */
public class AutoConfig {
}
