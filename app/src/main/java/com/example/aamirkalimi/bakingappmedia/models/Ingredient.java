package com.example.aamirkalimi.bakingappmedia.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private  String ingredientName;
    private String measure;
    private String quantity;

    public Ingredient(String inIngredientName,
            String inMeasure,
            String inQuantity){
        this.ingredientName = inIngredientName;
        this.measure = inMeasure;
        this.quantity = inQuantity;


    }
    protected Ingredient(Parcel in){
        ingredientName = in.readString();
        measure=in.readString();
        quantity=in.readString();
    }
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }
        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }

    };
    //implement getters:


    public String getIngredientName() {
        return ingredientName;
    }

    public String getMeasure() {
        return measure;
    }

    public String getQuantity() {
        return quantity;
    }



    //implement setters:
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientName);
        dest.writeString(measure);
        dest.writeString(quantity);
    }
}
