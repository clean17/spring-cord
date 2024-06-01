package hello.core.repository;

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
