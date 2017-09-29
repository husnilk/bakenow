package net.husnilkamil.bakenow.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;
import android.widget.Toast;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.RecipeWidgetPovider;
import net.husnilkamil.bakenow.adapter.StepAdapter;
import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.fragment.RecipeStepsFragment;
import net.husnilkamil.bakenow.fragment.StepDetailFragment;

import java.util.List;

public class StepActivity extends AppCompatActivity implements StepAdapter.OnStepClickListener{

    private static final String TAG = StepActivity.class.getSimpleName();

    boolean isTwoPane;
    long recipeId;
    long stepId;
    Recipe recipe;
    FragmentManager fragmentManager;
    RecipeStepsFragment recipeStepsFragment;
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent recipeDetailIntent = getIntent();
        if (recipeDetailIntent != null) {
            recipeId = recipeDetailIntent.getLongExtra(Recipe.KEY_RECIPE_ID, 0);
            recipe = Recipe.findById(Recipe.class, recipeId);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(recipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);

            Log.d(TAG, "Recipe ID : " + String.valueOf(recipeId));

            List<Step> stepList = Step.find(Step.class, "recipe_id=?", String.valueOf(recipeId));
            if(stepList.size() > 0){
                stepId = stepList.get(0).getId();
            }
        }

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.linear_layout_sw600dp) != null){
            isTwoPane = true;

            recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setAdapterClickListener(this);

            stepDetailFragment = new StepDetailFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.step_fragment_container, recipeStepsFragment)
                    .add(R.id.detail_fragment_container, stepDetailFragment)
                    .commit();

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepId(stepId);
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_container, stepDetailFragment)
                    .commit();

        }else {
            isTwoPane = false;
            recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setAdapterClickListener(this);

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_fragment_container, recipeStepsFragment)
                    .commit();
        }

        recipeStepsFragment.setRecipeId(recipeId);

    }

    @Override
    public void onStepClick(long stepId) {

        if(isTwoPane){
            if(stepDetailFragment != null){
                stepDetailFragment.releasePlayer();
            }
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepId(stepId);
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_container, stepDetailFragment)
                    .commit();
        }else {
            Intent stepDetailIntent = new Intent(this, StepDetailActivity.class);
            stepDetailIntent.putExtra(Recipe.KEY_RECIPE_ID, recipeId);
            stepDetailIntent.putExtra(Step.KEY_STEP_ID, stepId);
            startActivity(stepDetailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.widgetmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.action_to_widget:
                SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(Recipe.KEY_RECIPE_ID, recipe.getRecipeId());
                editor.apply();
                updateWidgetDisplay();
                Toast.makeText(this, "Recipe displayed on widget", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateWidgetDisplay(){

        Intent intent = new Intent(this, RecipeWidgetPovider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetPovider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

    }
}
