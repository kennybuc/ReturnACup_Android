package com.app.flexivendsymbol.helpers;

public class AppUtils {

    private static final String KEY_COUNTER_NUMBER = "KEY_COUNTER_NUMBER";

    public static int getCounterNumber() {
        return LocalStorage.getInstance().getIntPreference(KEY_COUNTER_NUMBER);
    }

    public static void incrementCounterNumber() {
        int num = getCounterNumber();
        LocalStorage.getInstance().put(KEY_COUNTER_NUMBER, num + 1);
    }
}
