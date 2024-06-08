package hello.core.repository;

import hello.core.annotation.MyPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;

@MyPolicy
public class SecondRepository implements MyRepository{

    @Override
    public void print() {
        System.out.println("SecondRepository");
    }

    @Override
    public String makeWord() {
        return "Second";
    }
}
