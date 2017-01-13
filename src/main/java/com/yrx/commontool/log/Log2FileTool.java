package com.yrx.commontool.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;

public class Log2FileTool {
	/** 程序的Context对象 */  
    private Context mContext;   
	//用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();  
    /** 错误报告文件的扩展名 */  
    private static final String CRASH_REPORTER_EXTENSION = ".txt";   
    private static final String CRASH_FILENAME = "/DuodianLog/";

    
    /**  
     * 初始化,注册Context对象,  
     * 获取系统默认的UncaughtException处理器,  
     * 设置该CrashHandler为程序的默认处理器  
     *   
     * @param ctx  
     */  
    public Log2FileTool(Context ctx) {   
        mContext = ctx;  
    }   
    
    /**
     * 判断存储卡是否存在
     * 
     * @return
     */
    private boolean existSDcard()
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            return true;
        }
        else
            return false;
    }
    
	/**  
     * 保存错误信息到文件中  
     * @param errorstr
     * @return  
     */  
    public void saveLog(String errorstr){	
    	 StringBuffer sb = new StringBuffer();  
         for (Map.Entry<String, String> entry : infos.entrySet()) {  
             String key = entry.getKey();  
             String value = entry.getValue();  
             sb.append(key + "=" + value + "\n");  
         }  

         sb.append(errorstr);  

        try {   
            long timestamp = System.currentTimeMillis();   
            Date dt = new Date(timestamp);
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            
            //byte[] buf = result.getBytes(); 
            byte[] buf = sb.toString().getBytes();
            DecimalFormat df = new DecimalFormat("00");
            String fileName = "log" +df.format(year)+df.format(month)+df.format(day)+df.format(hour)+df.format(min)+df.format(second)+CRASH_REPORTER_EXTENSION;
            if(existSDcard())   //有存储卡
            {
	            //String pathName="/sdcard/DrpalmError/";  
//            	String pathName = mContext.getString(R.string.crashfilepath);
            	String pathName = Environment.getExternalStorageDirectory().toString() + CRASH_FILENAME;
	            File path = new File(pathName);  
	            File file = new File(pathName + fileName); 
	            boolean flags = false;
	            if( !path.exists()) {  
	            	flags = path.mkdir();  
	            }  
	            if( !file.exists()) {  
	                file.createNewFile();  
	            }  
	            FileOutputStream fos = new FileOutputStream(file); 
	            fos.write(buf);            
	            fos.close(); 
            }
            else
            {
            	FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);  
	            fos.write(buf);            
	            fos.close(); 
            }
        } catch (Exception e) {   
//            Log.e(TAG, "an error occured while writing report file...", e);   
        }   
    }   
    
    
	/**
	 * 写入内容到SD卡中
	 * @param dirname 目录名
	 * @param filename 文件名 (xxxx.txt)
	 * @param content 内容
	 */
	public static void save2File(String dirname, String filename, String content) {
		String filepath = Environment.getExternalStorageDirectory().toString() + "/" + dirname + "/";
		
		BufferedWriter out = null;
		File path = new File(filepath);// 获得SD卡路径
		if (!path.exists()) {
			path.mkdir();
		} 
		File file = new File(filepath + filename);
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(content);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
