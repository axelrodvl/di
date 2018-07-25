package co.axelrod.di.test.sample.configuration;

import co.axelrod.di.annotation.Bean;
import co.axelrod.di.annotation.Configuration;

@Configuration
public class MyConfiguration {
    @Bean
    public BeanToInstantiateFromConfiguration beanToInstantiateFromConfiguration() {
        BeanToInstantiateFromConfiguration bean = new BeanToInstantiateFromConfiguration();
        bean.setValue("Configuration value");
        return bean;
    }

    @Bean(name = "beanToInstantiateFromConfigurationWithName")
    public BeanToInstantiateFromConfiguration beanToInstantiateFromConfigurationWithName() {
        BeanToInstantiateFromConfiguration bean = new BeanToInstantiateFromConfiguration();
        bean.setValue("Configuration value with name");
        return bean;
    }
}
