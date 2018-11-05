package com.example.aamirkalimi.bakingappmedia.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.aamirkalimi.bakingappmedia.MainActivity;
import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.RecipeDetailsActivity;
import com.example.aamirkalimi.bakingappmedia.data.RecipeContract;
import com.example.aamirkalimi.bakingappmedia.models.Ingredient;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
        int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget_recipe);
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.baking_preferences),
                0
        );

        String serializedRecipe = sharedPreferences.getString(context.getString(
                R.string.serialized_recipe),
                null);
        Intent intent = new Intent(context, ListWidgetService.class);
        Uri uri = RecipeContract.BASE_CONTENT_URI
                .buildUpon()
                .appendPath("widget")
                .appendPath("id")
                .appendPath(String.valueOf(appWidgetId))
                .build();
        intent.setData(uri);

        views.setRemoteAdapter(R.id.widget_list_view, intent);
        views.setEmptyView(R.id.widget_empty_view, R.id.tv_error_message);
        if (!TextUtils.isEmpty(serializedRecipe)) {
            Recipe recipe = Recipe.fromJson(serializedRecipe);
            views.setTextViewText(R.id.widget_text_view_recipe_name, recipe.getRecipeName());

            Intent appIntent = new Intent(context, RecipeDetailsActivity.class);
            appIntent.putExtra(context.getString(R.string.recipe), recipe);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
           views.setOnClickPendingIntent(R.id.widget_layout, appPendingIntent);
            //  views.setPendingIntentTemplate(R.id.widget_layout, appPendingIntent);

        }



        appWidgetManager.updateAppWidget(appWidgetId, views);
             }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}