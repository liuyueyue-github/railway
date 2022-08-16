package com.liuhappy.BugDemo.enums.impl;

import com.liuhappy.BugDemo.enums.ErrorCode;

/**
 * @author yueyue.liu
 */

public enum CommonErrorCode implements ErrorCode {


	RuntimeException("000000", "RuntimeException");


	private String code;
	private String desc;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	private CommonErrorCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}


	public static CommonErrorCode setErrorCode(String code) {
		for (CommonErrorCode errorCode : CommonErrorCode.values()) {
			if (errorCode.getCode()==code) {
				return errorCode;
			}
		}
		return null;
	}
}
