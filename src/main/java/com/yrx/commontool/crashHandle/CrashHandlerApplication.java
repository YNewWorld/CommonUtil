package com.yrx.commontool.crashHandle;

import org.litepal.LitePalApplication;

/**
 * @author Yrx
 *         Created on 2016/5/20.
 *         描述：
 */
public class CrashHandlerApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(this);
    }
}
