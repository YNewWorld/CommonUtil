package com.yrx.commontool.crashHandle;

import org.litepal.crud.DataSupport;

public class ErrorLogInfo extends DataSupport {

	private int id;
	private String modelno;
	private String appver;
	private boolean status = true;//true:未上传,false:已上传
	private String errorcontent;
	private String uuid = "";	//机器唯一标识码
	private String errortype = "";	//错误类型
	private String system = "";	//系统型号(例如:android4.3.1/ios9......)

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getErrorcontent() {
		return errorcontent;
	}
	public void setErrorcontent(String errorcontent) {
		this.errorcontent = errorcontent;
	}
	public String getModelno() {
		return modelno;
	}
	public void setModelno(String modelno) {
		this.modelno = modelno;
	}
	public String getAppver() {
		return appver;
	}
	public void setAppver(String appver) {
		this.appver = appver;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getErrortype() {
		return errortype;
	}

	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}
}
