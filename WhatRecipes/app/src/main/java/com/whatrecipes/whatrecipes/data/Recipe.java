package com.whatrecipes.whatrecipes.data;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class Recipe {
    private String name;
    private String recipeSummary;
    private Map<String,String> ingredients;
    private Integer cookingTime;
    private String imageUrl;
    private String stepsToPrepare;
    private Integer servings;
    private List<String> tags;
    private String author;
    private String authorImageUrl;
    private Bitmap bitmap;

    public Recipe(){}

    public Recipe(String name, String recipeSummary, Map<String,String> ingredients, Integer cookingTime, String bitmap,String stepsToPrepare, Integer servings, List<String> tags, String Author,String authorImageUrl){
        this.setName(name);
        this.setRecipeSummary(recipeSummary);
        this.setIngredients(ingredients);
        this.setCookingTime(cookingTime);
        this.setImageUrl(bitmap);
        this.setStepsToPrepare(stepsToPrepare);
        this.setServings(servings);
        this.setTags(tags);
        this.setAuthor(Author);
        this.setAuthorImageUrl(authorImageUrl);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }
}
