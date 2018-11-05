package com.example.aamirkalimi.bakingappmedia.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private Context mContext;
    private List<Ingredient> mIngredients;
    public IngredientAdapter(Context context, List<Ingredient> ingredients){
        mContext = context;
        mIngredients =ingredients;

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.layout_ingredient_list_item,
                parent,
                false
        );
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if(mIngredients == null){
            return;
        }
        Ingredient ingredient=mIngredients.get(position);

        String name = ingredient.getIngredientName();
        String formattedName = name.substring(0, 1).toUpperCase() + name.substring(1);
        holder.mTextViewIngredientName.setText(formattedName);
        String quantityMeasurement = ingredient.getQuantity() + " " + ingredient.getMeasure().toLowerCase();
        holder.mTextViewIngredientQuantityMeasurement.setText(quantityMeasurement);

    }

    @Override
    public int getItemCount() {
        if(mIngredients == null){
        return 0;}
        return mIngredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_ingredient_name)
        TextView mTextViewIngredientName;

        @BindView(R.id.text_view_ingredient_quantity_measurement)
        TextView mTextViewIngredientQuantityMeasurement;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
