package com.example.aamirkalimi.bakingappmedia.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aamirkalimi.bakingappmedia.MainActivity;
import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.adapters.RecipeAdapter;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FetchRecipesTask  extends AsyncTask<Void, Void, List<Recipe>> {

    Context mContext;
    List<Recipe> mRecipes;
    Activity mActivity;
    private ProgressBar mLoadingIndicator;
   private TextView mErrorMessageDisplay;
   private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipesAdapter;

    public FetchRecipesTask(Context context,RecipeAdapter recipesAdapter, Activity activity){
        mActivity = activity;
        mContext = context;
        mRecipesAdapter = recipesAdapter;
        mLoadingIndicator =  (ProgressBar) mActivity.findViewById(R.id.loading_indicator);
        mErrorMessageDisplay = (TextView) mActivity.findViewById(R.id.tv_error_message);
        mRecyclerView = (RecyclerView)mActivity.findViewById(R.id.recycler_view_recipes);
        mRecipes = new ArrayList<>();
    }
    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        URL recipesRequestUrl  = NetworkUtils.buildUrl();
        try {
            String jsonRecipeResponse  = NetworkUtils.getResponseFromHttpUrl(recipesRequestUrl);
            List<Recipe> jsonRecipeData  = OpenRecipesJsonUtils.getSimpleWeatherStringsFromJson(mContext, jsonRecipeResponse);
            mRecipes=jsonRecipeData;
            return jsonRecipeData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        super.onPostExecute(recipes);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (recipes != null) {
            showRecipeDataView();
            mRecipesAdapter.setRecipes(recipes);

        } else {
            showErrorMessage();
        }
    }

    private void showRecipeDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(R.string.error_message);
        mRecyclerView.setVisibility(View.INVISIBLE);

    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }


}
