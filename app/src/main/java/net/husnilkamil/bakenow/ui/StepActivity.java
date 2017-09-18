package net.husnilkamil.bakenow.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.StepAdapter;
import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.fragment.RecipeStepsFragment;

import java.util.List;

public class StepActivity extends AppCompatActivity implements StepAdapter.OnStepClickListener{

    private static final String TAG = StepActivity.class.getSimpleName();

    RecipeStepsFragment recipeStepsFragment;
    long recipeId;
    Recipe recipe;
    List<Step> steps;
    List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setAdapterClickListener(this);
        Intent recipeDetailIntent = getIntent();
        if(recipeDetailIntent != null) {
            recipeId = recipeDetailIntent.getLongExtra(Recipe.KEY_RECIPE_ID, 0);
            Recipe recipe = Recipe.findById(Recipe.class, recipeId);
            getSupportActionBar().setTitle(recipe.getName());
            recipeStepsFragment.setRecipeId(recipeId);
            Log.d(TAG, "Recipe ID : " + String.valueOf(recipeId));
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_fragment_container, recipeStepsFragment)
                .commit();

    }

    @Override
    public void onStepClick(long id) {

        Intent stepDetailIntent = new Intent(this, StepDetailActivity.class);
        stepDetailIntent.putExtra(Recipe.KEY_RECIPE_ID, recipeId);
        stepDetailIntent.putExtra(Step.KEY_STEP_ID, id);

        startActivity(stepDetailIntent);
    }
}
