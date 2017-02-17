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

import com.whatrecipes.whatrecipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    @BindView(R.id.recipeImageView)
    ImageView recipeImage;

    @BindView(R.id.EditTextRecipeTextTitle)
    TextView tvTitle;


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
        return convertView;
    }
}
