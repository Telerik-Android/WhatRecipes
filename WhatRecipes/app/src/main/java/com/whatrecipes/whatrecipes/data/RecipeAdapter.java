package com.whatrecipes.whatrecipes.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.CircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    public RecipeAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButterKnife.bind(this, convertView);

        String text = getItem(position).getName();
        tvTitle.setText(text);

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

        Integer loves = getItem(position).getLoves();
        if (loves == null)
            loves = 0;

        tvRecipeLoves.setText(loves.toString());

        ///TODO
        List<String> tags = getItem(position).getTags();
        return convertView;
    }

    @OnClick(R.id.EditTextViewRecipe)
    public void onViewRecipeClick() {
        Toast.makeText(getContext(), "View details", Toast.LENGTH_SHORT).show();
    }
}
