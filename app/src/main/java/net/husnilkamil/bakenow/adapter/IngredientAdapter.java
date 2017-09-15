package net.husnilkamil.bakenow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by husnilk on 9/12/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {



    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class IngredientHolder extends RecyclerView.ViewHolder{
        public IngredientHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickListener{
        void onClick();
    }
}
