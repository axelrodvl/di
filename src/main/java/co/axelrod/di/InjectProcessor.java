package co.axelrod.di;

import co.axelrod.di.annotation.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

class InjectProcessor {
    private static final Logger log = LogManager.getLogger(InjectProcessor.class);

    private Context context;

    InjectProcessor(Context context) {
        this.context = context;
    }

    void processInjectAnnotation() throws IllegalAccessException {
        for (Object bean : context.beans.values()) {
            for (Field beanField : bean.getClass().getDeclaredFields()) {
                Inject inject = beanField.getAnnotation(Inject.class);
                if(inject != null) {
                    if(inject.name().isEmpty()) {
                        injectBeanIntoField(bean, beanField, context.getInstance(beanField.getType()));
                    } else {
                        injectBeanIntoField(bean, beanField, context.getInstance(inject.name()));
                    }
                }
            }
        }
        log.trace("All fields injected");
    }

    private void injectBeanIntoField(Object targetBean, Field field, Object beanToInject) throws IllegalAccessException {
        boolean accessible = field.isAccessible();

        field.setAccessible(true);
        field.set(targetBean, beanToInject);
        field.setAccessible(accessible);

        log.trace("Injected bean " + beanToInject.getClass().getName() + " into " + targetBean.getClass().getName());
    }
}
