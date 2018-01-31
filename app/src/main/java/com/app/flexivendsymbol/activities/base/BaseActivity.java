package com.app.flexivendsymbol.activities.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.fragments.base.BaseFragment;
import com.app.flexivendsymbol.helpers.LocalStorage;
import com.app.flexivendsymbol.helpers.UIUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.UUID;

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseFragment rootFragment;
    protected BaseFragment activeFragment;

    protected LocalStorage localStorage;

    private KProgressHUD progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = LocalStorage.getInstance();
    }

    public void showProgress() {
        progress = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    public enum TransactionAnimation {
        FADE_IN, SLIDE_FROM_LEFT, SLIDE_FROM_RIGHT, SLIDE_FROM_TOP, SLIDE_FROM_BOTTOM
    }

    protected int getContentFrame() {
        return 0;
    }

    private TransactionAnimation defaultAnimation = TransactionAnimation.SLIDE_FROM_RIGHT;

    public void setCustomAnimation(TransactionAnimation defaultAnimation) {
        this.defaultAnimation = defaultAnimation;
    }

    public void addFragment(BaseFragment fragment) {
        addFragment(fragment, false);
    }

    public void addFragment(BaseFragment fragment, boolean addToBackStack) {
        addOrReplaceFragment(fragment, addToBackStack, true);
    }

    public void replaceFragment(BaseFragment fragment) {
        replaceFragment(fragment, false);
    }

    public void replaceFragment(BaseFragment fragment, boolean addToBackStack) {
        addOrReplaceFragment(fragment, addToBackStack, false);
    }

    public void addOrReplaceFragment(BaseFragment fragment, boolean addToBackStack, boolean add) {
        this.activeFragment = fragment;
        String tag = UUID.randomUUID().toString();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            switch (defaultAnimation) {
                case FADE_IN:
                    transaction.setCustomAnimations(R.anim.anim_fade_in,
                            R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    break;
                case SLIDE_FROM_LEFT:
                    transaction.setCustomAnimations(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_right, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    break;
                case SLIDE_FROM_RIGHT:
                    transaction.setCustomAnimations(R.anim.anim_slide_in_right,
                            R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                    break;
                case SLIDE_FROM_BOTTOM:
                    transaction.setCustomAnimations(R.anim.anim_slide_in_bottom,
                            R.anim.anim_slide_out_top, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    break;
                case SLIDE_FROM_TOP:
                    transaction.setCustomAnimations(R.anim.anim_slide_in_top,
                            R.anim.anim_slide_out_bottom, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    break;
                default:
                    transaction.setCustomAnimations(R.anim.anim_fade_in,
                            R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    break;
            }
        }

        if (add) {
            transaction.add(getContentFrame(), fragment, tag);
        } else {
            transaction.replace(getContentFrame(), fragment, tag);
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            rootFragment = fragment;
    }

    /**
     * Enables the cleanup of all stack before adding this fragment. Can be
     * useful to make the Dash-board or other fragment the base fragment in
     * terms of order
     *
     * @param contentFrame   Layout Resource ID to replace fragment
     * @param fragment       BaseFragment object to replace
     * @param addToBackStack Determines to add to fragment back-stack
     * @param remove         If true, cleanup all stack
     */
    public void replaceFragment(int contentFrame, BaseFragment fragment,
                                boolean addToBackStack, boolean remove) {

        if (remove) {
            this.popToRoot();
        }
        replaceFragment(fragment, addToBackStack);
    }

    public void popToRoot() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = getSupportFragmentManager().getBackStackEntryAt(i).getId();
            getSupportFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        activeFragment = null;
    }

    @Override
    public void onBackPressed() {
        if (activeFragment != null) {
            if (activeFragment.backButtonPressed()) {
                super.onBackPressed();
                int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackCount > 0) {
                    String tag = getSupportFragmentManager()
                            .getBackStackEntryAt(backStackCount - 1).getName();
                    activeFragment = (BaseFragment) getSupportFragmentManager()
                            .findFragmentByTag(tag);
                } else {
                    if (activeFragment.equals(rootFragment))
                        activeFragment = null;
                    else
                        activeFragment = rootFragment;
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    public void showToast(int resId) {
        UIUtils.showMessage(this, resId);
    }

    public void showToast(String message) {
        UIUtils.showMessage(this, message);
    }

    public void showSimpleAlertWith(String title, String message) {
        UIUtils.showSimpleAlertWith(this, title, message);
    }

    public void dismissKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
}
