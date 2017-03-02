package com.whatrecipes.whatrecipes.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.CircleTransform;
import com.whatrecipes.whatrecipes.view.ActivityRecipeDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    @BindView(R.id.recipeImageView)
    ImageView recipeImage;

    @BindView(R.id.EditTextRecipeTextTitle)
    TextView tvTitle;

    @BindView(R.id.TextViewRecipeCookingTime)
    TextView etCookingTime;

    @BindView(R.id.textViewAuthor)
    TextView tvAuthor;

    @BindView(R.id.textViewRecipeSummary)
    TextView tvRecipeSummary;

    @BindView(R.id.textViewAuthorProfileImage)
    ImageView ivAuthorProfileImage;

    @BindView(R.id.TextViewRecipeServings)
    TextView tvRecipeServings;

    @BindView(R.id.TextViewRecipeLoves)
    TextView tvRecipeLoves;

    @BindView(R.id.EditTextViewRecipe)
    TextView editTextViewRecipe;


    public RecipeAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButterKnife.bind(this, convertView);
        editTextViewRecipe.setTag(position);

        String text = getItem(position).getName();
        tvTitle.setText(text);
        tvTitle.setTag(position);

        String imageUrl = getItem(position).getImageUrl();
        Picasso.with(getContext()).load(getItem(position).getImageUrl()).fit().into(recipeImage);

        String author = getItem(position).getAuthor();
        tvAuthor.setText(author);

        Picasso.with(getContext()).load(getItem(position).getAuthorImageUrl()).transform(new CircleTransform()).into(ivAuthorProfileImage);

        String recipeSummary = getItem(position).getRecipeSummary();
        if (recipeSummary.length() > 250) {
            recipeSummary = recipeSummary.substring(0, 200);
        }
        tvRecipeSummary.setText(recipeSummary);
        tvRecipeSummary.setEllipsize(TextUtils.TruncateAt.END);

        String cookingTime = Integer.toString(getItem(position).getCookingTime());
        etCookingTime.setText(cookingTime);

        Integer servings = getItem(position).getServings();
        tvRecipeServings.setText(servings.toString());

        Integer loves = 0;

        try {

            loves = getItem(position).getLovedBy().size();
        }  catch (NullPointerException ex) {
            // handle exception
        }

        tvRecipeLoves.setText(loves.toString());

        ///TODO
        List<String> tags = getItem(position).getTags();
        return convertView;
    }


    @OnClick(R.id.EditTextViewRecipe)
    public void onViewRecipeClick(View view) {
        Intent intent = new Intent(this.getContext(), ActivityRecipeDetails.class);
        Recipe recipe = getItem((Integer) view.getTag());
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        intent.putExtra("Recipe", recipeJson);
        getContext().startActivity(intent);
    }
}
