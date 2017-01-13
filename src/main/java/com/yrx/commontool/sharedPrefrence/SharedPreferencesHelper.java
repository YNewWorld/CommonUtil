package com.yrx.commontool.sharedPrefrence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Yrx
 *         Created on 2016/9/26.
 *         描述：
 */

public class SharedPreferencesHelper {

    public static void saveSharedPreferences(Context mContext, String pName, String pValue) {
        SharedPreferences sp = mContext.getSharedPreferences("test",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(pName,pValue);
        editor.commit();
    }

    public static String readSharedPreferences(Context mContext,String pName) {
        SharedPreferences sp = mContext.getSharedPreferences("test",Activity.MODE_PRIVATE);
        return sp.getString(pName,"default");
    }

}
