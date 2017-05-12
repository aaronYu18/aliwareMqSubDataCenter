package com.courier.sdk.common;

import com.courier.sdk.utils.GsonUtil;

import java.io.Serializable;

/**
 * 统一定义id的entity基类.
 * 
 * @author Ryan
 */
public class IdEntity implements Serializable{

	public String toJson(){
		return GsonUtil.toJson(this);
	}

	public static <T> T getBean(String json, Class<T> cls){
		return GsonUtil.getBean(json, cls);
	}
}
