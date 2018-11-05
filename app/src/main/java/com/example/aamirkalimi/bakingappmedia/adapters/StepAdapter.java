package com.example.aamirkalimi.bakingappmedia.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter  extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{
    private Context mContext;
    private List<Step> mSteps;
    private StepOnClickHandler mClickHandler;
    private int selectedRowIndex = 0;
   public StepAdapter(Context context, List<Step> steps,StepOnClickHandler clickHandler) {
       mContext = context;
       mSteps = steps;
       mClickHandler = clickHandler;
   }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(
            R.layout.layout_step_list_item,
            parent,
            false
    );
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
       if(mSteps == null){
           return;
       }
       if(mContext.getResources().getBoolean(R.bool.isTablet)){

           if (selectedRowIndex == position) {
               holder.layoutStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
               holder.textViewStep.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
           } else {
               holder.layoutStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTheme));
               holder.textViewStep.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
           }
       }

        Step step = mSteps.get(position);
        holder.textViewStep.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
      if(mSteps == null)  return 0;
      return  mSteps.size();
    }
    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.layout_step)
        ConstraintLayout layoutStep;

        @BindView(R.id.text_view_step)
        TextView textViewStep;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           mClickHandler.onClick(getAdapterPosition());
        }
    }

    public interface StepOnClickHandler {
        void onClick(int position);
    }


    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }
}
