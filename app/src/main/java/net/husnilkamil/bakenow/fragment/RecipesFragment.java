package net.husnilkamil.bakenow.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.RecipeAdapter;
import net.husnilkamil.bakenow.entities.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


public class RecipesFragment extends Fragment {
    private final static String TAG = "RecipesFragment";

    @BindView(R.id.rv_recipes)
    RecyclerView mRecipeRecyclerView;

    private RecipeAdapter mRecipeAdapter;
    RecipeAdapter.OnRecipeClickListener mRecipeOnClickListener;
    boolean displayOnGrid = false;

    public RecipesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        ButterKnife.bind(this, view);

        mRecipeRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager;
        if(displayOnGrid){
            layoutManager = new GridLayoutManager(getContext(), 2);
        }else{
            layoutManager = new LinearLayoutManager(getContext());
        }
        mRecipeRecyclerView.setLayoutManager(layoutManager);

        mRecipeAdapter = new RecipeAdapter();
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        if(mRecipeOnClickListener != null){
            mRecipeAdapter.setOnClickListener(mRecipeOnClickListener);
        }

        loadRecipesFromDb();

        return view;
    }

    public void setAdapterClickListener(RecipeAdapter.OnRecipeClickListener onRecipeClickListener){
        this.mRecipeOnClickListener = onRecipeClickListener;
    }

    public void loadRecipesFromDb() {
        List<Recipe> data = Recipe.listAll(Recipe.class);
        Log.d(TAG, "Data Count : " + data.size());
        if(mRecipeAdapter != null) {
            mRecipeAdapter.setRecipes(data);
        }
    }

    public void setDisplayOnGrid(boolean displayOnGrid){
        this.displayOnGrid = displayOnGrid;
    }
}
