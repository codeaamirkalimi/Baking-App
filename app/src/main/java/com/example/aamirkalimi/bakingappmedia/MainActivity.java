package com.example.aamirkalimi.bakingappmedia;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamirkalimi.bakingappmedia.adapters.RecipeAdapter;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;
import com.example.aamirkalimi.bakingappmedia.utils.FetchRecipesTask;
import com.example.aamirkalimi.bakingappmedia.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements  RecipeAdapter.RecipeOnClickHandler  {


    private final String TAG= MainActivity.class.getName();
    //create an ID to identify the loader responsible for loading our recipe data
    private final String LIFECYCLE_CALLBACKS_RECIPES = "recipes";
    List<Recipe> mRecipeData;
    @BindView(R.id.recycler_view_recipes)
    RecyclerView mRecyclerViewRecipes;
    @BindView(R.id.loading_indicator)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_error_message)
    TextView mTextViewErrorMessage;
    private RecipeAdapter mRecipeAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewRecipes.setLayoutManager(layoutManager);
        mRecyclerViewRecipes.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this,this);
        mRecyclerViewRecipes.setAdapter(mRecipeAdapter);
        if (savedInstanceState != null) {
            mRecipeAdapter.setRecipes( savedInstanceState.<Recipe>getParcelableArrayList(LIFECYCLE_CALLBACKS_RECIPES));
        }else {
            loadRecipeData();
        }
    }

    //   Override onSaveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //  Call super.onSaveInstanceState
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIFECYCLE_CALLBACKS_RECIPES, new ArrayList<Recipe>(mRecipeAdapter.getRecipeList()));
    }

    // create a showLoading method that show the prograssBar and hide the recyclerView
    private void loadRecipeData() {
        if(NetworkUtils.isNetworkAvailable(this)) {
            FetchRecipesTask mFetchRecipesTask = new FetchRecipesTask(this,mRecipeAdapter,this);
            mFetchRecipesTask.execute();
   }
   else {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerViewRecipes.setVisibility(View.GONE);
            mTextViewErrorMessage.setVisibility(View.VISIBLE);
            mTextViewErrorMessage.setText(R.string.no_internet_connection);
        }
    }



    @Override
    public void onClick(Recipe recipe) {
        Toast.makeText(getApplicationContext(), recipe.getRecipeName(), Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.recipe), recipe);
        startActivity(intent);


    }



}
