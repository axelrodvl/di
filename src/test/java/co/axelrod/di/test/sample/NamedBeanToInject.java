package co.axelrod.di.test.sample;

import co.axelrod.di.annotation.Bean;

@Bean(name = "fieldToInject")
public class NamedBeanToInject {
    private String myDefaultValue;

    public NamedBeanToInject() {
        this.myDefaultValue = "My very default value! Injected with name!";
    }

    public String getMyDefaultValue() {
        return myDefaultValue;
    }
}
