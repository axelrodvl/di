package co.axelrod.di.test.circular;

import co.axelrod.di.annotation.Bean;

@Bean
public class ClassC {
    public ClassC(ClassA classA) {

    }
}
