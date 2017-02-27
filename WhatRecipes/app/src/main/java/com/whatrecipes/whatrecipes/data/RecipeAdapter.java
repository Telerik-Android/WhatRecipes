package com.whatrecipes.whatrecipes.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whatrecipes.whatrecipes.R;

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

    @BindView(R.id.EditTextRecipeCookingTime)
    TextView etCookingTime;

    public RecipeAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButterKnife.bind(this, convertView);

        String text = getItem(position).getName();
        tvTitle.setText(text);

        Bitmap bitmap = getItem(position).getBitmap();
        recipeImage.setImageBitmap(bitmap);
        String author = getItem(position).getAuthor();
        String recipeSummary = getItem(position).getRecipeSummary();


        String cookingTime = Integer.toString(getItem(position).getCookingTime());
        etCookingTime.setText(cookingTime);
        Integer servings = getItem(position).getServings();
        List<String> tags = getItem(position).getTags();
        return convertView;
    }

    @OnClick(R.id.EditTextViewRecipe)
    public void onViewRecipeClick(){
        Toast.makeText(getContext(), "View details", Toast.LENGTH_SHORT).show();
    }
}
