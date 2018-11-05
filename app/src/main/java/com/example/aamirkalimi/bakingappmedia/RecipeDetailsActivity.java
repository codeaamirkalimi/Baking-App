package com.example.aamirkalimi.bakingappmedia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aamirkalimi.bakingappmedia.adapters.StepAdapter;
import com.example.aamirkalimi.bakingappmedia.fragment.RecipeDetailsFragment;
import com.example.aamirkalimi.bakingappmedia.fragment.StepDetailsFragment;
import com.example.aamirkalimi.bakingappmedia.idlingresource.ExoPlayerIdlingResource;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;
import com.example.aamirkalimi.bakingappmedia.models.Step;
import com.example.aamirkalimi.bakingappmedia.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements
        RecipeDetailsFragment.RecipeDetailsOnClickListener,
        StepDetailsFragment.StepDetailsOnClickListener {

    private Recipe recipe;
    private int position;
    @Nullable
    private ExoPlayerIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        if (intent != null) {
            recipe = intent.getParcelableExtra(getString(R.string.recipe));
        }

        if (recipe != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(recipe.getRecipeName());
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.findFragmentByTag(RecipeDetailsFragment.class.getSimpleName()) != null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.recipe), recipe);

            fragmentManager.beginTransaction()
                    .add(
                            R.id.container_recipe_details,
                            RecipeDetailsFragment.newInstance(bundle),
                            RecipeDetailsFragment.class.getSimpleName()
                    )
                    .commit();

            if (getResources().getBoolean(R.bool.isTablet)) {
                List<Step> steps = recipe.getSteps();
                if (steps != null) {
                    bundle = new Bundle();
                    bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<Step>) steps);
                    bundle.putInt(getString(R.string.step_position), position);
                    FragmentUtils.addDetailsFragment(this, bundle);
                }
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            List<Step> steps = recipe.getSteps();
            if (steps != null) {
                this.position = position;

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<Step>) steps);
                bundle.putInt(getString(R.string.step_position), position);
                FragmentUtils.replaceDetailsFragment(this, bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeDetailsFragment recipeDetailsFragment = (RecipeDetailsFragment) fragmentManager
                        .findFragmentByTag(RecipeDetailsFragment.class.getSimpleName());
                if (recipeDetailsFragment != null) {
                    StepAdapter stepAdapter = recipeDetailsFragment.getStepAdapter();
                    stepAdapter.setSelectedRowIndex(position);
                    stepAdapter.notifyDataSetChanged();
                }
            }
        } else {
            List<Step> steps = recipe.getSteps();

            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(getString(R.string.steps), (ArrayList<Step>) steps);
            intent.putExtra(getString(R.string.step_position), position);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                intent.putExtra(getString(R.string.name), actionBar.getTitle());
            }
            startActivity(intent);
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new ExoPlayerIdlingResource();
        }
        return idlingResource;
    }

    public void setIdleState(boolean idle) {
        if (idlingResource == null) {
            return;
        }
        idlingResource.setIdleState(idle);
    }
}
