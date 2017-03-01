package com.whatrecipes.whatrecipes.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatrecipes.whatrecipes.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fatal on 1.3.2017 г..
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<String> ingredientsName;
    private List<String> ingredientsQuantity;


    private final Context context;

    @BindView(R.id.text_view_ingredient_name)
    TextView tvIngredientName;

    public IngredientsAdapter(Context context, List<String> ingredientsName, List<String> ingredientsQuantity) {
        this.ingredientsName = ingredientsName;
        this.ingredientsQuantity = ingredientsQuantity;
        this.context = context;
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ButterKnife.bind(this, parent);

        return null;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, int position) {

        holder.tvIngredientName.setText(ingredientsName.get(position));
        holder.tvIngredientQuality.setText(ingredientsQuantity.get(position));

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIngredientName;
        public TextView tvIngredientQuality;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
