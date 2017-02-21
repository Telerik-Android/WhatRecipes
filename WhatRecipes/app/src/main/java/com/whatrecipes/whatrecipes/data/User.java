package com.whatrecipes.whatrecipes.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.whatrecipes.whatrecipes.utils.Validator;

/**
 * Created by fatal on 2/19/2017.
 */

public class User implements IProfile<User> {
    private String userDisplayName;

    public User() {
    }

    public User(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }


    protected boolean validateModel() {
        return !Validator.stringEmptyOrNull(userDisplayName);
    }

    @Override
    public User withName(String name) {
        return null;
    }

    @Override
    public StringHolder getName() {
        return null;
    }

    @Override
    public User withEmail(String email) {
        return null;
    }

    @Override
    public StringHolder getEmail() {
        return null;
    }

    @Override
    public User withIcon(Drawable icon) {
        return null;
    }

    @Override
    public User withIcon(Bitmap bitmap) {
        return null;
    }

    @Override
    public User withIcon(@DrawableRes int iconRes) {
        return null;
    }

    @Override
    public User withIcon(String url) {
        return null;
    }

    @Override
    public User withIcon(Uri uri) {
        return null;
    }

    @Override
    public User withIcon(IIcon icon) {
        return null;
    }

    @Override
    public ImageHolder getIcon() {
        return null;
    }

    @Override
    public User withSelectable(boolean selectable) {
        return null;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public User withIdentifier(long identifier) {
        return null;
    }

    @Override
    public long getIdentifier() {
        return 0;
    }
}
