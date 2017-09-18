package net.husnilkamil.bakenow.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.StepAdapter;
import net.husnilkamil.bakenow.entities.Step;
import net.husnilkamil.bakenow.fragment.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity implements StepAdapter.OnStepClickListener{

    long stepId;
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent detailIntent = getIntent();

        stepDetailFragment = new StepDetailFragment();

        if(detailIntent != null) {
            stepId = detailIntent.getLongExtra(Step.KEY_STEP_ID, 0);
            stepDetailFragment.setStepId(stepId);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_detail_fragment_container, stepDetailFragment)
                .commit();

    }

    @Override
    public void onStepClick(long id) {
        Toast.makeText(this, "Halo", Toast.LENGTH_SHORT).show();
    }
}
