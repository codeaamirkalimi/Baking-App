package com.example.aamirkalimi.bakingappmedia;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aamirkalimi.bakingappmedia.adapters.RecipeAdapter;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;
import com.example.aamirkalimi.bakingappmedia.utils.FetchRecipesTask;
import com.example.aamirkalimi.bakingappmedia.utils.NetworkUtils;
import com.example.aamirkalimi.bakingappmedia.widget.RecipeWidgetProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetConfigurationActivity extends AppCompatActivity implements
        RecipeAdapter.RecipeOnClickHandler {
    // private final String TAG= WidgetConfigurationActivity.class.getName();
    private final String LIFECYCLE_CALLBACKS_RECIPES = "recipes";
    //List<Recipe> mRecipeData;
    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerViewRecipes;

    @BindView(R.id.tv_error_message)
    TextView emptyView;

    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private RecipeAdapter recipeAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configuration);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.choose_a_recipe);
        }
        setResult(RESULT_CANCELED);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        int spanCount = 1;
        if (getResources().getBoolean(R.bool.isTablet)) {
            spanCount = 3;
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerViewRecipes.setLayoutManager(layoutManager);
        recyclerViewRecipes.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(this, this);
        recyclerViewRecipes.setAdapter(recipeAdapter);
        if (savedInstanceState != null) {
            recipeAdapter.setRecipes(savedInstanceState.<Recipe>getParcelableArrayList(LIFECYCLE_CALLBACKS_RECIPES));
        } else {
            loadRecipeData();
        }
    }

    // create a showLoading method that show the prograssBar and hide the recyclerView
    private void loadRecipeData() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            FetchRecipesTask mFetchRecipesTask = new FetchRecipesTask(this, recipeAdapter, this);
            mFetchRecipesTask.execute();
        } else {
            loadingIndicator.setVisibility(View.GONE);
            recyclerViewRecipes.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        if (recipe == null) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.baking_preferences),
                0
        );
        String serializedRecipe = recipe.serialize();
        sharedPreferences
                .edit()
                .putString(getString(R.string.serialized_recipe), serializedRecipe)
                .apply();

        appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

        //Retrieve the App Widget ID from the Intent that launched the Activity//

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null)
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            //If the intent doesn’t have a widget ID, then call finish()//

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }

            //TO DO, Perform the configuration and get an instance of the AppWidgetManager//

            //Create the return intent//

            Intent resultValue = new Intent();

            //Pass the original appWidgetId//

            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

         //Set the results from the configuration Activity//

            setResult(RESULT_OK, resultValue);
           //Finish the Activity//
            finish();
        }
    }

