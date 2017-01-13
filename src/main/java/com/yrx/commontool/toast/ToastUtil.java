package com.yrx.commontool.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yrx.commontool.screen.ScreenUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 由于华为需要打开通知权限才可以看到系统Toast，
 * 所以使用悬浮窗代替
 */
public class ToastUtil extends BaseThread {
	private static int SHOW_TIME = 2000; // 显示时间
	private static final int QUEUE_SIZE = 120; // 队列大小
	private static final int QUEUE_SIZE_LIMIT = 100; // 限制队列大小
	private static final int FLAG_SHOW = 1000; // 显示
	private static final int FLAG_HIDE = 1001; // 隐藏
	private static final int FLAG_CLEAR = 1002; // 清理消息队列
	private static final String QUITMSG = "@bian_#feng_$market_%toast_&quit_*flag"; // 退出的标记

	private static BlockingQueue<String> mMsgQueue = new ArrayBlockingQueue<String>(QUEUE_SIZE); // 消息缓存队列

	private static ToastUtil mToast;

	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mParams;
	private View toastView;
	private TextView tvAlert;
	private Context mContext;

	@SuppressLint("InflateParams")
	private ToastUtil(Context context, boolean isShowInCenter) {
		Context c = context.getApplicationContext();
		mContext = context;
		mWindowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);

		mParams = new WindowManager.LayoutParams();
		mParams.type = WindowManager.LayoutParams.TYPE_TOAST; //TYPE_SYSTEM_OVERLAY
		mParams.windowAnimations = android.R.style.Animation_Toast;
		mParams.format = PixelFormat.TRANSLUCENT;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.alpha = 1f;// 透明度，0全透 ，1不透
		//设置消息显示位置
		if(!isShowInCenter) {
			mParams.gravity = Gravity.CENTER_HORIZONTAL| Gravity.TOP;
			mParams.verticalMargin = 0.85f;
		}else {
			mParams.gravity = Gravity.CENTER;
			//标题栏占屏幕高度的比值
			float percent = ScreenUtils.getStatusHeight(mContext)/(float)ScreenUtils.getScreenHeight(mContext);
			//DecimalFormat df = new DecimalFormat("0.000");//保留两位有效小数
			//percent = (Float.valueOf(df.format(percent)));
			mParams.verticalMargin = percent;
		}
		mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

		int layoutId = context.getResources().getIdentifier("ayout_toast", "layout", context.getPackageName());
		int tvId = context.getResources().getIdentifier("tvAlert", "id", context.getPackageName());
		toastView = LayoutInflater.from(c).inflate(layoutId, null);
		tvAlert = (TextView) toastView.findViewById(tvId);

		start();
	}


	/**
	 * 初始化消息显示
	 *
	 * @param context
	 * @param isShowInCenter 为true则居中显示消息
	 */
	private static void init(Context context, boolean isShowInCenter) {
		if (null == mToast) {
			mToast = new ToastUtil(context,isShowInCenter);
		}
	}

	private Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case FLAG_SHOW:
				String str = msg.obj.toString();
				if (!TextUtils.isEmpty(str)) {
					showMsg(str);
				}
				break;
			case FLAG_HIDE:
				hideMsg();
				break;
			case FLAG_CLEAR:
				showMsg("操作异常，消息太多");
				break;

			default:
				break;
			}
		};
	};

	private void showMsg(String msg) {
//		if (Build.VERSION.SDK_INT >= 23) {
//			if(!Settings.canDrawOverlays(mContext)) {
//				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//				mContext.startActivity(intent);
//				return;
//			} else {
//				//绘ui代码, 这里说明6.0系统已经有权限了
//			}
//		} else {
//			//绘ui代码,这里android6.0以下的系统直接绘出即可
//
//		}

		try {
			tvAlert.setText(msg);
			if (null == toastView.getParent()) {
				mWindowManager.addView(toastView, mParams);
			}
		} catch (Exception e) {
			//小米MUI8 会抛出permission denied for this window type，使用系统Toast代替
			Toast.makeText(mContext , msg ,  Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	private void hideMsg() {
		try {
			if (null != toastView.getParent()) {
				mWindowManager.removeView(toastView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 显示消息
//	 *
//	 * @param msg
//	 *            显示的内容
//	 */
//	public static void show(String msg) {
//		try {
//			mMsgQueue.put(msg); // block
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void show(Context context, int id) {
//		try {
//			mMsgQueue.put(context.getResources().getString(id)); // block
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 显示消息
	 * @param context
	 * @param text	显示的内容
	 * @param duration
	 */
	public static ToastUtil makeText(Context context, String text, int duration){
		if(duration == Toast.LENGTH_SHORT){
			SHOW_TIME = 2000;
		}else if(duration == Toast.LENGTH_LONG){
			SHOW_TIME = 5000;
		}else{
			SHOW_TIME = 2000;
		}

		init(context,false); //默认下方显示消息

		try {
			mMsgQueue.put(text); // block
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mToast;
	}
/**
	 * 居中显示消息
	 * @param context
	 * @param text	显示的内容
	 * @param duration
	 */
	public static ToastUtil makeTextCenter(Context context, String text, int duration){
		if(duration == Toast.LENGTH_SHORT){
			SHOW_TIME = 2000;
		}else if(duration == Toast.LENGTH_LONG){
			SHOW_TIME = 5000;
		}else{
			SHOW_TIME = 2000;
		}

		init(context,true); //居中显示消息

		try {
			mMsgQueue.put(text); // block
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mToast;
	}

	/**
	 * 这个函数并没有什么卵用，只是为了和系统的Toast使用方法一致
	 */
	public void show() {

	}

	/**
	 * 退出
	 */
	public static void eixt() {
		try {
			mMsgQueue.put(QUITMSG);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		try {
			String msgStr = mMsgQueue.take();

			if (QUITMSG.equals(msgStr)) {
				exitToast();
				return;
			}

			Message msg = mHandler.obtainMessage();
			if (null == msg) {
				msg = new Message();
			}
			msg.what = FLAG_SHOW;
			msg.obj = msgStr;
			mHandler.sendMessage(msg);

			Thread.sleep(SHOW_TIME);

			if (mMsgQueue.size() == 0) {
				mHandler.sendEmptyMessage(FLAG_HIDE);
			}

			if (mMsgQueue.size() > QUEUE_SIZE_LIMIT) {
				mMsgQueue.clear();

				mHandler.sendEmptyMessage(FLAG_CLEAR);
				Thread.sleep(SHOW_TIME);
				mHandler.sendEmptyMessage(FLAG_HIDE);
			}

			System.out.println(">>>>>" + mMsgQueue.size());
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(FLAG_HIDE);
		}
	}

	/**
	 * 退出，清理内存
	 */
	private void exitToast() {
		try {
			hideMsg();

			quit();
			mMsgQueue.clear();
			mMsgQueue = null;
			mToast = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}