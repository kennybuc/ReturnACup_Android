package com.app.flexivendsymbol.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.fragments.base.BaseFragment;

public class StartupFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_startup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Wrap event handlers to view elements.
        view.findViewById(R.id.btnLogin).setOnClickListener(this);
        view.findViewById(R.id.btnSignup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            baseActivity.replaceFragment(new LoginFragment(), true);
        } else if (v.getId() == R.id.btnSignup) {
            baseActivity.replaceFragment(new SignupFragment(), true);
        }
    }

}
