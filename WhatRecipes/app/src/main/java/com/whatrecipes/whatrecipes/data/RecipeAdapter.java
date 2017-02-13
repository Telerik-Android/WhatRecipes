package com.whatrecipes.whatrecipes.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.whatrecipes.whatrecipes.R;

import static android.R.attr.resource;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeAdapter extends ArrayAdapter<String> {


    public RecipeAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TextView v = (TextView)(convertView.findViewById(R.id.recipeTextTitle));
        TextView v2 = (TextView)(convertView.findViewById(R.id.recipeTextSummary));
        v.setText(getItem(position));
        v2.setText("MNOGOOGOGOG TEXT");
        Button btn = (Button)convertView.findViewById(R.id.button);
        return convertView;
    }
}
