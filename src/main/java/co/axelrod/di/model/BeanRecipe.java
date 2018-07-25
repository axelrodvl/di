package co.axelrod.di.model;

import java.util.ArrayList;
import java.util.List;

public class BeanRecipe {
    public Class clazz;
    public String name;

    public BeanRecipe(Class clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public List<Class> constructorArguments = new ArrayList<>();
}
