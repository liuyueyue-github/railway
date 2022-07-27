package com.liuhappy.BugDemo.vo;

import com.liuhappy.BugDemo.enums.StatusCode;
import com.liuhappy.BugDemo.enums.impl.ResultCode;
import lombok.Data;

/**
 * @author Grin
 * @Date 2022/6/24
 * @Description
 */
@Data
public class ResultVo<T> {
    /**
     * 状态码
     */
    private String code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 返回对象
     */
    private T data;

    /**
     * 手动设置返回vo
     * @param code
     * @param msg
     * @param data
     */
    public ResultVo(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认返回成功状态码，数据对象
     * @param data
     */
    public ResultVo(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * 返回指定状态码，数据对象
     * @param statusCode
     * @param data
     */
    public ResultVo(StatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * 只返回状态码
     * @param statusCode
     */
    public ResultVo(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }
}
