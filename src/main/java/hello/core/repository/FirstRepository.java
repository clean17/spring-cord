package hello.core.repository;

import org.springframework.context.annotation.Primary;

@Primary
public class FirstRepository implements MyRepository{

    @Override
    public void print() {
        System.out.println("FirstRepository");
    }

    @Override
    public String makeWord() {
        return "First";
    }
}
