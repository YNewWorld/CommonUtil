package com.yrx.commontool.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.yrx.commontool.log.LogDebug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetStatusTool {
	public final static String NetType_NOCONNECT = "NotNet";
	public final static String NetType_WIFI = "wifi";
	public final static String NetType_3G = "3G";
	
	private final static int STATUS_NOCONNECT = -1;	//未连接
	private final static int STATUS_WIFI = 1;
	private final static int STATUS_GPRS = 2;
	
    /**
     * 网络是否连接&&连接类型
     * @param context
     * @return
     */
    private static int IsNetUsed(Context context)
    {
		//----------Test---------------

		LogDebug.i("IsNetUsed" , "getCurrentNetworkType:" + NetWorkUtil.getCurrentNetworkType(context));

		//-----------------------------

		//获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		if (null == connManager){
			LogDebug.i("IsNetUsed","status = STATUS_NOCONNECT，SSID:"+getSSID(context));
			return STATUS_NOCONNECT;
		}
		NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
			LogDebug.i("IsNetUsed","status = STATUS_NOCONNECT，SSID:"+getSSID(context));
			return STATUS_NOCONNECT;
		}

		NetworkInfo wifiInfo=connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(null!=wifiInfo){
			State state = wifiInfo.getState();
			if(null!=state)
				if (state == State.CONNECTED || state == State.CONNECTING) {
					LogDebug.i("IsNetUsed","status = NetType_WIFI，SSID:"+getSSID(context));
					return STATUS_WIFI;
				}
		}

		// 网络
		//没SIM卡的机可能会报空指针错误
		try {
			NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (null != networkInfo) {
				State state = networkInfo.getState();
				String strSubTypeName = networkInfo.getSubtypeName();
				if (null != state)
					if (state == State.CONNECTED || state == State.CONNECTING) {
						LogDebug.i("IsNetUsed","status = STATUS_GPRS，SSID:"+getSSID(context));
						return STATUS_GPRS;
					}
			}
		}catch(Exception e){

		}

		return  STATUS_NOCONNECT;
    }

	/**
     * 取当前网络状态类型
     */
    public static String getNetType(Context context){
    	int type = IsNetUsed(context);
    	switch (type) {
		case STATUS_WIFI:
			return NetType_WIFI;
		case STATUS_GPRS:
			return NetType_3G;
		default:
			return NetType_NOCONNECT;
		}
    }
    
    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {  
                return mNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }
    
    /**      
     *  @author suncat      
     *  @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）      
     * @return     
     *  */     
	public static final boolean ping() {
		String result = null;
		try {
			String ip = "www.baidu.com";
			// ping 的地址，可以换成任何一种可靠的外网
			Process p = Runtime.getRuntime().exec("ping -w 1 " + ip);	// -c 3
			// ping网址3次
			// 读取ping的内容，可以不加
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			try{
				while ((content = in.readLine()) != null) {
					stringBuffer.append(content);
				}
				LogDebug.v("jjj","------ping-----result content : " + stringBuffer.toString());
			}catch(Exception e){}
			
			// ping的状态
			int status = p.waitFor();
			if (status == 0) {
				result = "success";
				return true;
			} else {
				result = "failed";
			}
		} catch (IOException e) {
			result = "IOException";
		} catch (InterruptedException e) {
			result = "InterruptedException";
		} finally {
			LogDebug.v("----result---", "result = " + result);
		}
		return false;
	}
	
	/**
	 * 启动线程PING，结果回调到onPingListener中
	 * @param listener
	 */
	public static void ping(final onPingListener listener) {
		Runnable rping = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				listener.callBack(NetStatusTool.ping());
			}
		};
		Thread tping = new Thread(rping);
        tping.start();
	}
	
	static String mSsid = "";
	/**
	 * 获取wifi SSID
	 * @return SSID
	 */
	public static String getSSID(Context context){
		WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled()) {
			if (wifi != null) {
				try{
					WifiInfo info = wifi.getConnectionInfo();
					if (info != null) {
						String ssid = info.getSSID();
						if(ssid != null && !"".equals(ssid) && !ssid.contains("0x")&&!ssid.contains("<unknown ssid>")){
							mSsid = ssid.replace("\"", "");
						}else {
							mSsid = "";
						}
					}
				}catch(Exception e){}
				
			}
		}
		return mSsid;
	}
}
