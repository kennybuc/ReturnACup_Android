package com.app.flexivendsymbol.fragments.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.app.flexivendsymbol.activities.base.BaseActivity;
import com.app.flexivendsymbol.helpers.LocalStorage;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;
    protected LocalStorage localStorage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        baseActivity = (BaseActivity) context;
        localStorage = LocalStorage.getInstance();
    }

    public boolean backButtonPressed() {
        return true;
    }

}