package net.husnilkamil.bakenow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.entities.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by husnilk on 9/12/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    List<Ingredient> data;

    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_ingredient, parent, false);

        IngredientHolder holder = new IngredientHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        Ingredient ingredient = data.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<Ingredient> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_ingredient)
        TextView mIngredientTextView;
        @BindView(R.id.tv_quantity)
        TextView mQuantityTextView;
        @BindView(R.id.tv_measure)
        TextView mMeasureTextView;

        public IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ingredient ingredient){
            mIngredientTextView.setText(ingredient.getIngredient());
            mQuantityTextView.setText(String.valueOf(ingredient.getQuantity()));
            mMeasureTextView.setText(ingredient.getMeasure());
        }
    }

}
