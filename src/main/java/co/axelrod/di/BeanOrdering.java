package co.axelrod.di;

import co.axelrod.di.exception.CircularDependencyException;
import co.axelrod.di.exception.NoBeanToInjectException;
import co.axelrod.di.model.BeanRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BeanOrdering {
    private List<BeanRecipe> orderedRecipes = new ArrayList<>();

    private Map<Class, Vertex> rawRecipes = new HashMap<>();

    private class Vertex {
        BeanRecipe recipe;

        Vertex(BeanRecipe recipe) {
            this.recipe = recipe;
        }

        Boolean visited = false;
        Boolean processed = false;

        void markVisited() {
            visited = true;
            processed = false;
        }

        void markProcessed() {
            visited = false;
            processed = true;
        }
    }

    BeanOrdering(List<BeanRecipe> unorderedRecipes) {
        for(BeanRecipe recipe : unorderedRecipes) {
            rawRecipes.put(recipe.clazz, new Vertex(recipe));
        }
    }

    List<BeanRecipe> getOrderedBeanRecipes() {
        for(Class clazz = getNotProcessedRecipe(); clazz != null; clazz = getNotProcessedRecipe()) {
            dfs(clazz);
        }
        return orderedRecipes;
    }

    private Class getNotProcessedRecipe() {
        for(Vertex recipe : rawRecipes.values()) {
            if(!recipe.processed) {
                return recipe.recipe.clazz;
            }
        }
        return null;
    }

    private void dfs(Class clazz) {
        Vertex vertex = rawRecipes.get(clazz);

        if (vertex == null) {
            throw new NoBeanToInjectException("Required bean " + clazz.getName() + " seems not be annotated with @Bean");
        }

        if (vertex.visited && !vertex.processed) {
            throw new CircularDependencyException("Circular dependency found, cannot create context");
        }

        vertex.markVisited();

        for (Class toVisit : vertex.recipe.constructorArguments) {
            dfs(toVisit);
        }

        orderedRecipes.add(rawRecipes.get(clazz).recipe);

        vertex.markProcessed();
    }
}
