package com.whatrecipes.whatrecipes.view;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.R;

import javax.inject.Inject;


public class BlankFragment extends Fragment implements com.whatrecipes.whatrecipes.view.View {
    Button sendBtn;
    EditText editText;
    ListView listView;

    @Inject Presenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_blank, container, false);
        App.get().getAppComponent().inject(this);

         sendBtn = (Button) view.findViewById(R.id.button);
        editText = (EditText) view.findViewById(R.id.messageText);
         listView = (ListView) view.findViewById(R.id.messagesList);
        presenter.setView(this);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message("ref1",editText.getText().toString());

                presenter.AddMessage(msg);
                editText.setTag("");
            }
        });

    return view;
    }

    @Override
    public void addMessage(Message msg) {


    }
}
