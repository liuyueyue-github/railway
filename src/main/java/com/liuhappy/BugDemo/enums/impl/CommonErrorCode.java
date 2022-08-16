package com.liuhappy.BugDemo.enums.impl;

import com.liuhappy.BugDemo.enums.ErrorCode;

/**
 * @author yueyue.liu
 */

public enum CommonErrorCode implements ErrorCode {


	RuntimeException("000000", "RuntimeException"),
	PD_ERROR_00("PD000000","产品添加失败"),
	PD_ERROR_01("PD000001","产品删除失败"),
	PD_ERROR_02("PD000002","产品更新失败"),
	PD_ERROR_03("PD000003","产品不存在")
	;


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
