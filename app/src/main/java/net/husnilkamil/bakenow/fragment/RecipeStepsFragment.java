package net.husnilkamil.bakenow.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.IngredientAdapter;
import net.husnilkamil.bakenow.adapter.StepAdapter;
import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.entities.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment {

    private static final String TAG = "RecipeStepsFragment";

    @BindView(R.id.rv_steps)
    RecyclerView mStepRecyclerView;

    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientRecyclerView;

    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;

    private long recipeId;
    private StepAdapter.OnStepClickListener mOnStepClickListener;

    public RecipeStepsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        ButterKnife.bind(this, view);

        mIngredientRecyclerView.setHasFixedSize(true);
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext());
        mIngredientRecyclerView.setLayoutManager(ingredientLayoutManager);

        mIngredientAdapter = new IngredientAdapter();
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        mStepRecyclerView.setHasFixedSize(true);
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext());
        mStepRecyclerView.setLayoutManager(stepLayoutManager);

        mStepAdapter = new StepAdapter();
        mStepRecyclerView.setAdapter(mStepAdapter);
        if(mOnStepClickListener != null){
            mStepAdapter.setClickListener(mOnStepClickListener);
        }

        loadData();

        return view;
    }

    private void loadData() {
        Log.d(TAG, "Recipe ID " + recipeId);

        List<Step> steps = Step.find(Step.class, "recipe_id=?", String.valueOf(recipeId));
        List<Ingredient> ingredients = Ingredient.find(Ingredient.class, "recipe_id=?", String.valueOf(recipeId));

        Log.d(TAG, "Steps Count : " + steps.size());
        Log.d(TAG, "Ingredients counts : " + ingredients.size());

        mIngredientAdapter.setData(ingredients);
        mStepAdapter.setData(steps);

    }

    public void setAdapterClickListener(StepAdapter.OnStepClickListener clickListener){
        this.mOnStepClickListener = clickListener;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}
