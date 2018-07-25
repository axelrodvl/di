package co.axelrod.di;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static co.axelrod.di.ClassLoaderUtils.getClasses;

public class Context {
    private static final Logger log = LogManager.getLogger(Context.class);

    final List<Class> classes = new ArrayList<>();
    final Map<String, Object> beans = new HashMap<>();
    final Map<String, Object> properties = new HashMap<>();

    public Context(List<String> packages) throws Exception {
        long beginningTime = System.currentTimeMillis();

        properties.putAll(PropertyParser.getProperties());

        for (String packageName : packages) {
            classes.addAll(getClasses(packageName));
        }

        new ConfigurationProcessor(this).processConfigurationAnnotation();
        new BeanProcessor(this).processBeanAnnotation();
        new InjectProcessor(this).processInjectAnnotation();
        new ValueProcessor(this).processValueAnnotation();

        log.info("Context created in {} ms", System.currentTimeMillis() - beginningTime);
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T getInstance(String name, Class<T> type) {
        return (T) beans.get(name);
    }

    public Object getInstance(String name) {
        return beans.get(name);
    }

    Object getInstance(Class clazz) {
        for (Object object : beans.values()) {
            if (object.getClass().equals(clazz)) {
                return object;
            }
        }
        return null;
    }

    String createBeanName(Class clazz, String name) {
        if (name.isEmpty()) {
            name = clazz.getName();
            log.trace("Bean for class {} has no name, generating", clazz.getName());
        }
        if (beans.containsKey(name)) {
            name += "_" + UUID.randomUUID();
            log.warn("Found instantiated bean with same name, changing name to {}", name);
        }
        return name;
    }
}
