package co.axelrod.di.test.sample;

import co.axelrod.di.annotation.Bean;
import co.axelrod.di.annotation.Inject;
import co.axelrod.di.annotation.Value;

@Bean(name = "beanToInstantiate")
public class BeanToInstantiate {
    @Inject
    private BeanToInject beanToInject;

    @Inject(name = "fieldToInject")
    public NamedBeanToInject namedBeanToInject;

    @Value("stringProperty")
    private String stringProperty;

    @Value("doubleValue")
    private Double doubleProperty;

    @Value("integerProperty")
    private Integer integerProperty;

    @Value("booleanProperty")
    private Boolean booleanProperty;

    public BeanToInstantiate() {
        System.out.println("I've constructed!");
    }

    public void doWork() {
        System.out.println("Doing my work!");
        System.out.println("Value for injected somethingToInject = " + beanToInject.getMyDefaultValue());
        System.out.println("Value for injected injectWithName = " + namedBeanToInject.getMyDefaultValue());

        System.out.println("String property = " + stringProperty);
        System.out.println("Double property = " + doubleProperty);
        System.out.println("Integer property = " + integerProperty);
        System.out.println("Boolean property = " + booleanProperty);
    }

    public NamedBeanToInject getNamedBeanToInject() {
        return namedBeanToInject;
    }

    public BeanToInject getBeanToInject() {
        return beanToInject;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public Double getDoubleProperty() {
        return doubleProperty;
    }

    public Integer getIntegerProperty() {
        return integerProperty;
    }

    public Boolean getBooleanProperty() {
        return booleanProperty;
    }
}
