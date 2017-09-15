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
}
