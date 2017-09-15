package net.husnilkamil.bakenow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.retrofit.model.Recipe;

import java.util.List;

public class StepActivity extends AppCompatActivity {

    private static final String TAG = StepActivity.class.getSimpleName();

    long recipeId;
    List<Step> steps;
    List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent recipeDetailIntent = getIntent();
        if(recipeDetailIntent != null){
            recipeId = recipeDetailIntent.getLongExtra(Recipe.KEY_RECIPE_ID, 0);
            Log.d(TAG, "Recipe ID : " + String.valueOf(recipeId));
            steps = Step.find(Step.class, "recipe_id=?", String.valueOf(recipeId));
            ingredients = Ingredient.find(Ingredient.class, "recipe_id=?", String.valueOf(recipeId));

            Log.d(TAG, "Steps Count : " + steps.size());
            Log.d(TAG, "Ingredients counts : " + ingredients.size());
        }


    }
}
