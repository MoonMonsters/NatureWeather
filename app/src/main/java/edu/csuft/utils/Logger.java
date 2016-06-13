package edu.csuft.utils;

import android.util.Log;

/**
 * Created by Chalmers on 2016-05-29 12:44.
 * email:qxinhai@yeah.net
 */
public class Logger {
    private static boolean flag = false;

    public static void i(String tag, String msg) {
        if (flag) {
            Log.i(tag, msg);
        }
    }
}
