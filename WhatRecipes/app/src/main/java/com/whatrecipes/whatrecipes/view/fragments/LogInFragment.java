package com.whatrecipes.whatrecipes.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.presenters.LogInPresenter;
import com.whatrecipes.whatrecipes.presenters.RegisterUserPresenter;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fatal on 2/21/2017.
 */

public class LogInFragment extends Fragment implements IView.LogInUserView {

    @BindView(R.id.editTextEmail)
    public EditText etEmail;

    @BindView(R.id.editTextPassword)
    public EditText etPassword;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.button_cancel)
    Button btnCancel;

    @BindView(R.id.button_log_in)
    Button btnLogIn;



    @Inject
    LogInPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        App.get().component().inject(this);

        presenter.setView(this);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void LogInUser() {
        presenter.logInUser(etEmail.getText().toString(), etPassword.getText().toString());
    }


    @OnClick(R.id.button_log_in)
    @Override
    public void handleLogInButtonClick() {
        LogInUser();
    }

    @OnClick(R.id.button_cancel)
    @Override
    public void handleCancelButtonClick() {
        getActivity().finish();
    }

    @Override
    public void showProgressBar() {
        btnCancel.setEnabled(false);
        btnLogIn.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        btnCancel.setEnabled(true);
        btnLogIn.setEnabled(true);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showInvalidLogInMessage() {
        Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfulLogInMessage() {
        Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAllFieldsMustBeFilledMessage() {
        Toast.makeText(getActivity(), "all fileds must be filled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}
