package net.husnilkamil.bakenow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by husnilk on 9/12/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class StepHolder extends RecyclerView.ViewHolder{
        public StepHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickListener {
        void onClick();
    }
}
