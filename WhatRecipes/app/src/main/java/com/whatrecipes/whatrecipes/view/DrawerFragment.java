package com.whatrecipes.whatrecipes.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;

public class DrawerFragment extends Fragment {
    AccountHeader accountHeader;
    Drawable profile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        Integer profile =  R.drawable.user_not_registered;
        setupHeader(profile,"Telerik Academy","Telerik@Telerik.com");
        setupDrawer(view);

        return view;
    }

    private void setupHeader(Integer profileImage, String accountName, String emailAddress){
         this.accountHeader = new AccountHeaderBuilder()
                .withActivity(this.getActivity())
                .withHeaderBackground(R.drawable.drawer_header_nutrional_dark)
                .addProfiles(
                        new ProfileDrawerItem().withName(accountName).withEmail(emailAddress).withIcon(profileImage)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    private void setupDrawer(View view) {


        //if you want to update the items at a later time it is recommended to keep it in a variable
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_add_recipe);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_discover_recipes);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.getBackground().setAlpha(90);




        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this.getActivity())
                .withToolbar(toolbar)
                .withAccountHeader(this.accountHeader)
                .addDrawerItems(
                        new DividerDrawerItem(),
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(position){
                            case 0:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(),new RecipesStackFragment(),R.id.cardStackFragment);
                                break;
                            case 1:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(),new RecipesStackFragment(),R.id.cardStackFragment);
                                break;
                            case 2:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(),new RecipesStackFragment(),R.id.cardStackFragment);
                                break;
                            case 3:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(),new RecipesStackFragment(),R.id.cardStackFragment);
                                break;
                            case 4:
                                ActivityUtils.replaceFragmentToActivity(getFragmentManager(),new RecipesStackFragment(),R.id.cardStackFragment);
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }
}
