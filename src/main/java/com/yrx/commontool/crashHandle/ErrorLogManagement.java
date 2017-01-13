package com.yrx.commontool.crashHandle;

import android.content.Context;

import com.yrx.commontool.device.DeviceManagement;
import com.yrx.commontool.log.LogDebug;

import org.litepal.crud.DataSupport;

public class ErrorLogManagement {

	private ErrorLogInfo mErrorLogInfo;
	private Context mContext;
	
	public ErrorLogManagement(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	
	public void saveErrorLog(String errorcontent, String appver, String modleIno, boolean stutas){
        ErrorLogInfo errorLog = new ErrorLogInfo();
        errorLog.setErrorcontent(errorcontent);
        errorLog.setAppver(appver);
        errorLog.setModelno(modleIno);
        errorLog.setStatus(stutas);
		//errorLog.setUuid(DeviceManagement.getInstance(mContext).getUuId());
		errorLog.setErrortype("");
		errorLog.setSystem(DeviceManagement.getInstance(mContext).getOSVersion());
        errorLog.save();
        LogDebug.i("ErrorLog", "save--应用程序版本号:"+appver+" 终端型号:"+modleIno+" bug内容:"+errorcontent);
	}
	
	public ErrorLogInfo findNewErrorLog(){
		ErrorLogInfo errorLogInfo = DataSupport.findLast(ErrorLogInfo.class);
		if(errorLogInfo == null){
			return null;
		}else {
			return errorLogInfo;
		}
	}
	
	public void deleteErrorLog(){
		DataSupport.deleteAll(ErrorLogInfo.class);
		LogDebug.i("ErrorLog", "删除表ErrorLogInfo");
	}
	
	
	/*public void sendErrorLog(){
		//上传log到服务器
		mErrorLogInfo = findNewErrorLog();
		if(mErrorLogInfo != null){
//			GetErrorLogParam getErrorLogParam = new GetErrorLogParam();
//			getErrorLogParam.mErrorLogInfo = mErrorLogInfo;
//			RequestTool rt = new RequestTool(mContext){
//				@Override
//				public void CallBack(String code, String jsonstr) {
//					// TODO Auto-generated method stub
//					if (jsonstr != null && !jsonstr.equals("")) {
//						Gson gson = new Gson();
//						RegisterUserResult result = new RegisterUserResult();
//						result = gson.fromJson(jsonstr,RegisterUserResult.class);
//						if (result.getOpret().getOpflag().equals("1")) {
//							deleteErrorLog();
//						}
//					}
//				}
//			};
//
//			rt.SendPost(RequestURL.getServiceURL(), getErrorLogParam, false);


			RetrofitUtils4SAPI.getInstance(mContext).SendBug(mErrorLogInfo.getModelno(),
					mErrorLogInfo.getErrorcontent(), mErrorLogInfo.getUuid(), mErrorLogInfo.getErrortype(),
					mErrorLogInfo.getSystem())
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<HttpOpret>() {
						@Override
						public void onCompleted() {

						}

						@Override
						public void onError(Throwable e) {
							LogDebug.i("retrofit", "提交BUG:" + e.toString());
						}

						@Override
						public void onNext(HttpOpret result) {
							LogDebug.i("retrofit", "提交BUG成功");
						}
					});

			LogDebug.i("ErrorLog","上传log到服务器");
		}
	}
	*/
}
