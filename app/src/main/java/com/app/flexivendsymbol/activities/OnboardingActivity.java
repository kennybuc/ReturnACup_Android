package com.app.flexivendsymbol.activities;

import android.os.Bundle;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.activities.base.BaseActivity;
import com.app.flexivendsymbol.fragments.StartupFragment;

public class OnboardingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        replaceFragment(new StartupFragment());
    }

    @Override
    protected int getContentFrame() {
        return R.id.fragment_container;
    }
}
