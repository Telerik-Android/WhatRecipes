package com.whatrecipes.whatrecipes.utils;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayOutputStream;

/**
 * Created by fatal on 2/19/2017.
 */

public class ImageHelper {
    public static byte[] getImageByteArray(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public static Uri getImageURIFromString(String imageURI) {
        return Uri.parse(imageURI);
    }
}