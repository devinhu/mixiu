/*
    ShengDao Android Client, BaseResponse
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.model.base;

import android.text.TextUtils;
import android.util.Log;

import com.sd.one.utils.AES;
import com.sd.one.utils.NLog;

/**
 * [返回结果基类，返回结果公共字段本类实现]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class BaseResponse extends BaseModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5616901114632786764L;

	private String code;

	private String message;

	private String data;

	public String getData() {
		try {
			data = AES.getInstance().decrypt(data);
			NLog.e("getData", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSucces() {
		if (!TextUtils.isEmpty(code) && "0".equals(code)) {
			return true;
		}
		return false;
	}
}
