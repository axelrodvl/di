package co.axelrod.di.test.sample;

public class NoBean {
    public NoBean() {
        throw new RuntimeException("Should not be instantiated");
    }
}
