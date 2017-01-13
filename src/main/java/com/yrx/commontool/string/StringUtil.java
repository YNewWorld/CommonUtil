package com.yrx.commontool.string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Yrx
 *         Created on 2017/1/5.
 *         描述：
 */

public class StringUtil {

    /**
     *  对指定字符设置颜色
     * @param str
     *                         字符串
     * @param ch1
     *                         切换颜色开始的字符
     * @param ch2
     *                         切换颜色停止的字符
     * @param color
     *                         设置的颜色
     * @param tv
     *                         TextView控件
     */
    private static void setTVColor(String str , char ch1 , char ch2 , int color , TextView tv){
        int a = str.indexOf(ch1); //从字符ch1的下标开始
        int b = str.indexOf(ch2)+1; //到字符ch2的下标+1结束,因为SpannableStringBuilder的setSpan方法中区间为[ a,b )左闭右开
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(color), a, b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 将异常信息转成字符串
     * @param throwable
     * @return
     */
    public static String FormatStackTrace(Throwable throwable) {
        if(throwable==null) return "";
        String rtn = throwable.getStackTrace().toString();
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            writer.flush();
            rtn = writer.toString();
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
        return rtn;
    }

}
