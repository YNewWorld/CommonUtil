package com.yrx.commontool.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;

import com.yrx.commontool.file.FileGlobal;
import com.yrx.commontool.log.LogDebug;
import com.yrx.commontool.screen.ScreenInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BitmapUtil
{
	private final static String FILEPATH = "/DuoDianImage/";

	/**
	 * 获取图片并压缩（调用此方法前先调用ScreenInfo.loadScreenInfo(activityContext)取得屏幕宽高信息，否则压缩不正常）
	 * @param path 图片路径
	 * @param compressScaleToScreen 目标图片宽度占屏幕宽度的比例(即要压缩成屏幕宽度的compressScaleToScreen分之一)
	 * @return
	 */
	public static Bitmap getBitmapWithScaleToScreen(String path, int compressScaleToScreen)
	{
		Options options = new Options();
		options.inJustDecodeBounds = true; // 先设true，用以读入图片信息
		BitmapFactory.decodeFile(path, options); //此时bitmap为null,因为仅读入图片信息
		int imgWidth = options.outWidth;
		int imgHeight = options.outHeight;
		
		options.inJustDecodeBounds = false; // 再设false，用以加载bitmap
		options.inSampleSize = getScaleToScreen(compressScaleToScreen, imgWidth);
		
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		SoftReference<Bitmap> reference = new SoftReference<Bitmap>(bitmap);
		return reference.get();
	}

	/**
	 * 获取图片并压缩到目标宽度
	 * @param path 图片路径
	 * @param targetWidth 目标图片宽度,单位像素
	 * @return
	 */
	public static Bitmap getBitmapWithCompressWidth(String path, int targetWidth)
	{
		Options options = new Options();
		options.inJustDecodeBounds = true; // 先设true，用以读入图片信息
		BitmapFactory.decodeFile(path, options); //此时bitmap为null,因为仅读入图片信息
		int imgWidth = options.outWidth;
		int imgHeight = options.outHeight;

		options.inJustDecodeBounds = false; // 再设false，用以加载bitmap
		options.inSampleSize = calculateCompressScale(targetWidth, imgWidth);

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		SoftReference<Bitmap> reference = new SoftReference<Bitmap>(bitmap);
		return reference.get();
	}

	
	/** 
	 * 保存压缩图片到sd卡方法 
	 * @param pathList
	 */
	public static List<String> saveBitmapList(List<String> pathList) {
		String pathName = Environment.getExternalStorageDirectory().toString() +FILEPATH;
		List<String> CompressPathList = new ArrayList<String>();
		for (int i = 0; i < pathList.size(); i++) {
			String filename = pathList.get(i).substring(pathList.get(i).lastIndexOf("/") + 1);
			Bitmap bm = getBitmapWithScaleToScreen(pathList.get(i), 5);
			File file = new File(pathName);
			File f = new File(pathName+filename);
			if (!file.exists()) {
				file.mkdir();
			} 
			try {
				FileOutputStream out = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();
				CompressPathList.add(pathName+filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return CompressPathList;
	}


	/**
	 *
	 * 保存图片到sd卡哆点目录
	 * @param bitmaps 图片列表
	 * @return 保存后的图片路列表
     */
	public static List<String> saveImgToSDCard(List<Bitmap> bitmaps) {

		String path = Environment.getExternalStorageDirectory().toString() + FileGlobal.getImageFolder();

		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//设置日期格式

		List<String> pathList = new ArrayList<String>();
		String date = mDateFormat.format(new Date());

		for (int i = 0; i < bitmaps.size(); i++) {

			String fileName = date + "_" + i + ".jpg";

			File file = new File(path);
			File f = new File(path + fileName);

			if (!file.exists()) {
				file.mkdir();
			}

			try {
				FileOutputStream out = new FileOutputStream(f);
				bitmaps.get(i).compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				pathList.add(path + fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return pathList;
	}
	
	/**
	 * 根据源图宽度和目标宽度，计算图片压缩比
	 * @param targetWidth 目标宽度
	 * @param imgWidth 源图片宽度
	 * @return 当源图大于目标宽度时(返回压缩比例N:源图为目标图片的N倍)，当源图小于目标宽度(返回压缩比为1)
	 */
	private static int calculateCompressScale(int targetWidth, int imgWidth)
	{
		//计算压缩的比例
		double scaleFloat = (double)imgWidth / (double)targetWidth;
		scaleFloat = Math.ceil(scaleFloat);  //进位取整，只要有小数点都进1

		int scaleInt = (int) scaleFloat;
		return scaleInt;
	}

	/**
	 * 根据源图宽度和目标占屏幕宽度的比例,获取图片压缩比
	 * @param compressScaleToScreen 目标图片宽度想要占屏幕宽度的比例（目标宽度为屏幕宽度的几分之一）
	 * @param imgWidth 源图片宽度
	 * @return 当源图大于目标宽度时(返回压缩比例N:源图为目标图片的N倍)，当源图小于目标宽度(返回压缩比为1)
	 */
	private static int getScaleToScreen(int compressScaleToScreen, int imgWidth)
	{
		//计算图片的目标宽度
		int imgTargetWidth = ScreenInfo.screenWidthPx / compressScaleToScreen;
		//计算压缩的比例
		return calculateCompressScale(imgTargetWidth, imgWidth);
	}

	/**
	 * 根据长边压缩
	 * 以长边小于等于X为标准进行压缩图片
	 * @param path
	 * @param longestSide	最长边长度
	 * @return
	 */
	public static byte[] getBitmapBytesWithCompressLongsize(String path, int longestSide) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		int inSampleSize = 1;
		if (height > longestSide || width > longestSide) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) longestSide);
			} else {
				inSampleSize = Math.round((float) width / (float) longestSide);
			}
		}
		opts.inSampleSize = inSampleSize;
		opts.inJustDecodeBounds = false;

		Bitmap tBitmap =  BitmapFactory.decodeFile(path, opts);
		LogDebug.i("bitmap" , "压缩大小后：" + tBitmap.getByteCount());

		ByteArrayOutputStream stream = null;
		byte[] b = null;
		try {
			stream = new ByteArrayOutputStream();
			tBitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
			//把流转化为数组
            b = stream.toByteArray();
		}finally {
			try {
				if(stream != null)
					stream.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		LogDebug.i("bitmap" , "压缩质量后：" + b.length);
		return b;
	}

	/**
	 * 解决微信分享透明背景图片背景变黑
	 * @param bitmap
	 * @return
     */
	public static Bitmap changeColor(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int[] colorArray = new int[w * h];
		int n = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int color = getMixtureWhite(bitmap.getPixel(j, i));
				colorArray[n++] = color;
			}
		}
		return Bitmap.createBitmap(colorArray, w, h, 		    Bitmap.Config.ARGB_8888);
	}

	//获取和白色混合颜色
	private static int getMixtureWhite(int color) {
		int alpha = Color.alpha(color);
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite(green, alpha),
				getSingleMixtureWhite(blue, alpha));
	}

	// 获取单色的混合值
	private static int getSingleMixtureWhite(int color, int alpha) {
		int newColor = color * alpha / 255 + 255 - alpha;
		return newColor > 255 ? 255 : newColor;
	}


	/**
	 * 将bitmap图拼接在一起(纵向拼接)
	 * @param bitmaps
	 * @return
     */
	public static Bitmap stitchBitmap(Bitmap... bitmaps)
	{
		List<Integer> widthList = new ArrayList<>();
		List<Integer> heightList = new ArrayList<>();

		int totalWidth = 0;  //所有图片宽度累加
		int totalHeight = 0; //所有图片高度累加

		for (Bitmap b: bitmaps)
		{
			int w = b.getWidth();
			int h = b.getHeight();

			totalWidth = totalWidth + w;
			totalHeight = totalHeight + h;

			widthList.add(w);
			heightList.add(h);
		}

		Integer maxWidth = Collections.max(widthList);  //最大的宽度
		Integer maxHeight = Collections.max(heightList);  //最大的高度

		Bitmap newBitmap = Bitmap.createBitmap(maxWidth, totalHeight, Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(newBitmap);

		int addHeight = 0;

		for (Bitmap b: bitmaps)   //拼接图片，将每一张画到前一张下面
		{
			c.drawBitmap(b, 0, addHeight, null);
			addHeight = addHeight + b.getHeight();
		}

		return newBitmap;
	}


	/**
	 * 将View截图
	 * @param v
	 * @return
     */
	public static Bitmap captureFromView(View v) {
		if (v == null) {
			return null;
		}
		Bitmap screenshot;
		screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(screenshot);
		c.translate(-v.getScrollX(), -v.getScrollY());
		v.draw(c);
		return screenshot;
	}


	/**
	 * Bitmap转ByteArray
	 * @param b
	 * @return
     */
	public static byte[] bitmapToByteArray(Bitmap b)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		try
		{
			stream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return byteArray;
	}


	/**
	 * 从本地路径或网络URL取得图片bitmap对象
	 * @param imageUrl
	 * @return
	 * @throws Throwable
     */
	public static Bitmap fetchBitmap(String imageUrl) throws Throwable
	{
		Bitmap bitmap = null;

		if(!imageUrl.contains("http"))
		{
			bitmap = BitmapFactory.decodeFile(imageUrl);
			return bitmap;
		}

		BufferedInputStream bis = new BufferedInputStream(new URL(imageUrl).openStream());
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(dataStream);

		byte[] b = new byte[1024];
		int len = 0;
		while ((len = bis.read(b)) != -1)
		{
			bos.write(b, 0, len);
		}
		bos.flush();
		byte[] bytes = dataStream.toByteArray();

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		if (bytes != null && bytes.length > 0)
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

		bis.close();
		dataStream.close();
		bos.close();

		return bitmap;
	}
}
