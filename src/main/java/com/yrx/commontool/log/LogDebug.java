package com.yrx.commontool.log;


import android.util.Log;

import com.yrx.commontool.BuildConfig;


public class LogDebug {
	private static final String TEST_TAG = "LogDebug";
	
	/**
	 * 绿色
	 * @param msg
	 */
	public static void i(String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(TEST_TAG, msg);
		}
	}
	
	/**
	 * 黑色
	 * @param msg
	 */
	public static void v(String msg) {
		if (BuildConfig.DEBUG) {
			Log.v(TEST_TAG, msg);
		}
	}
	
	/**
	 * 红色
	 * @param msg
	 */
	public static void e(String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(TEST_TAG, msg);
		}
	}
	
	/**
	 * 蓝色
	 * @param msg
	 */
	public static void d(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(TEST_TAG, msg);
		}
	}
	
	/**
	 * 橙色
	 * @param msg
	 */
	public static void w(String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(TEST_TAG, msg);
		}
	}
	
	public static void i(String tag,String msg) {
//		if (BuildConfig.DEBUG) {
			Log.i(tag, msg);
//		}
	}
	
	public static void v(String tag,String msg) {
//		if (BuildConfig.DEBUG) {
			Log.v(tag, msg);
//		}
	}
	
	public static void e(String tag,String msg) {
//		if (BuildConfig.DEBUG) {
			Log.e(tag, msg);
//		}
	}
	
	public static void d(String tag,String msg) {
//		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
//		}
	}
}
