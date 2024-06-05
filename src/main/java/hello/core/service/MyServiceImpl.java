package hello.core.service;

import hello.core.repository.MyRepository;

public class MyServiceImpl implements MyService {

    /**
     * 의존관계에 대해 아무것도 모른다
     * 주입받은 인스턴스의 메서드만 사용해서 로직만 구현한다
     */
    private final MyRepository myRepository;

    public MyServiceImpl(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    // "ctrl shift T" - 테스트 케이스 단축키
    @Override
    public void print() {
        myRepository.print();
    }

    @Override
    public String makeWord() {
        return myRepository.makeWord() + "Word";
    }
}
