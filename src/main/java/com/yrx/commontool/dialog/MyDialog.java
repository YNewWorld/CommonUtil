package com.yrx.commontool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.widget.Button;
import android.widget.TextView;

import com.yrx.commontool.R;


public class MyDialog {
	
	/**
	 * 弹出只有OK按钮的对话框
	 * @param ok
	 * @param title
	 * @param content
	 */
	public static void showOK(Context context, String title, String content, final OnDialogBtnClickCallback ok){
		final Dialog lDialog = new Dialog(context, R.style.myMarkDialog);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.dialog);
		lDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
		((TextView) lDialog.findViewById(R.id.tv_dialog_title)).setText(title);
		((TextView) lDialog.findViewById(R.id.tv_dialog_msg)).setText(content);
		Button btn_ok = (Button) lDialog.findViewById(R.id.btn_ok);
		if(ok != null){
			lDialog.dismiss();
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
					ok.onCallback();
				}
			});
		}else{
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
				}
			});
		}
		
		try{
			lDialog.show();
		}catch(BadTokenException e){}
		
	}
	
	/**
	 * 弹出OK和Cancel按钮的对话框
	 * @param ok
	 * @param title
	 * @param content
	 */
	public static void showOK_Cancel(Context context, String title, String content, final OnDialogBtnClickCallback ok, final OnDialogBtnClickCallback cancel){
		final Dialog lDialog = new Dialog(context, R.style.myMarkDialog);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.msg_dialog);
		lDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
		((TextView) lDialog.findViewById(R.id.tv_dialog_title)).setText(title);
		((TextView) lDialog.findViewById(R.id.tv_dialog_msg)).setText(content);
		Button btn_ok = (Button) lDialog.findViewById(R.id.btn_ok);
		if(ok != null){
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
					ok.onCallback();
				}
			});
		}else{
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
				}
			});
		}
		
		Button btn_cancel = (Button) lDialog.findViewById(R.id.btn_cancel);
		if(cancel != null){
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
					cancel.onCallback();
				}
			});
			
			//设置返回键与CANCEL一样的事件
			lDialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK)
				    {
						return true;
				    }else{
				    	return false;
				    }
				}
			});
		}else{
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
				}
			});
		}
		
		lDialog.show();
	}
	
	/**
	 * 弹出OK和Cancel按钮的对话框
	 * @param oktext	ok按钮文字
	 * @param canceltext cancel按钮文字
	 * @param ok
	 * @param cancel
	 */
	public static void showOK_Cancel(Context context, String title, String content, String oktext, String canceltext, final OnDialogBtnClickCallback ok, final OnDialogBtnClickCallback cancel){
		final Dialog lDialog = new Dialog(context, R.style.myMarkDialog);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.msg_dialog);
		lDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
		((TextView) lDialog.findViewById(R.id.tv_dialog_title)).setText(title);
		((TextView) lDialog.findViewById(R.id.tv_dialog_msg)).setText(content);
		Button btn_ok = (Button) lDialog.findViewById(R.id.btn_ok);
		btn_ok.setText(oktext);
		if(ok != null){
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
					ok.onCallback();
				}
			});
		}else{
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
				}
			});
		}
		
		Button btn_cancel = (Button) lDialog.findViewById(R.id.btn_cancel);
		btn_cancel.setText(canceltext);
		if(cancel != null){
//			lDialog.dismiss();
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
					cancel.onCallback();
				}
			});
			
			//设置返回键与CANCEL一样的事件
			lDialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK)
				    {
						return true;
				    }else{
				    	return false;
				    }
				}
			});
		}else{
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lDialog.dismiss();
				}
			});
		}
		
		lDialog.show();
	}
	
	/**
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnDialogBtnClickCallback{
		public void onCallback();
	}
	
}
