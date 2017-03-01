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
    private List<String> ingredientsName;
    private List<String> ingredientsQuantity;
    private Map<String, String> ingredients;
    private Integer cookingTime;
    private String imageUrl;
    private String stepsToPrepare;
    private Integer servings;
    private List<String> tags;
    private Integer loves;
    private String author;
    private String authorImageUrl;
    private Bitmap bitmap;

    public Recipe() {
    }

    public Recipe(String name, String recipeSummary, List<String> ingredientsName, List<String> ingredientsQuantity, Integer cookingTime, String bitmap, String stepsToPrepare, Integer servings, List<String> tags, String Author, String authorImageUrl) {
        this.setName(name);
        this.setRecipeSummary(recipeSummary);
        this.setIngredientsName(ingredientsName);
        this.setIngredientsQuantity(ingredientsQuantity);
        this.setCookingTime(cookingTime);
        this.setImageUrl(bitmap);
        this.setStepsToPrepare(stepsToPrepare);
        this.setServings(servings);
        this.setTags(tags);
        this.setAuthor(Author);
        this.setAuthorImageUrl(authorImageUrl);
        this.loves = 0;
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

    public Integer getLoves() {
        return loves;
    }

    public void setLoves(Integer loves) {
        this.loves = loves;
    }

    public List<String> getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(List<String> ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public List<String> getIngredientsName() {
        return ingredientsName;
    }

    public void setIngredientsName(List<String> ingredientsName) {
        this.ingredientsName = ingredientsName;
    }
}
