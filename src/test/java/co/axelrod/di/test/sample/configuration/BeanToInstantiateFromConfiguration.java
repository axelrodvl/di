package co.axelrod.di.test.sample.configuration;

public class BeanToInstantiateFromConfiguration {
    private String value;

    public BeanToInstantiateFromConfiguration() {
        System.out.println("I've constructed from Configuration!");
    }

    public void doWork() {
        System.out.println("Doing my work!");
        System.out.println("Value for value = " + value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
