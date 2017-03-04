package com.whatrecipes.whatrecipes.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.view.fragments.DrawerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;

    DrawerFragment drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerFragment = new DrawerFragment();

        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), drawerFragment, R.id.drawerFragment);


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {

            final AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("Are you sure you want to exit?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });



            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
