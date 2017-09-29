package net.husnilkamil.bakenow.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.RecipeAdapter;
import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.fragment.RecipesFragment;
import net.husnilkamil.bakenow.retrofit.RecipeInterface;
import net.husnilkamil.bakenow.utils.DataUtils;
import net.husnilkamil.bakenow.utils.RecipeApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecipeInterface recipeInterface;
    RecipesFragment recipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Log.d(TAG, "connected");
            getRecipesFromServer();
        }


        recipesFragment = new RecipesFragment();
        if(findViewById(R.id.layout_main_sw600dp) != null){
            recipesFragment.setDisplayOnGrid(true);
        }
        recipesFragment.setAdapterClickListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_fragment_container, recipesFragment)
                .commit();

    }

    private void getRecipesFromServer() {

        recipeInterface = RecipeApiUtils.getRecipes();

        recipeInterface.getRecipes().enqueue(new Callback<List<net.husnilkamil.bakenow.retrofit.model.Recipe>>() {

            @Override
            public void onResponse(Call<List<net.husnilkamil.bakenow.retrofit.model.Recipe>> call, Response<List<net.husnilkamil.bakenow.retrofit.model.Recipe>> response) {
                if (response.isSuccessful()) {
                    List<net.husnilkamil.bakenow.retrofit.model.Recipe> data = response.body();
                    Log.d(TAG, "Total data " + data.size());
                    DataUtils.saveToDb(data);
                    recipesFragment.loadRecipesFromDb();
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "Cannot Retrieve data. Error code " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<net.husnilkamil.bakenow.retrofit.model.Recipe>> call, Throwable t) {
                Log.d(TAG, "Failed : " + t.getMessage());
            }

        });
    }

    @Override
    public void onRecipeClick(long recipeId) {

        Intent detailIntent = new Intent(this, StepActivity.class);
        detailIntent.putExtra(Recipe.KEY_RECIPE_ID, recipeId);

        startActivity(detailIntent);

    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
