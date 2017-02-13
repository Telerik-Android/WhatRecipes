package com.whatrecipes.whatrecipes.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import static android.R.attr.bitmap;
import static android.R.attr.tag;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class Recipe {
    private String name;
    private String recipeSummary;
    private String[] ingredients;
    private Integer cookingTime;
    private String encodedImage;
    private String[] stepsToPrepare;
    private Integer servings;
    private String[] tags;

    public Recipe(String name, String recipeSummary, String[] ingredients, Integer cookingTime, Bitmap bitmap, String[] stepsToPrepare, Integer servings, String[] tags){
        this.setName(name);
        this.setRecipeSummary(recipeSummary);
        this.setIngredients(ingredients);
        this.setCookingTime(cookingTime);
        this.setEncodedImage(bitmap);
        this.setStepsToPrepare(stepsToPrepare);
        this.setServings(servings);
        this.setTags(tags);
    }

    public Bitmap getEncodedImage() {
        return this.decodeImage(encodedImage);
    }

    public void setEncodedImage(Bitmap bitmap) {
        this.encodedImage = this.encodeImage(bitmap);
    }

    public Bitmap decodeImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return encodedImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String[] getStepsToPrepare() {
        return stepsToPrepare;
    }

    public void setStepsToPrepare(String[] stepsToPrepare) {
        this.stepsToPrepare = stepsToPrepare;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getRecipeSummary() {
        return recipeSummary;
    }

    public void setRecipeSummary(String recipeSummary) {
        this.recipeSummary = recipeSummary;
    }
}
