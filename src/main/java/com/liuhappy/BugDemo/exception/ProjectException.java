package com.liuhappy.BugDemo.exception;

import com.liuhappy.BugDemo.enums.IResult;

/**
 * @author yueyue.liu
 */
public class ProjectException extends RuntimeException {

    /**
     * 错误编码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 异常接口
     */
    private IResult basicEnumInterface;

    public ProjectException() {

    }
    public ProjectException(String code,String message) {
        this.code = code;
        this.message = message;
    }


    public ProjectException(IResult basicEnumInterface) {
        this.code = basicEnumInterface.getCode();
        this.message = basicEnumInterface.getMsg();
        this.basicEnumInterface = basicEnumInterface;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IResult getBasicEnumInterface() {
        return basicEnumInterface;
    }

    public void setBasicEnumInterface(IResult basicEnumInterface) {
        this.basicEnumInterface = basicEnumInterface;
    }

    @Override
    public String toString() {
        return "ProjectException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
