package com.app.flexivendsymbol.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.activities.base.BaseActivity;
import com.app.flexivendsymbol.api.domain.AppUser;
import com.app.flexivendsymbol.helpers.AppUtils;
import com.app.flexivendsymbol.helpers.UIUtils;

public class CounterActivity extends BaseActivity {

    private TextView tvCounterNumber;
    private SlidingPaneLayout slidingPaneLayout;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        // Map view elements to class members.
        tvCounterNumber = findViewById(R.id.tvCounterNumber);
        slidingPaneLayout = findViewById(R.id.slidingPaneLayout);
        etPassword = findViewById(R.id.etPassword);

        AppUser user = AppUser.getSavedUser();
        EditText etEmailAddress = findViewById(R.id.etEmailAddress);
        if (user != null) {
            etEmailAddress.setText(user.email);
        } else {
            etEmailAddress.setText("Not logged in");
        }

        // Wrap event handlers to view elements.
        findViewById(R.id.btnStartScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounterActivity.this, ScanningActivity.class));
            }
        });
        findViewById(R.id.btnShowLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingPaneLayout.openPane();
            }
        });
        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLogout();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvCounterNumber.setText(String.valueOf(AppUtils.getCounterNumber()));
    }

    private void checkAndLogout() {
        String password = etPassword.getText().toString();
        if (password.length() == 0) {
            UIUtils.shakeForError(etPassword);
        } else {
            AppUser user = AppUser.getSavedUser();
            if (user == null) {
                performLogout();
            } else {
                if (password.equals(user.password)) {
                    performLogout();
                } else {
                    UIUtils.showMessage(this, "Incorrect password");
                    UIUtils.shakeForError(etPassword);
                }
            }
        }
    }

    private void performLogout() {
        AppUser.logoutUser();
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }

}
