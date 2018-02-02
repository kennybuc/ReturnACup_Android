package com.app.flexivendsymbol.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.activities.CounterActivity;
import com.app.flexivendsymbol.api.domain.AppUser;
import com.app.flexivendsymbol.fragments.base.BaseFragment;
import com.app.flexivendsymbol.helpers.UIUtils;
import com.app.flexivendsymbol.helpers.ValidationUtils;

public class SignupFragment extends BaseFragment {

    private EditText etEmailAddress;
    private EditText etCompanyName;
    private EditText etPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Map view elements to class members.
        etEmailAddress = view.findViewById(R.id.etEmailAddress);
        etCompanyName = view.findViewById(R.id.etCompanyName);
        etPassword = view.findViewById(R.id.etPassword);

        // Wrap event handlers to view elements.
        view.findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequest();
            }
        });
    }

    private void checkAndRequest() {
        String email = etEmailAddress.getText().toString();
        String companyName = etCompanyName.getText().toString();
        String password = etPassword.getText().toString();

        if (!ValidationUtils.validateEmail(email)) {
            UIUtils.shakeForError(etEmailAddress);
        } else if (companyName.trim().length() == 0) {
            UIUtils.shakeForError(etCompanyName);
        } else if (password.length() == 0) {
            UIUtils.shakeForError(etPassword);
        } else {
            requestSignup();
        }
    }

    private void requestSignup() {
        // Request signup and handle response.

        /* Save user info locally for now */
        AppUser user = new AppUser();
        String email = etEmailAddress.getText().toString();
        String companyName = etCompanyName.getText().toString();
        String password = etPassword.getText().toString();
        user.email = email;
        user.company_name = companyName;
        user.password = password;
        user.saveToStorage();

        // Navigate to the counter page after success login.
        startActivity(new Intent(baseActivity, CounterActivity.class));
        baseActivity.finish();
    }

}
