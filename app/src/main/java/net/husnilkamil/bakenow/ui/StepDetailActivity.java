package net.husnilkamil.bakenow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.fragment.StepDetailFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailActivity extends AppCompatActivity{

    private static final String TAG = "StepDetailActivity";
    long stepId = -1;
    int recipeId = -1;
    List<Step> stepList;
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent detailIntent = getIntent();

        if(savedInstanceState == null){
            stepDetailFragment = new StepDetailFragment();
            recipeId = detailIntent.getIntExtra(Recipe.KEY_RECIPE_ID, 1);
            stepId = detailIntent.getLongExtra(Step.KEY_STEP_ID, 0);
            stepDetailFragment.setStepId(stepId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment_container, stepDetailFragment)
                    .commit();
        }else {
            recipeId = savedInstanceState.getInt(getString(R.string.recipe_id_key));
            stepId = savedInstanceState.getLong(getString(R.string.step_id_key));
            Log.d(TAG, "Restore State " + recipeId + " " + stepId);
        }
        stepList = Step.find(Step.class, "recipe_id=?", String.valueOf(recipeId));



    }
    
    @OnClick(R.id.previous_button)
    public void previous(View view){
        int idx = getIndex(stepId);
        if(idx <= 0 ){
            Toast.makeText(this, "Already at first step", Toast.LENGTH_SHORT).show();
        }else{
            idx--;
            Step step = stepList.get(idx);
            //stepDetailFragment.releasePlayer();
            stepId = step.getId();
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepId(stepId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment_container, stepDetailFragment)
                    .commit();
        }
    }
    
    @OnClick(R.id.next_button)
    public void next(View view){
        int idx = getIndex(stepId);
        if(idx >= (stepList.size() - 1)){
            Toast.makeText(this, "This is the last step", Toast.LENGTH_SHORT).show();
        }else{
            idx++;
            //stepDetailFragment.releasePlayer();
            Step step = stepList.get(idx);
            stepId = step.getId();
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepId(stepId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment_container, stepDetailFragment)
                    .commit();
        }
    }

    public int getIndex(long stepId){
        int index = 0;
        for (Step step : this.stepList) {
            if(step.getId() == stepId){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.recipe_id_key), recipeId);
        outState.putLong(getString(R.string.step_id_key), stepId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        recipeId = savedInstanceState.getInt(getString(R.string.recipe_id_key));
        stepId = savedInstanceState.getLong(getString(R.string.step_id_key));
        Log.d(TAG, "Restore Instance " + recipeId +  " " + stepId);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stepDetailFragment = null;
    }
}
