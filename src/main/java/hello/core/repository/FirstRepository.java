package hello.core.repository;

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
