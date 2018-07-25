package co.axelrod.di;

import co.axelrod.di.annotation.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

class ValueProcessor {
    private static final Logger log = LogManager.getLogger(ValueProcessor.class);

    private Context context;

    ValueProcessor(Context context) {
        this.context = context;
    }

    void processValueAnnotation() throws IllegalAccessException {
        for (Object bean : context.beans.values()) {
            for (Field beanField : bean.getClass().getDeclaredFields()) {
                Value value = beanField.getAnnotation(Value.class);
                if(value != null) {
                    injectValueIntoField(bean, beanField, context.properties.get(value.value()));
                }
            }
        }
        log.trace("All values injected");
    }

    private void injectValueIntoField(Object targetBean, Field field, Object valueToInject) throws IllegalAccessException {
        boolean accessible = field.isAccessible();

        field.setAccessible(true);
        field.set(targetBean, valueToInject);
        field.setAccessible(accessible);

        log.trace("Injected value into " + targetBean.getClass().getName());
    }
}
