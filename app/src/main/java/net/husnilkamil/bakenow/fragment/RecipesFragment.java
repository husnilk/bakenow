package net.husnilkamil.bakenow.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.adapter.RecipeAdapter;
import net.husnilkamil.bakenow.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesFragment extends Fragment {
    @BindView(R.id.rv_recipes) RecyclerView mRecipeRecyclerView;

    private RecipeAdapter mRecipeAdapter;

    public RecipesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        ButterKnife.bind(this, view);

        mRecipeRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecipeRecyclerView.setLayoutManager(layoutManager);

        mRecipeAdapter = new RecipeAdapter();
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void setRecipeData(List<Recipe> data){
        mRecipeAdapter.setRecipes(data);
    }
}
