package com.whatrecipes.whatrecipes.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.Facing;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.view.fragments.AddNewRecipeFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.Settings.Global.getString;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class CameraUtils {
    private static final int REQUEST_PORTRAIT_RFC = 1337;
    private static final int REQUEST_PORTRAIT_FFC = REQUEST_PORTRAIT_RFC + 1;
    public static void takeRecipeCameraPhoto(AddNewRecipeFragment fragment){

        String filename = "cam2_" + Build.MANUFACTURER + "_" + Build.PRODUCT
                + "_" + new SimpleDateFormat("yyyyMMdd'-'HHmmss").format(new Date());
        File testRoot = new File(fragment.getActivity().getExternalFilesDir(null), filename);


        Intent i = new CameraActivity.IntentBuilder(fragment.getContext())
                .requestPermissions()
                .skipConfirm()
                .facing(Facing.FRONT)
                .to(new File(testRoot, fragment.getString(R.string.screenshot)))
                .debug()
                .zoomStyle(ZoomStyle.SEEKBAR)
                .updateMediaStore()
                .build();
        i.putExtra(fragment.getString(R.string.PhotoPath),testRoot.getAbsolutePath());

        fragment.startActivityForResult(i, REQUEST_PORTRAIT_FFC);
    }

    public static void takeUserProfileCameraPhoto(AddNewRecipeFragment fragment){

        String filename = "cam2_" + Build.MANUFACTURER + "_" + Build.PRODUCT
                + "_" + new SimpleDateFormat("yyyyMMdd'-'HHmmss").format(new Date());
        File testRoot = new File(fragment.getActivity().getExternalFilesDir(null), filename);


        Intent i = new CameraActivity.IntentBuilder(fragment.getContext())
                .requestPermissions()
                .skipConfirm()
                .facing(Facing.FRONT)
                .to(new File(testRoot, fragment.getString(R.string.screenshot)))
                .debug()
                .zoomStyle(ZoomStyle.SEEKBAR)
                .updateMediaStore()
                .build();
        i.putExtra(fragment.getString(R.string.PhotoPath),testRoot.getAbsolutePath());

        fragment.startActivityForResult(i, REQUEST_PORTRAIT_FFC);
    }
}
