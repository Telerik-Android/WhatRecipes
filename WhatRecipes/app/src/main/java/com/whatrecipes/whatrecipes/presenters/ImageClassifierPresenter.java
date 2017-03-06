package com.whatrecipes.whatrecipes.presenters;


import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

public class ImageClassifierPresenter
        implements IPresenter.ImageClassifierPresenter, ValueEventListener {

    private IView.ImageClassifierView mView;
    private final IFirebaseDatabaseInteractor db;

    @Inject
    public ImageClassifierPresenter(IFirebaseDatabaseInteractor db) {
        this.db = db;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<DataSnapshot> snaps = Lists.newArrayList(dataSnapshot.getChildren());
        mView.setClassificationName((String)snaps.get(0).child("name").getValue());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        return;
    }

    @Override
    public void getRecipeWithName(String name) {
        this.db.getRecipeNameBeginsWith(this, name);
    }

    @Override
    public void setView(IView.ImageClassifierView view) {
        this.mView = view;
    }
}
