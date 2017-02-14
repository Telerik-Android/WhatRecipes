package com.whatrecipes.whatrecipes.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.Facing;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.DaggerAppComponent;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.ViewModule;
import com.whatrecipes.whatrecipes.presenters.AddNewRecipePresenter;
import com.whatrecipes.whatrecipes.view.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class AddNewRecipeFragment extends Fragment implements IView.AddNewRecipeView {

    private ImageView imageView;
    private Uri file;

    @BindView(R.id.camera_button)
    Button cameraButton;

    private File testRoot;
    private static final int REQUEST_PORTRAIT_RFC=1337;
    private static final int REQUEST_PORTRAIT_FFC=REQUEST_PORTRAIT_RFC+1;

    @Inject
    AddNewRecipePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_recipe,container,false);


            //LinearLayout ll = (LinearLayout)view.findViewById(R.id.ingredient_framelayout);
            //ll.addView(layout);
        DaggerAppComponent.builder()
                .viewModule(new ViewModule(this))
                .build()
                .inject(this);

//
//        App.get()
//                .getAppComponent().inject(this);

        ButterKnife.bind(this,view);
        imageView=(ImageView) view.findViewById(R.id.image_view);

        return view;
    }

    @OnClick(R.id.camera_button)
    public void takeCameraPhoto(){
        String filename="cam2_"+ Build.MANUFACTURER+"_"+Build.PRODUCT
                +"_"+new SimpleDateFormat("yyyyMMdd'-'HHmmss").format(new Date());
        testRoot=new File(getActivity().getExternalFilesDir(null), filename);


        Intent i=new CameraActivity.IntentBuilder(getActivity())
                .skipConfirm()
                .facing(Facing.FRONT)
                .to(new File(testRoot, getString(R.string.screenshot)))
                .debug()
                .zoomStyle(ZoomStyle.SEEKBAR)
                .updateMediaStore()
                .build();

        startActivityForResult(i, REQUEST_PORTRAIT_FFC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(testRoot.getAbsolutePath()+getString(R.string.screenshot),options);


        //Preview photo
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void showAddRecipeForm() {

    }

    @Override
    public LinearLayout addIngridientFormView() {
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.weight=1;
        LinearLayout.LayoutParams layoutParams3= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams3.weight=3;

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(layoutParams);

        LinearLayout layoutChild1 = new LinearLayout(getContext());
        layoutChild1.setOrientation(LinearLayout.VERTICAL);
        layoutChild1.setLayoutParams(layoutParams2);

        LinearLayout layoutChild2 = new LinearLayout(getContext());
        layoutChild2.setOrientation(LinearLayout.VERTICAL);
        layoutChild2.setLayoutParams(layoutParams3);

        TextView tv1 = new TextView(getContext());
        tv1.setText("Ingredient:");

        EditText ingredientEditText = new EditText(getContext());
        ingredientEditText.setLayoutParams(layoutParams);
        ingredientEditText.setHint("Ingredient");

        TextView tv2 = new TextView(getContext());
        tv2.setText("Quantity:");

        EditText ingredientEditTextQuantity = new EditText(getContext());
        ingredientEditTextQuantity.setLayoutParams(layoutParams);
        ingredientEditTextQuantity.setHint("Quantity");

        layoutChild1.addView(tv1);
        layoutChild1.addView(ingredientEditText);
        layoutChild2.addView(tv2);
        layoutChild2.addView(ingredientEditTextQuantity);

        layout.addView(layoutChild1);
        layout.addView(layoutChild2);

        return layout;
    }

}
