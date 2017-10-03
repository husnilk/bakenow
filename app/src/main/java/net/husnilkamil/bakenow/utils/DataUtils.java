package net.husnilkamil.bakenow.utils;

import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.entities.Recipe;

import java.util.List;

/**
 * Created by husnilk on 9/18/2017.
 */

public class DataUtils {
    public static void saveToDb(List<net.husnilkamil.bakenow.retrofit.model.Recipe> data){

        Step.deleteAll(Step.class);
        Ingredient.deleteAll(Ingredient.class);
        net.husnilkamil.bakenow.entities.Recipe.deleteAll(net.husnilkamil.bakenow.entities.Recipe.class);

        for (net.husnilkamil.bakenow.retrofit.model.Recipe recipe : data) {
            Recipe recipeEntity = new Recipe(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipe.getImage());
            recipeEntity.save();

            int recipeId = recipeEntity.getRecipeId();

            for (net.husnilkamil.bakenow.retrofit.model.Ingredient ingredient : recipe.getIngredients()) {
                Ingredient ingredientEntity = new Ingredient(
                        recipeId,
                        ingredient.getQuantity(),
                        ingredient.getMeasure(),
                        ingredient.getIngredient()
                );
                ingredientEntity.save();
            }

            for (net.husnilkamil.bakenow.retrofit.model.Step step : recipe.getSteps()) {
                Step stepEntity = new Step(
                        recipeId,
                        step.getShortDescription(),
                        step.getDescription(),
                        step.getVideoURL(),
                        step.getThumbnailURL()
                );
                stepEntity.save();
            }
        }

    }
}
