package com.whatrecipes.whatrecipes.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fatal on 2/16/2017.
 */

public class RecipeViewUtils {
    public static List<View> findViewWithTagRecursively(ViewGroup root, Object tag){
        List<View> allViews = new ArrayList<View>();

        final int childCount = root.getChildCount();
        for(int i=0; i<childCount; i++){
            final View childView = root.getChildAt(i);

            if(childView instanceof ViewGroup){
                allViews.addAll(findViewWithTagRecursively((ViewGroup)childView, tag));
            }
            else{
                final Object tagView = childView.getTag();
                if(tagView != null && tagView.equals(tag))
                    allViews.add(childView);
            }
        }

        return allViews;
    }

    public static List<String> parseIngredientsByViews(List<View> view1){

        List<String> ingredientsName = new ArrayList<>();

        for (int i = 0; i< view1.size();i++){
            EditText v1 = (EditText)view1.get(i);

            ingredientsName.add(v1.getText().toString());
        }

        return ingredientsName;
    }

    public static Bitmap getEncodedImage(String bitmap) {
        byte[] decodedString = Base64.decode(bitmap, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String setEncodedImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return encodedImage;
    }
}
