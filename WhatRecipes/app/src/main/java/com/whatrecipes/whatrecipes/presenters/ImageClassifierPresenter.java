package com.whatrecipes.whatrecipes.presenters;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.ai.Classifier;
import com.whatrecipes.whatrecipes.data.ai.TensorFlowImageClassifier;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

public class ImageClassifierPresenter
        implements IPresenter.ImageClassifierPresenter, ValueEventListener {

    private IView.ImageClassifierView mView;
    private final IFirebaseDatabaseInteractor db;
    private Classifier mClassifier;

    private AtomicBoolean mReady;

    private static final int NUM_CLASSES = 1008;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    private static final int INPUT_SIZE = 299;
    private static final String MODEL_FILE =
            "file:///android_asset/banitsa.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/banitsa.txt";

    @Inject
    public ImageClassifierPresenter(IFirebaseDatabaseInteractor db) {
        this.mReady = new AtomicBoolean();
        this.db = db;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<DataSnapshot> snaps = Lists.newArrayList(dataSnapshot.getChildren());
        DataSnapshot recipe = snaps.get(0);
        Recipe model = recipe.getValue(Recipe.class);
        mView.openRecipeDetails(model);
    }

    @Override
    public void initClassifier(AssetManager assetManager, Context ctx) {
        try {
            if (this.mClassifier == null) {
                this.mClassifier = new TensorFlowImageClassifier(
                        assetManager,
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME
                );
            }

        } catch (IOException e) {
            // handle it, or not XD
        }
        setReady(true);
    }

    @Override
    public void classifyImage(Bitmap bmp) {
        Bitmap rescaled = Bitmap.createScaledBitmap(
                bmp,
                INPUT_SIZE,
                INPUT_SIZE,
                true);

        final List<Classifier.Recognition> results = mClassifier.recognizeImage(rescaled);

        setReady(true);
        String firstResultString = results.get(0).toString();
        String firstResultTitle = results.get(0).getTitle();
        this.mView.setClassifiedImageData(rescaled, firstResultString);

        this.db.getRecipeNameBeginsWith(this, firstResultTitle);
    }

    public void unloadClassifier() {
        this.setReady(false);
        try {
            if (this.mClassifier != null) {
                this.mClassifier.close();
            }
        } catch (Throwable t) {
            // close quietly
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        return;
    }

    @Override
    public void setView(IView.ImageClassifierView view) {
        this.mView = view;
    }

    @Override
    public void setReady(boolean ready) {
        mReady.set(ready);
    }

    @Override
    public boolean getReady() {
        return this.mReady.get();
    }
}
