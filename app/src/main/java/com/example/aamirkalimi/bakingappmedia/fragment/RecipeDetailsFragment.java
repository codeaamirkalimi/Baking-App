package com.example.aamirkalimi.bakingappmedia.fragment;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.adapters.IngredientAdapter;
import com.example.aamirkalimi.bakingappmedia.adapters.StepAdapter;
import com.example.aamirkalimi.bakingappmedia.models.Ingredient;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;
import com.example.aamirkalimi.bakingappmedia.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment
        implements StepAdapter.StepOnClickHandler {

    @BindView(R.id.recycler_view_ingredients)
    RecyclerView recyclerViewIngredients;

    @BindView(R.id.recycler_view_steps)
    RecyclerView recyclerViewSteps;

    @BindView(R.id.nested_scroll_view_recipe_details)
    NestedScrollView nestedScrollViewRecipeDetails;

    private RecipeDetailsOnClickListener listener;
    private StepAdapter stepAdapter;
    // Tag for logging
    private static final String TAG = "RecipeDetailsFragment";


    public RecipeDetailsFragment() {

    }

    public static Fragment newInstance(Bundle bundle) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);
        return recipeDetailsFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        List<Ingredient> ingredients = null;
        List<Step> steps = null;
        if (bundle != null) {
            Recipe recipe = bundle.getParcelable(getString(R.string.recipe));
            if (recipe != null) {
                ingredients = recipe.getIngredients();
                steps = recipe.getSteps();
            }
        }

        IngredientAdapter ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewIngredients.setAdapter(ingredientAdapter);

        stepAdapter = new StepAdapter(getContext(), steps, this);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSteps.setAdapter(stepAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeDetailsOnClickListener) {
            listener = (RecipeDetailsOnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.must_implement)
                    + RecipeDetailsOnClickListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(getString(R.string.scroll_position),
                new int[]{nestedScrollViewRecipeDetails.getScrollX(), nestedScrollViewRecipeDetails.getScrollY()});
        outState.putInt(getString(R.string.selected_row_index), stepAdapter.getSelectedRowIndex());
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            final int[] position = savedInstanceState.getIntArray(getString(R.string.scroll_position));
            if (position != null) {
                nestedScrollViewRecipeDetails.post(new Runnable() {
                    public void run() {
                        nestedScrollViewRecipeDetails.scrollTo(position[0], position[1]);
                    }
                });
            }
            int selectedRowIndex = savedInstanceState.getInt(getString(R.string.selected_row_index));
            stepAdapter.setSelectedRowIndex(selectedRowIndex);
            stepAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(int position) {
        listener.onStepSelected(position);
    }

    public StepAdapter getStepAdapter() {
        return stepAdapter;
    }

    public interface RecipeDetailsOnClickListener {
        void onStepSelected(int position);
    }
}
