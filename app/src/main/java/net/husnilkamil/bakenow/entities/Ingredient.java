package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;

/**
 * Created by husnilk on 9/15/2017.
 */

public class Ingredient extends SugarRecord<Ingredient> {

    long recipeId;
    Double quantity;
    String measure;
    String ingredient;

    public Ingredient() {}

    public Ingredient(long recipeId, Double quantity, String measure, String ingredient) {
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


}
