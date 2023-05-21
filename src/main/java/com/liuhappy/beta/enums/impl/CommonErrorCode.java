package com.liuhappy.beta.enums.impl;

import com.liuhappy.beta.enums.ErrorCode;

/**
 * @author yueyue.liu
 */

public enum CommonErrorCode implements ErrorCode {


    RuntimeException("000000", "RuntimeException"),
    PC_ADD("PC000000", "支出种类添加失败"),
    PC_DELETE("PC000001", "支出种类删除失败"),
    PC_UPDATE("PC000002", "支出种类更新失败"),
    PC_SELECT("PC000003", "支出种类不存在"),
    PC_ALREADY_EXIST("PC000004", "支出种类已存在"),

    IC_ADD("IC000000", "收入种类添加失败"),
    IC_DELETE("IC000001", "收入种类删除失败"),
    IC_UPDATE("IC000002", "收入种类更新失败"),
    IC_SELECT("IC000003", "收入种类不存在"),
    IC_ALREADY_EXIST("IC000004", "收入种类已存在"),

    PF_ADD("PF000000", "支出流水添加失败"),
    PF_DELETE("PF000001", "支出流水删除失败"),
    PF_UPDATE("PF000002", "支出流水更新失败"),

    IF_ADD("IF000000", "收入流水添加失败"),
    IF_DELETE("IF000001", "收入流水删除失败"),
    IF_UPDATE("IF000002", "收入流水更新失败")
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
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }
}
