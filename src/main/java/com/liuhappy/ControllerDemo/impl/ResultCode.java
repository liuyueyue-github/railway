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
    SUCCESS(1000, "请求成功"),
    FAIL(1001, "请求失败");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
