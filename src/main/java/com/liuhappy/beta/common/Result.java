package com.liuhappy.beta.common;

import com.liuhappy.beta.enums.IResult;
import com.liuhappy.beta.enums.impl.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Grin
 * @Date 2022/6/24
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result<?> failed() {
        return new Result<>(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg(), "");
    }

    public static Result<?> failed(String message) {
        return new Result<>(ResultEnum.FAIL.getCode(), message, "");
    }

    public static Result<?> failed(IResult errorResult) {
        return new Result<>(errorResult.getCode(), errorResult.getMsg(), "");
    }

    public static <T> Result<T> instance(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(message);
        result.setData(data);
        return result;
    }
}
