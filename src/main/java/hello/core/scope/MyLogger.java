package hello.core.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requsetUrl;

    public void setRequsetUrl(String requsetUrl) { // url은 Lazy Loading시점에 빈이 생성되면 나중에 넣는다
        this.requsetUrl = requsetUrl;
    }

    public void log(String msg) {
        System.out.println("["+uuid+"]"+"["+requsetUrl+"]"+" msg = " + msg);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
    }

    @PreDestroy
    public void close() {
        System.out.println("["+uuid+"]"+" MyLogger.close " + this);
    }
}
