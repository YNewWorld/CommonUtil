package com.yrx.commontool.file;

/**
 * 全局文件路径
 * Created by zhang jj on 2016/12/14.
 */

public class FileGlobal
{
    private final static String DUO_DIAN_ROOT = "/DrCom/";              //哆点根目录
    private final static String AD_FOLDER = "AD/";                      //广告目录
    private final static String PUSH_LOG_FOLDER = "DrpalmPushLog/";     //PUSH日志
    private final static String DB_FOLDER = "DuodianDB/";
    private final static String ERROR_FOLDER = "DuoDianError/";
    private final static String JNI_DIAGNOSE_LOG_FOLDER = "DuodianJniDiagnoseLog/";
    private final static String JNI_LOG_FOLDER = "DuodianJniLog/";
    private final static String IMAGE_FOLDER = "DuoDianImage/";
    private final static String STAT_FOLDER = "DuodianStat/";       //数据采集Log


    public static String getDuoDianRoot()
    {
        return DUO_DIAN_ROOT;
    }

    public static String getAdFolder()
    {
        return getDuoDianRoot() + AD_FOLDER;
    }

    public static String getPushLogFolder()
    {
        return getDuoDianRoot() + PUSH_LOG_FOLDER;
    }

    public static String getDbFolder()
    {
        return getDuoDianRoot() + DB_FOLDER;
    }

    public static String getErrorFolder()
    {
        return getDuoDianRoot() + ERROR_FOLDER;
    }

    public static String getJniDiagnoseLogFolder()
    {
        return getDuoDianRoot() + JNI_DIAGNOSE_LOG_FOLDER;
    }

    public static String getJniLogFolder()
    {
        return getDuoDianRoot() + JNI_LOG_FOLDER;
    }

    public static String getImageFolder()
    {
        return getDuoDianRoot() + IMAGE_FOLDER;
    }

    public static String getStatFolder()
    {
        return getDuoDianRoot() + STAT_FOLDER;
    }
}
