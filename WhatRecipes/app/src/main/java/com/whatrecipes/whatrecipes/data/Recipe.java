package com.whatrecipes.whatrecipes.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;
import static android.R.attr.tag;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class Recipe {
    private String name;
    private String recipeSummary;
    private Map<String,String> ingredients;
    private Integer cookingTime;
    private String encodedImage;
    private String stepsToPrepare;
    private Integer servings;
    private List<String> tags;
    private String author;
    private Bitmap bitmap;

    public Recipe(){}

    public Recipe(String name, String recipeSummary, Map<String,String> ingredients, Integer cookingTime, String bitmap,String stepsToPrepare, Integer servings, List<String> tags, String Author){
        this.setName(name);
        this.setRecipeSummary(recipeSummary);
        this.setIngredients(ingredients);
        this.setCookingTime(cookingTime);
        this.setEncodedImage(bitmap);
        this.setStepsToPrepare(stepsToPrepare);
        this.setServings(servings);
        this.setTags(tags);
        this.setAuthor(Author);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getStepsToPrepare() {
        return stepsToPrepare;
    }

    public void setStepsToPrepare(String stepsToPrepare) {
        this.stepsToPrepare = stepsToPrepare;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getRecipeSummary() {
        return recipeSummary;
    }

    public void setRecipeSummary(String recipeSummary) {
        this.recipeSummary = recipeSummary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
