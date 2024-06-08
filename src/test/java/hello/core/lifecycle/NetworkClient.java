package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String dbUrl;

    public NetworkClient() {
        System.out.println("new 생성자 = " + dbUrl);
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void connect() {
        System.out.println("dbUrl = " + dbUrl);
    }

    public void call(String msg) {
        System.out.println("call = " + dbUrl + " msg " + msg);
    }

    public void disconnect() {
        System.out.println("close = " + dbUrl);
    }

    @PostConstruct
    public void init() {
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        disconnect();
    }
}
