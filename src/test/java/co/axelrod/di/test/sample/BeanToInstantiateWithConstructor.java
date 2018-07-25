package co.axelrod.di.test.sample;

import co.axelrod.di.annotation.Bean;

@Bean(name = "beanToInstantiateWithConstructor")
public class BeanToInstantiateWithConstructor {
    private BeanToInject beanToInject;
    private NamedBeanToInject namedBeanToInject;

    public BeanToInstantiateWithConstructor(BeanToInject beanToInject, NamedBeanToInject namedBeanToInject) {
        this.beanToInject = beanToInject;
        this.namedBeanToInject = namedBeanToInject;
        System.out.println("I've constructed with injection!");
    }

    public void doWork() {
        System.out.println("Doing my work!");
        System.out.println("Value for injected somethingToInject = " + beanToInject.getMyDefaultValue());
        System.out.println("Value for injected injectWithName = " + namedBeanToInject.getMyDefaultValue());
    }

    public NamedBeanToInject getNamedBeanToInject() {
        return namedBeanToInject;
    }

    public BeanToInject getBeanToInject() {
        return beanToInject;
    }
}
