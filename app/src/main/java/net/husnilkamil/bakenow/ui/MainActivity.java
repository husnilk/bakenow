package net.husnilkamil.bakenow.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.RecipeAdapter;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.idlingres.SimpleIdlingResource;
import net.husnilkamil.bakenow.retrofit.RecipeInterface;
import net.husnilkamil.bakenow.utils.DataUtils;
import net.husnilkamil.bakenow.utils.RecipeApiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_recipes)
    RecyclerView mRecipeRecyclerView;

    RecipeInterface recipeInterface;
    RecipeAdapter mRecipeAdapter;
    RecipeAdapter.OnRecipeClickListener mRecipeOnClickListener;
    boolean dataLoaded = false;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        getIdlingResource();

        if (isConnected && !dataLoaded) {
            Log.d(TAG, "connected");
            getRecipesFromServer();
            dataLoaded = true;
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "not connected");
        }

        mRecipeRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager;
        if(getResources().getBoolean(R.bool.isTablet)){
            layoutManager = new GridLayoutManager(this, 2);
        }else{
            layoutManager = new LinearLayoutManager(this);
        }
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeAdapter();
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setOnClickListener(this);

        loadRecipesFromDb();

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
                    loadRecipesFromDb();
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "Cannot Retrieve data. Error code " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<net.husnilkamil.bakenow.retrofit.model.Recipe>> call, Throwable t) {
                Log.d(TAG, "Failed : " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Couldn't retrieve the data", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void loadRecipesFromDb() {
        List<Recipe> data = Recipe.listAll(Recipe.class);
        Log.d(TAG, "Data Count : " + data.size());
        if(mRecipeAdapter != null) {
            mRecipeAdapter.setRecipes(data);
        }
    }

    @Override
    public void onRecipeClick(int recipeId) {

        Log.d(TAG, "Recipe ID: " + recipeId);

        Intent detailIntent = new Intent(this, StepActivity.class);
        detailIntent.putExtra(Recipe.KEY_RECIPE_ID, recipeId);
        startActivity(detailIntent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(getString(R.string.loaded_data_key), dataLoaded);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        dataLoaded = savedInstanceState.getBoolean(getString(R.string.loaded_data_key));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
