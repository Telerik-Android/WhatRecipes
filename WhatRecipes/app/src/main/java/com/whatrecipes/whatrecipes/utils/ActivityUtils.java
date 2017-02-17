package com.whatrecipes.whatrecipes.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * Replace current fragment with another, with backtracking between them
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void replaceFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment, int frameId) {
        Fragment fragmentToBacktrack  = fragmentManager.findFragmentById(frameId);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(fragmentToBacktrack!=null)
            transaction.addToBackStack(fragmentToBacktrack.toString());

        transaction.replace(frameId, fragment);
        transaction.commit();
    }
}
