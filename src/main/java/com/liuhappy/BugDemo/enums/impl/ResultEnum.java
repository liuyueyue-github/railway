package com.liuhappy.BugDemo.enums.impl;

import com.liuhappy.BugDemo.enums.IResult;
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
    SUCCESS("BUG1000", "接口调用成功"),
    FAIL("BUG1001", "接口调用失败"),
    VALIDATE_FAILED("BUG1002", "参数校验失败"),
    FORBIDDEN("BUG1003", "没有权限访问资源");

    private final String code;
    private final String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
