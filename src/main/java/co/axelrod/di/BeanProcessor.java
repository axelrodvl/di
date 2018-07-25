package co.axelrod.di;

import co.axelrod.di.annotation.Bean;
import co.axelrod.di.model.BeanRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

class BeanProcessor {
    private static final Logger log = LogManager.getLogger(BeanProcessor.class);

    private Context context;

    BeanProcessor(Context context) {
        this.context = context;
    }

    private List<BeanRecipe> recipes = new ArrayList<>();

    void processBeanAnnotation() throws Exception {
        createRecipes();
        orderRecipes();
        processRecipes();
        log.trace("All beans instantiated");
    }

    private void createRecipes() {
        for (Class clazz : context.classes) {
            Bean bean = (Bean) clazz.getAnnotation(Bean.class);
            if(bean != null) {
                BeanRecipe beanRecipe = new BeanRecipe(clazz, context.createBeanName(clazz, bean.name()));
                if (!onlyDefaultConstructor(clazz)) {
                    beanRecipe.constructorArguments = Arrays.asList(clazz.getConstructors()[0].getParameterTypes());
                }
                recipes.add(beanRecipe);
            }
        }
    }

    private boolean onlyDefaultConstructor(Class clazz) {
        return clazz.getConstructors().length == 1 && clazz.getConstructors()[0].getParameterCount() == 0;
    }

    private void orderRecipes() {
        recipes = new BeanOrdering(recipes).getOrderedBeanRecipes();
    }

    private void processRecipes() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object objectFromRecipe;
        for (BeanRecipe beanRecipe : recipes) {
            if (onlyDefaultConstructor(beanRecipe.clazz)) {
                objectFromRecipe = instantiateBeanWithDefaultConstructor(beanRecipe);
            } else {
                objectFromRecipe = instantiateBeanWithNonDefaultConstructor(beanRecipe);
            }
            context.beans.put(beanRecipe.name, objectFromRecipe);
            log.trace("Instantiated object for class {} with name = {}", beanRecipe.clazz.getName(), beanRecipe.name);
        }
    }

    @SuppressWarnings("unchecked")
    private Object instantiateBeanWithDefaultConstructor(BeanRecipe recipe)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return recipe.clazz.getConstructor().newInstance();
    }

    private Object instantiateBeanWithNonDefaultConstructor(BeanRecipe beanRecipe)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object[] arguments = new Object[beanRecipe.constructorArguments.size()];

        int i = 0;
        for(Class clazz : beanRecipe.constructorArguments) {
            arguments[i++] = context.getInstance(clazz);
        }

        return beanRecipe.clazz.getConstructors()[0].newInstance(arguments);
    }
}
