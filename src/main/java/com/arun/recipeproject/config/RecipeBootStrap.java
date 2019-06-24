package com.arun.recipeproject.config;

import com.arun.recipeproject.model.*;
import com.arun.recipeproject.repositories.CategoryRepository;
import com.arun.recipeproject.repositories.RecipeRepository;
import com.arun.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public RecipeBootStrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        recipeRepository.saveAll(getRecipes());
    }

    public List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> each = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!each.isPresent()) {
            throw new RuntimeException("Expected UOM");
        }

        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tablespoon.isPresent()) {
            throw new RuntimeException("Expected UOM");
        }

        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");
        if (!cup.isPresent()) {
            throw new RuntimeException("Expected UOM");
        }


        Set<UnitOfMeasure> uom = new HashSet<>();
        uom.add(each.get());
        uom.add(tablespoon.get());
        uom.add(cup.get());

        Optional<Category> american = categoryRepository.findByDescription("American");
        Optional<Category> italian = categoryRepository.findByDescription("Italian");
        Optional<Category> mexican = categoryRepository.findByDescription("Mexican");
        Optional<Category> fast_food = categoryRepository.findByDescription("Fast Food");

        Set<Category> categories = new HashSet<>();
        categories.add(american.get());
        categories.add(italian.get());
        categories.add(mexican.get());
        categories.add(fast_food.get());

        Recipe recipe = new Recipe();
        recipe.setDescription("This is an American dish");
        recipe.setCategories(categories);
        recipe.setCookTime(10);
        recipe.setDifficulty(Difficulty.HARD);
        recipe.setDirections("East");

        Notes notes = new Notes();
        notes.setRecipeNotes("This is Recipe number 1");
        notes.setRecipe(recipe);

        recipe.setNotes(notes);

        Ingredient.IngredientBuilder builder = Ingredient.builder();
        Ingredient ingredient1 = builder.amount(new BigDecimal("100")).description("Ingredient1").recipe(recipe).build();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient1);
        recipe.setIngredients(ingredients);

        recipe.setSource("source");
        recipe.setUrl("https://www.arun.com");

        recipes.add(recipe);

        return recipes;


    }


}
