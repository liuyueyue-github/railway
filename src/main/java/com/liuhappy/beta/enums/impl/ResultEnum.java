package com.liuhappy.beta.enums.impl;

import com.liuhappy.beta.enums.IResult;
import lombok.Getter;

/**
 * @author Grin
 * @Date 2022/6/24
 * @Description
 */
@Getter
public enum ResultEnum implements IResult {
    /**
     * enum
     */
    SUCCESS("BUG1000", "success"),
    FAIL("BUG1001", "fail"),
    VALIDATE_FAILED("BUG1002", "参数校验失败"),
    FORBIDDEN("BUG1003", "没有权限访问资源");

    private final String code;
    private final String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
