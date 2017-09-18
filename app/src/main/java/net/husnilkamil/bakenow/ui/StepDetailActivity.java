package net.husnilkamil.bakenow.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    long stepId, recipeId;
    List<Step> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);

        Intent detailIntent = getIntent();

        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        if(detailIntent != null) {
            recipeId = detailIntent.getLongExtra(Recipe.KEY_RECIPE_ID, 0);
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
            stepId = step.getId();
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
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
            Step step = stepList.get(idx);
            stepId = step.getId();
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
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

}
