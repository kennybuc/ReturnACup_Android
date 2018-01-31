package com.app.flexivendsymbol.helpers;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.app.flexivendsymbol.R;

public class UIUtils {

    public static void showMessage(Context context, int resId) {
        try {
            showMessage(context, context.getString(resId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Shake edit text to represent input error.
     *
     * @param view View object to shake.
     */
    public static void shakeForError(View view) {
        Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake_horizontal);
        view.startAnimation(shake);
    }

    /**
     * Show simple ok alert from context.
     *
     * @param context Context object where to show alert.
     * @param title   Title of the alert.
     * @param message Content of the alert.
     */
    public static void showSimpleAlertWith(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("OK", null)
                .show();
    }

}
