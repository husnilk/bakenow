package net.husnilkamil.bakenow.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.fragment.StepDetailFragment;
import net.husnilkamil.bakenow.idlingres.SimpleIdlingResource;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailActivity extends AppCompatActivity{

    long stepId;
    int recipeId;
    List<Step> stepList;
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent detailIntent = getIntent();

        stepDetailFragment = new StepDetailFragment();

        if(detailIntent != null) {
            recipeId = detailIntent.getIntExtra(Recipe.KEY_RECIPE_ID, 1);
            stepId = detailIntent.getLongExtra(Step.KEY_STEP_ID, 0);
            stepDetailFragment.setStepId(stepId);
            stepList = Step.find(Step.class, "recipe_id=?", String.valueOf(recipeId));
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_detail_fragment_container, stepDetailFragment)
                .commit();

    }
    
    @OnClick(R.id.previous_button)
    public void previous(View view){
        int idx = getIndex(stepId);
        if(idx <= 0 ){
            Toast.makeText(this, "Already at first step", Toast.LENGTH_SHORT).show();
        }else{
            idx--;
            Step step = stepList.get(idx);
            stepDetailFragment.releasePlayer();
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
            stepDetailFragment.releasePlayer();
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
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.recipe_id_key), recipeId);
        outState.putLong(getString(R.string.step_id_key), stepId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipeId = savedInstanceState.getInt(getString(R.string.recipe_id_key));
        stepId = savedInstanceState.getLong(getString(R.string.step_id_key));
    }
}
