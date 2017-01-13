package com.yrx.commontool.device;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import com.yrx.commontool.log.LogDebug;

import java.util.List;

/**
 * 设备信息管理工具
 * @author zjj
 *
 */
public class DeviceManagement {
	private Context mContext;
	//单例
    private static DeviceManagement mInstance = null;

    public static DeviceManagement getInstance(Context applicationContext){
		if(mInstance == null){
			mInstance = new DeviceManagement(applicationContext);
		}
		return mInstance;
	}
    
    private DeviceManagement(Context applicationContext){
    	mContext = applicationContext.getApplicationContext();
    }
    
	/**
	 * 手机的型号
	 * @return
	 */
	public String getMoelno(){
		String moelon = android.os.Build.MODEL;
		moelon = moelon.replace(" ", "+");
		return moelon;
	}

	/**
	 * 获取当前系统的android版本号
	 * @return
	 */
	public String getOSVersion(){
//		return String.valueOf(android.os.Build.VERSION.SDK_INT);
//		return String.valueOf(android.os.Build.VERSION.RELEASE);
		return "Android_" + android.os.Build.VERSION.RELEASE;
	}
	
	
	/**
	 * 取客户端版本号
	 * @return
	 */
	public String getVersion(){
		String str_version = "";
		 PackageManager manager = mContext.getPackageManager();
	     PackageInfo info;
		try {
			info = manager.getPackageInfo(mContext.getPackageName(), 0);
			str_version = info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str_version ;
	}
	
	/**
	 * 版本号码
	 * @return
	 */
	public int getVersioncode(){
		int versioncode = -1;
		 PackageManager manager = mContext.getPackageManager();
	     PackageInfo info;
		try {
			info = manager.getPackageInfo(mContext.getPackageName(), 0);
			versioncode = info.versionCode;
			LogDebug.i("当前版本  versionCode:" + versioncode);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versioncode ;
	}

	/**
	 * build版本号码
	 * @return
	 */
	public int getVersionBuild(){
		int versionbuild = 1;
		try {
			ApplicationInfo appInfo = mContext.getPackageManager()
					.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
			versionbuild = appInfo.metaData.getInt("versionBuild");
			LogDebug.i("当前版本  versionbuild:" + versionbuild);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionbuild ;
	}
	
	/**
	 * 机器的唯一标识
	 * @return
	 *//*
	public String getUuId() {
		String uuid = PushManager.getTokenid(mContext,"");
		LogDebug.i("tokenid" , "机器的唯一标识 uuid:" + uuid );
		return uuid;
	}
	
	*//**
	 * PushTokenId
	 * @return
	 *//*
	public String getPushTokenId() {
		String tokenid = PushManager.getTokenid(mContext,	mContext.getPackageName());
		LogDebug.i("tokenid" , "机器的PushTokenId:" + tokenid + ",name:" + mContext.getPackageName() + ".");
		return tokenid;
	}
	
	*//**
	 * 取某个APP PushTokenId
	 * @return
	 *//*
	public String getAppPushTokenId(String pkgname) {
		String tokenid = PushManager.getTokenid(mContext,	pkgname);
		LogDebug.i("机器的PushTokenId:" + tokenid );
		return tokenid;
	}
	*/
	/**
	 * 取运营商
	 * @return （移动：1，电信：2，联通：3，未知：4）
	 */
	public String getSpType(){
		SIMCardInfo siminfo = new SIMCardInfo(mContext);
		if(siminfo.getProvidersName().equals(SIMCardInfo.TYPE_WCDMA)){
			return "3";
		}else if(siminfo.getProvidersName().equals(SIMCardInfo.TYPE_CDMA)){
			return "2";
		}else if(siminfo.getProvidersName().equals(SIMCardInfo.TYPE_GSM)){
			return "1";
		}else{
			return "4";
		}
	}
	
	/**
	 * 取渠道
	 * @return
	 */
	public String getChannel() {
		try {
			ApplicationInfo ai = mContext.getPackageManager().getApplicationInfo(
					mContext.getPackageName(), PackageManager.GET_META_DATA);
			Object value = ai.metaData.get("InstallChannel");
			if (value != null) {
				LogDebug.i("channel:" + value.toString() );
				return value.toString();
			}
		} catch (Exception e) {
			//
		}
		return null;
	}

	/**
	 * 获取本机应用程序的包所有的名是否包含传入包名
	 * @param packageName
	 * @return 判断pName中是否有目标程序的包名，有返回应用版本号，没有则返回""
	 */
	public String isAppAvilible(String packageName){
	    final PackageManager packageManager = mContext.getPackageManager();//获取packagemanager
	    List< PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
	    if(pinfo != null){ 
	    	for(int i = 0; i < pinfo.size(); i++){ 
	    		if( pinfo.get(i).packageName.equals(packageName)){
	    			return pinfo.get(i).versionName;
	    		}
	    	}
	    }
	    
	    return "";
//	    List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名 
//	   //从pinfo中将包名字逐一取出，压入pName list中 
//	        if(pinfo != null){ 
//	        for(int i = 0; i < pinfo.size(); i++){ 
//	            String pn = pinfo.get(i).packageName; 
//	            String version = pinfo.get(i).versionName;
////	            Log.i("jjj", "包名:" + pn);
//	            pName.add(pn); 
//	        } 
//	    } 
//	    return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE 
	}

	/**
	 * 取屏幕宽
	 * @return
     */
	public int getScreenWidth(){
		int width = 0;
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		if (Build.VERSION.SDK_INT > 16){
			Point point = new Point();
			wm.getDefaultDisplay().getRealSize(point);
			width = point.x;
		}else{
			width = wm.getDefaultDisplay().getWidth();
		}

		return width;
	}

	/**
	 * 取屏幕高
	 * @return
	 */
	public int getScreenHeight(){
		int height = 0;
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		if (Build.VERSION.SDK_INT > 16){
			Point point = new Point();
			wm.getDefaultDisplay().getRealSize(point);
			height  = point.y;
		}else{
			height  = wm.getDefaultDisplay().getHeight();
		}

		return height ;
	}

}
