package co.axelrod.di.test;

import co.axelrod.di.exception.CircularDependencyException;
import co.axelrod.di.Context;
import co.axelrod.di.exception.NoBeanToInjectException;
import co.axelrod.di.test.sample.*;
import co.axelrod.di.test.sample.configuration.BeanToInstantiateFromConfiguration;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

public class ContextTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void unnamedBean() throws Exception {
        Context context = new Context(Collections.singletonList("co.axelrod.di.test.sample"));

        Assert.assertNotNull(context.getInstance(BeanToInject.class.getName()));
    }

    @Test
    public void defaultConstructor() throws Exception {
        Context context = new Context(Collections.singletonList("co.axelrod.di.test.sample"));

        Assert.assertNotNull(context.getInstance("fieldToInject"));

        BeanToInstantiate beanToInstantiate = context.getInstance("beanToInstantiate", BeanToInstantiate.class);
        Assert.assertNotNull(beanToInstantiate);
        Assert.assertNotNull(beanToInstantiate.getNamedBeanToInject());
        Assert.assertNotNull(beanToInstantiate.getBeanToInject());
        beanToInstantiate.doWork();
    }

    @Test
    public void constructorWithArguments() throws Exception {
        Context context = new Context(Collections.singletonList("co.axelrod.di.test.sample"));

        BeanToInstantiateWithConstructor beanToInstantiateWithConstructor
                = context.getInstance("beanToInstantiateWithConstructor", BeanToInstantiateWithConstructor.class);
        Assert.assertNotNull(beanToInstantiateWithConstructor);
        Assert.assertNotNull(beanToInstantiateWithConstructor.getNamedBeanToInject());
        Assert.assertNotNull(beanToInstantiateWithConstructor.getBeanToInject());
        beanToInstantiateWithConstructor.doWork();
    }

    @Test
    public void configuration() throws Exception {
        Context context = new Context(Collections.singletonList("co.axelrod.di.test.sample"));

        BeanToInstantiateFromConfiguration beanToInstantiateFromConfiguration =
                context.getInstance(BeanToInstantiateFromConfiguration.class.getName(), BeanToInstantiateFromConfiguration.class);
        Assert.assertNotNull(beanToInstantiateFromConfiguration);
        Assert.assertEquals("Configuration value", beanToInstantiateFromConfiguration.getValue());
        beanToInstantiateFromConfiguration.doWork();

        BeanToInstantiateFromConfiguration beanToInstantiateFromConfigurationWithName =
                context.getInstance("beanToInstantiateFromConfigurationWithName", BeanToInstantiateFromConfiguration.class);
        Assert.assertNotNull(beanToInstantiateFromConfigurationWithName);
        Assert.assertEquals("Configuration value with name", beanToInstantiateFromConfigurationWithName.getValue());
        beanToInstantiateFromConfigurationWithName.doWork();
    }

    @Test
    public void values() throws Exception {
        Context context = new Context(Collections.singletonList("co.axelrod.di.test.sample"));

        BeanToInstantiate beanToInstantiate = context.getInstance("beanToInstantiate", BeanToInstantiate.class);
        Assert.assertEquals(Integer.valueOf(123), beanToInstantiate.getIntegerProperty());
        Assert.assertEquals(Double.valueOf(123.123d), beanToInstantiate.getDoubleProperty());
        Assert.assertEquals(Boolean.TRUE, beanToInstantiate.getBooleanProperty());
        Assert.assertEquals("String", beanToInstantiate.getStringProperty());
    }

    @Test
    public void noAnnotation() throws Exception {
        Context context = new Context(Collections.singletonList("co.axelrod.di.test.sample"));

        Assert.assertNull(context.getInstance(NoBean.class.getName()));
    }

    @Test
    public void missingBeanToInject() throws Exception {
        expectedException.expect(NoBeanToInjectException.class);
        new Context(Collections.singletonList("co.axelrod.di.test.missing"));
    }

    @Test
    public void circularDependencies() throws Exception {
        expectedException.expect(CircularDependencyException.class);
        new Context(Collections.singletonList("co.axelrod.di.test.circular"));
    }
}
