package com.example.aamirkalimi.bakingappmedia.adapters;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;
import com.example.aamirkalimi.bakingappmedia.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private RecipeOnClickHandler clickHandler;

    public RecipeAdapter(Context context, RecipeOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;
        recipeList= new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.recipe_list_item,
                parent,
                false
        );
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        if (recipeList == null) {
            return;
        }
        Recipe recipe = recipeList.get(position);
        holder.mTextViewRecipeName.setText(recipe.getRecipeName());
        holder.mTextViewServes.setText(context.getString(
                R.string.serves,
                String.valueOf(recipe.getServing())
        ));
        String recipeImage = recipe.getImageURL();
        if(!recipeImage.isEmpty()) {
            Picasso.with(context).load(recipeImage).into(holder.mImageViewRecipeItem);
        } else {
            int drawableId = ImageUtils.getImageResourceId(context, recipe.getRecipeName());
           Picasso.with(context).load(drawableId).into(holder.mImageViewRecipeItem);
           // holder.mImageViewRecipeItem.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        clear();
        if(recipes != null) {
            for (Recipe mRecipe : recipes) {
                add(mRecipe);
            }
        }
    }
    public void add(Recipe inRecipe) {

        recipeList.add(inRecipe);
        notifyDataSetChanged();
    }


    public void clear() {
        if (recipeList == null) {
            return;
        }
        recipeList.clear();
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView mTextViewRecipeName;

        @BindView(R.id.tv_serving)
        TextView mTextViewServes;

        @BindView(R.id.imageView_recipe)
        ImageView mImageViewRecipeItem;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            clickHandler.onClick(recipeList.get(index));
        }
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public interface RecipeOnClickHandler {
        void onClick(Recipe recipe);
    }
}
