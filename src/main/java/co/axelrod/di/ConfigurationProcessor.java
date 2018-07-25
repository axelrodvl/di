package co.axelrod.di;

import co.axelrod.di.annotation.Bean;
import co.axelrod.di.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ConfigurationProcessor {
    private static final Logger log = LogManager.getLogger(ConfigurationProcessor.class);

    private Context context;

    ConfigurationProcessor(Context context) {
        this.context = context;
    }

    void processConfigurationAnnotation() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        for (Class clazzToProcess : context.classes) {
            if (clazzToProcess.isAnnotationPresent(Configuration.class)) {
                Object configuration = clazzToProcess.getConstructor().newInstance();
                for (Method method : clazzToProcess.getMethods()) {
                    Bean bean = method.getAnnotation(Bean.class);
                    if(bean != null) {
                        Object createdBean = method.invoke(configuration, (Object[]) null);
                        String beanName = context.createBeanName(method.getReturnType(), bean.name());
                        context.beans.put(beanName, createdBean);
                        log.trace("Instantiated object for class {} with name = {}", method.getReturnType(), beanName);
                    }
                }
            }
        }
    }
}
