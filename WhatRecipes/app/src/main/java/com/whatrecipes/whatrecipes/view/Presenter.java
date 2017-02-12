package com.whatrecipes.whatrecipes.view;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

/**
 * Created by dnt on 12.2.2017 г..
 */

public class Presenter {
    View view;
    FirebaseDatabase database;

    @Inject
    public Presenter(FirebaseDatabase database){
        this.database=database;
    }

    void setView(View view){
        this.view = view;
    }

    void AddMessage(Message msg){
        database.getReference("messages").push().setValue(msg);
    }
}
