package com.yrx.commontool.screen;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenInfo 
{
	public static int screenWidthPx;  //屏幕宽度像素
	public static int screenHeightPx;
	
	
	/**
	 * 屏幕宽度像素
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int srceenWidth = dm.widthPixels;
        screenWidthPx = srceenWidth;
		return srceenWidth;
	}

	/**
	 * 屏幕高度像素
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int srceenHeight = dm.heightPixels;
        screenHeightPx = srceenHeight;
		return srceenHeight;
	}
	
	public static void loadScreenInfo(Context context)
	{	
		getScreenWidth(context);
		getScreenHeight(context);
	}
}
