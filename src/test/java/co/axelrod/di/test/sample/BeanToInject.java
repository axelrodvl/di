package co.axelrod.di.test.sample;

import co.axelrod.di.annotation.Bean;

@Bean
public class BeanToInject {
    private String myDefaultValue;

    public BeanToInject() {
        this.myDefaultValue = "My very default value!";
    }

    public String getMyDefaultValue() {
        return myDefaultValue;
    }
}
