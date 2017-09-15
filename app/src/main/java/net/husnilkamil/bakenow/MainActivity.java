package net.husnilkamil.bakenow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.husnilkamil.bakenow.adapter.RecipeAdapter;
import net.husnilkamil.bakenow.entities.IngredientEntity;
import net.husnilkamil.bakenow.entities.RecipeEntity;
import net.husnilkamil.bakenow.entities.StepEntity;
import net.husnilkamil.bakenow.fragment.RecipesFragment;
import net.husnilkamil.bakenow.model.Ingredient;
import net.husnilkamil.bakenow.model.Step;
import net.husnilkamil.bakenow.retrofit.RecipeInterface;
import net.husnilkamil.bakenow.model.Recipe;
import net.husnilkamil.bakenow.utils.RecipeApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    RecipeInterface recipeInterface;
    RecipesFragment recipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeInterface = RecipeApiUtils.getRecipes();

        loadRecipes();

        recipesFragment = new RecipesFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_fragment_container, recipesFragment)
                .commit();
    }

    private void loadRecipes() {

        recipeInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    List<Recipe> data = response.body();
                    Log.d(TAG, "Total data " + data.size());
                    recipesFragment.setRecipeData(data);
                    saveToDb(data);
                }else{
                    int statusCode = response.code();
                    Log.d(TAG, "Cannot Retrieve data. Error code " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }

        });
    }

    private void saveToDb(List<Recipe> data){

        StepEntity.deleteAll(StepEntity.class);
        IngredientEntity.deleteAll(IngredientEntity.class);
        RecipeEntity.deleteAll(RecipeEntity.class);

        for (Recipe recipe : data) {
            RecipeEntity recipeEntity = new RecipeEntity(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getServings(),
                    recipe.getImage());
            recipeEntity.save();

            Long recipeId = recipeEntity.getId();

            for (Ingredient ingredient : recipe.getIngredients()) {
                IngredientEntity ingredientEntity = new IngredientEntity(
                        recipeId,
                        ingredient.getQuantity(),
                        ingredient.getMeasure(),
                        ingredient.getIngredient()
                );
                ingredientEntity.save();
            }

            for (Step step : recipe.getSteps()) {
                StepEntity stepEntity = new StepEntity(
                        step.getId(),
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

    @Override
    public void onRecipeClick(int recipeId) {

        Intent detailIntent = new Intent(this, StepActivity.class);
        detailIntent.putExtra(Recipe.KEY_RECIPE_ID, recipeId);

        startActivity(detailIntent);

    }
}
