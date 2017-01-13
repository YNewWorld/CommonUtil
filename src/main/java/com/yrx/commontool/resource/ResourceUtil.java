package com.yrx.commontool.resource;

import android.content.Context;

/**
 * 
 * @author lenovo123 
 * 
 * 我们希望将我们的Android工程提供给第三方开发者使用。这个时候，最普遍的做法，就是提供一个jar包和一堆资源文件，
 * 第三方开发者可以将资源文件拷贝到Android工程的相应目录下，同时引用我们提供的jar包，就可以使用我们提供的相应API了。
 * 需要特别注意的是
 * ，以jar包和资源包方式提供给第三方开发者，我们的工程的代码中就不能使用类似于R.layout.main、R.string.
 * name等等这样的方式来引用资源了。 为此，我们就不能直接使用R文件，而是要通过字段名称来动态的获取资源的id，再来使用。
 * 以下封装了一个类，可以通过字段名称动态获取id。
 */
public class ResourceUtil {
	public static int getLayoutId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "layout",
				paramContext.getPackageName());
	}

	public static int getStringId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "string",
				paramContext.getPackageName());
	}

	public static int getDrawableId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString,
				"drawable", paramContext.getPackageName());
	}

	public static int getStyleId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "style",
				paramContext.getPackageName());
	}

	public static int getId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "id",
				paramContext.getPackageName());
	}

	public static int getColorId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "color",
				paramContext.getPackageName());
	}
	
	public static int getAnimId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "anim",
				paramContext.getPackageName());
	}
}
