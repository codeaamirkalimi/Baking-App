package com.example.aamirkalimi.bakingappmedia.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import java.util.List;

public class Recipe implements Parcelable {
    private String recipeName;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int serving;
    private String imageURL;
    public Recipe(   String inRecipeName,
              List<Ingredient> inIngredients,
              List<Step> inSteps,
              int inServing,
              String inImageURL){
        this.recipeName = inRecipeName;
        this.ingredients= inIngredients;
        this.steps= inSteps;
        this.serving= inServing;
        this.imageURL = inImageURL;
    }

    private  Recipe(Parcel in){
        recipeName = in.readString();
        ingredients= in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        serving = in.readInt();
        imageURL = in.readString();
    }
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServing() {
        return serving;
    }

    public String getImageURL() {
        return imageURL;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeName);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(serving);
        dest.writeString(imageURL);
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Recipe fromJson(String serializedRecipe) {
        Gson gson = new Gson();
        return gson.fromJson(serializedRecipe, Recipe.class);
    }
}
