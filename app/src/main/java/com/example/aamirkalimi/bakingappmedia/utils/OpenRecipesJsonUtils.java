package com.example.aamirkalimi.bakingappmedia.utils;

import android.content.Context;
import android.util.Log;

import com.example.aamirkalimi.bakingappmedia.R;
import com.example.aamirkalimi.bakingappmedia.models.Ingredient;
import com.example.aamirkalimi.bakingappmedia.models.Recipe;
import com.example.aamirkalimi.bakingappmedia.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenRecipesJsonUtils {

    private static List<Recipe> recipeData;
    private static final  String TAG= OpenRecipesJsonUtils.class.getName();

    public static  List<Recipe> getSimpleWeatherStringsFromJson(Context context, String jsonRecipeResponse){
        recipeData = new ArrayList<>();
        try {
            JSONArray jsonObjectRecipeArray = new JSONArray(jsonRecipeResponse);
            for (int i = 0; i < jsonObjectRecipeArray.length(); i++) {
                JSONObject recipeObject = jsonObjectRecipeArray.getJSONObject(i);
                Recipe newRecipe= getRecipe(context,recipeObject);
                recipeData.add(newRecipe);
            }
            return recipeData;


        }catch(JSONException e){
            e.printStackTrace();
            Log.d(TAG,"Error with parse json data");
        }
        return null;
    }

    private static Recipe getRecipe(Context context, JSONObject recipeJson) throws JSONException {
        String name = recipeJson.getString(context.getString(R.string.name));
        List<Ingredient> ingredients = getIngredients(context, recipeJson);
        List<Step> steps = getSteps(context, recipeJson);
        int servings = recipeJson.getInt(context.getString(R.string.servings));
        String imageUrl = recipeJson.getString(context.getString(R.string.image));

        return new Recipe(name, ingredients, steps, servings, imageUrl);
    }

    private static List<Ingredient> getIngredients(Context context, JSONObject recipeJson) throws JSONException {
        JSONArray ingredientsJsonArray = recipeJson.getJSONArray(context.getString(R.string.ingredients));
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            JSONObject ingredientJson = ingredientsJsonArray.getJSONObject(i);
            String name = ingredientJson.getString(context.getString(R.string.ingredient));
            String measure = ingredientJson.getString(context.getString(R.string.measure));
            String quantity = ingredientJson.getString(context.getString(R.string.quantity));

            Ingredient ingredient = new Ingredient(name, measure, quantity);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private static List<Step> getSteps(Context context, JSONObject recipeJson) throws JSONException {
        JSONArray stepsJsonArray = recipeJson.getJSONArray(context.getString(R.string.steps));
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < stepsJsonArray.length(); i++) {
            JSONObject stepJson = stepsJsonArray.getJSONObject(i);
            String shortDescription = stepJson.getString(context.getString(R.string.short_description));
            String description = stepJson.getString(context.getString(R.string.description));
            String videoUrl = stepJson.getString(context.getString(R.string.video_url));
            String thumbnailUrl = stepJson.getString(context.getString(R.string.thumbnail_url));

            Step step = new Step(shortDescription, description, videoUrl, thumbnailUrl);
            steps.add(step);
        }
        return steps;
    }
}
