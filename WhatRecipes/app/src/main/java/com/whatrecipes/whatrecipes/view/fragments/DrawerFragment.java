package com.whatrecipes.whatrecipes.view.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.view.ActivityLogIn;
import com.whatrecipes.whatrecipes.view.ActivityRegister;

import javax.inject.Inject;

public class DrawerFragment extends Fragment {
    private static final int RC_SIGN_OUT = 121;
    private static final int RC_SIGN_UP = 123;
    private static final int RC_SIGN_IN = 122;

    AccountHeader accountHeader;
    Drawable profile;
    Drawer drawer;
    Toolbar mToolbar;

    @Inject
    FirebaseAuthenticationInteractor firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        App.get().component().inject(this);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Integer profile1 = R.drawable.user_not_registered;

        if (firebaseAuth.getLoggedInUserDisplayName() != null) {
            setupHeader(firebaseAuth.getLoggedInUserDisplayName(), firebaseAuth.getLoggedInUserEmail());
            setupDrawer(view);
        } else {

            setupHeader("not logged in", "not logged in");
            setupDrawer(view);
        }

        return view;
    }

    private void setupHeader(final String name, final String emailAddress) {
        this.accountHeader = new AccountHeaderBuilder()
                .withSelectionListEnabled(false)
                .withActivity(this.getActivity())
                .withHeaderBackground(R.drawable.drawer_header_nutrional_dark)
                .addProfiles(
                        new ProfileDrawerItem().withEmail(emailAddress).withName(name)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        if (firebaseAuth.getLoggedInUserDisplayName() != null) {
                            // setupHeader(R.drawable.user_not_registered,firebaseAuth);
                            //  setupDrawer(getView());
                            Toast.makeText(getActivity(), "hl " + firebaseAuth.getLoggedInUserDisplayName(), Toast.LENGTH_SHORT).show();
                        } else {
                            //  setupHeader("not logged in","not logged in email");
                            //  setupDrawer(getView());
                            Toast.makeText(getActivity(), "not logged in", Toast.LENGTH_SHORT).show();

                        }
                        return false;
                    }
                })
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        if (firebaseAuth.getLoggedInUserDisplayName() != null) {
                            // setupHeader(R.drawable.user_not_registered,firebaseAuth);
                            //  setupDrawer(getView());
                            Toast.makeText(getActivity(), "il " + firebaseAuth.getLoggedInUserDisplayName(), Toast.LENGTH_SHORT).show();
                        } else {
                            //  setupHeader("not logged in","not logged in email");
                            //  setupDrawer(getView());
                            Toast.makeText(getActivity(), "not logged in", Toast.LENGTH_SHORT).show();

                        }

                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();
    }

    private void setupDrawer(View view) {


        //if you want to update the items at a later time it is recommended to keep it in a variable
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_add_recipe);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_discover_recipes);
        SecondaryDrawerItem signOut = new SecondaryDrawerItem().withIdentifier(4).withName("Sign out");
        SecondaryDrawerItem signUp = new SecondaryDrawerItem().withIdentifier(5).withName("Sign up");
        SecondaryDrawerItem signIn = new SecondaryDrawerItem().withIdentifier(6).withName("Sign in");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //create the drawer and remember the `Drawer` result object
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this.getActivity())
                .withToolbar(toolbar)
                .withAccountHeader(this.accountHeader)
                .addDrawerItems(new DividerDrawerItem(), item2, item3)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 2:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(), new AddNewRecipeFragment(), R.id.cardStackFragment);
                                break;
                            case 3:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(), new RecipesStackFragment(), R.id.cardStackFragment);
                                break;
                            case 4:
                                firebaseAuth.logTheUserOut();
                                setupHeader("not logged in", "");
                                setupDrawer(getView());
                                break;
                            case 5:
                                startActivityForResult(new Intent(getActivity(), ActivityRegister.class)
                                        , RC_SIGN_UP);
                                break;
                            case 6:
                                startActivityForResult(new Intent(getActivity(), ActivityLogIn.class)
                                        , RC_SIGN_IN);
                                break;
                        }
                        return false;
                    }
                });

        if (firebaseAuth.getLoggedInUserDisplayName() != null) {
            drawerBuilder.addDrawerItems(signOut);
        } else {
            drawerBuilder.addDrawerItems(signIn,signUp);
        }

        drawer = drawerBuilder.build();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_SIGN_IN:
                setupHeader(firebaseAuth.getLoggedInUserDisplayName(), firebaseAuth.getLoggedInUserEmail());
                setupDrawer(getView());
                break;
            case RC_SIGN_UP:
                setupHeader(firebaseAuth.getLoggedInUserDisplayName(), firebaseAuth.getLoggedInUserEmail());
                setupDrawer(getView());
                break;
        }
    }
}
