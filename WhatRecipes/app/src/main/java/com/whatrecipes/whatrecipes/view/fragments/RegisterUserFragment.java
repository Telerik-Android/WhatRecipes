package com.whatrecipes.whatrecipes.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.presenters.RegisterUserPresenter;
import com.whatrecipes.whatrecipes.utils.CameraUtils;
import com.whatrecipes.whatrecipes.utils.ImageHelper;
import com.whatrecipes.whatrecipes.utils.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;


public class RegisterUserFragment extends Fragment implements IView.RegisterUserView {

    @BindView(R.id.editTextEmail)
    public EditText etEmail;

    @BindView(R.id.editTextPassword)
    public EditText etPassword;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.button_register)
    Button btnRegister;

    @BindView(R.id.button_cancel)
    Button btnCancel;

    @Inject
    RegisterUserPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_username, container, false);

        App.get().component().inject(this);
        presenter.setView(this);

        ButterKnife.bind(this, view);

        return view;
    }

    public void registerUser(String email, String password) {

        //checking if email and passwords are empty
        if (Validator.isValidEmail(email)) {
            Toast.makeText(getActivity(), "Please enter valid email", Toast.LENGTH_LONG).show();
            return;
        }
        if (Validator.stringEmptyOrNull(password)) {
            Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if(!Validator.isValidPasswordLength(password)){
            Toast.makeText(getActivity(), "Please enter longer password", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.registerUser(email, password);

    }


    @Override
    @OnClick(R.id.button_register)
    public void handleRegisterButtonClick() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        registerUser(email, password);
    }

    @Override
    @OnClick(R.id.button_cancel)
    public void handleCancelButtonClick() {
        finishActivity(RESULT_CANCELED);
    }

    @Override
    public void showProgressBar() {
        btnCancel.setEnabled(false);
        btnRegister.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        btnCancel.setEnabled(true);
        btnRegister.setEnabled(true);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showInvalidRegisterMessage() {
        Toast.makeText(getActivity(), "registered fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfulRegisterMessage() {
        Toast.makeText(getActivity(), "registered success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAllFieldsMustBeFilledMessage() {
        Toast.makeText(getActivity(), "all fileds must be filled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity(int result) {
        getActivity().setResult(result);
        getActivity().finish();
    }


}
