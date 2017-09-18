package net.husnilkamil.bakenow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.husnilkamil.bakenow.R;
import net.husnilkamil.bakenow.entities.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by husnilk on 9/12/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    List<Step> data;
    OnStepClickListener stepClickListener;


    public StepAdapter(){

    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        StepHolder holder = new StepHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        Step step = data.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<Step> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setClickListener(OnStepClickListener clickListener) {
        this.stepClickListener = clickListener;
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_step_short_desc)
        TextView mStepShortDescText;

        View itemView;

        public StepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.itemView = itemView;
        }

        public void bind(Step step){
            mStepShortDescText.setText(step.getShortDescription());
            itemView.setTag(step.getId());
        }

        @Override
        public void onClick(View view) {
            long stepId = data.get(getAdapterPosition()).getId();
            stepClickListener.onStepClick(stepId);
        }
    }

    public interface OnStepClickListener{
        void onStepClick(long id);
    }

}
