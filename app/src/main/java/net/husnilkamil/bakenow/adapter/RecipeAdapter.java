package net.husnilkamil.bakenow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.entities.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by husnilk on 9/12/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private static final String TAG = "RecipeAdapter";

    private List<Recipe> recipes;
    private OnRecipeClickListener mRecipeClickListener;

    public void setOnClickListener(OnRecipeClickListener mRecipeClickListener) {
        Log.d(TAG, mRecipeClickListener.toString());
        this.mRecipeClickListener = mRecipeClickListener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recipe, parent, false);

        RecipeHolder holder = new RecipeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if(recipes == null){
            return 0;
        }else{
            return recipes.size();
        }
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.img_recipe_image)
        ImageView mRecipeImage;
        @BindView(R.id.text_recipe_title)
        TextView mTextRecipeTitle;
        @BindView(R.id.text_recipe_serving)
        TextView mTextRecipeServing;

        View view;
        Context context;

        public RecipeHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            view = itemView;
        }

        public void bind(Recipe recipe){
            String servingText = "serving for " + recipe.getServings();
            mTextRecipeTitle.setText(recipe.getName());
            mTextRecipeServing.setText(servingText);
            if(recipe.getImage() == "" || recipe.getImage().equals("")){
                mRecipeImage.setImageResource(R.drawable.dining_icon);
            }else {
                String imageUrl = recipe.getImage();
                Picasso.with(context).load(imageUrl).into(mRecipeImage);
            }
            view.setTag(recipe.getId());
        }

        @Override
        public void onClick(View view) {
            long recipeId = (long) view.getTag();
            mRecipeClickListener.onRecipeClick(recipeId);
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(long recipeId);
    }
}
