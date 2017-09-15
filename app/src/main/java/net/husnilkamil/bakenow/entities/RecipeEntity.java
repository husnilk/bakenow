package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;

/**
 * Created by husnilk on 9/15/2017.
 */

public class RecipeEntity extends SugarRecord<RecipeEntity> {
    Integer id;
    String name;
    Integer servings;
    String image;

    IngredientEntity ingredients;
    StepEntity steps;

    public RecipeEntity() {}

    public RecipeEntity(Integer id, String name, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }
}
