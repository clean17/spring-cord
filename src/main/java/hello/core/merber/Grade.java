package hello.core.merber;

public enum Grade {
    BASIC(1),
    VIP(2);

    private final int level;

    Grade(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}
