package com.whatrecipes.whatrecipes.view.fragments;

import android.Manifest;
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
import android.widget.ImageView;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.Facing;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.presenters.AddNewRecipePresenter;
import com.whatrecipes.whatrecipes.view.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
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
    private Button cameraButton;
    private File testRoot;
    private static final int REQUEST_PORTRAIT_RFC=1337;
    private static final int REQUEST_PORTRAIT_FFC=REQUEST_PORTRAIT_RFC+1;

    @Inject
    AddNewRecipePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_recipe,container,false);

        App.get().getAppComponent().inject(this);
        ButterKnife.bind(getActivity());

        imageView=(ImageView) view.findViewById(R.id.image_view);
        String filename="cam2_"+ Build.MANUFACTURER+"_"+Build.PRODUCT
                +"_"+new SimpleDateFormat("yyyyMMdd'-'HHmmss").format(new Date());
        testRoot=new File(getActivity().getExternalFilesDir(null), filename);

        cameraButton = (Button) view.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i=new CameraActivity.IntentBuilder(getActivity())
                        .skipConfirm()
                        .facing(Facing.FRONT)
                        .to(new File(testRoot, "portrait-front.jpg"))
                        .debug()
                        .zoomStyle(ZoomStyle.SEEKBAR)
                        .updateMediaStore()
                        .build();

                startActivityForResult(i, REQUEST_PORTRAIT_FFC);



            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(testRoot.getAbsolutePath()+"/portrait-front.jpg",options);

        imageView.setImageBitmap(bitmap);
        Integer a = 5;
    }

    @Override
    public void showAddRecipeForm() {

    }

}
