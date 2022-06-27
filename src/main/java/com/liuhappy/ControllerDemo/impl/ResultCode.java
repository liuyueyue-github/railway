package com.liuhappy.ControllerDemo.impl;

import com.liuhappy.ControllerDemo.interfaces.StatusCode;
import lombok.Getter;

/**
 * @author Grin
 * @Date 2022/6/24
 * @Description
 */
@Getter
public enum ResultCode implements StatusCode {
    /**
     * enum
     */
    SUCCESS("BUG1000", "请求成功"),
    FAIL("BUG1001", "请求失败");

    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
