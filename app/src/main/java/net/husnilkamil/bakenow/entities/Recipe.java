package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by husnilk on 9/15/2017.
 */

public class Recipe extends SugarRecord<Recipe> {

    @Ignore
    public static final String KEY_RECIPE_ID = "key_recipe_id";

    long id;
    int recipeId;
    String name;
    Integer servings;
    String image;

    Ingredient ingredients;
    Step steps;

    public Recipe() {}

    public Recipe(int recipeId, String name, Integer servings, String image) {
        this.recipeId = recipeId;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId){
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
