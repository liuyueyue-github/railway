package com.liuhappy.BugDemo.common;

import com.alibaba.fastjson.JSONObject;
import com.liuhappy.BugDemo.enums.ErrorCode;
import com.liuhappy.BugDemo.enums.IResult;
import com.liuhappy.BugDemo.enums.impl.CommonErrorCode;
import com.liuhappy.BugDemo.enums.impl.ResultEnum;
import com.liuhappy.BugDemo.vo.User;

import java.util.Date;

/**
 * @author yueyue.liu
 */
public class ResponseWrapBuild {

    public static <T>ResponseWrap<T> buildSuccess(IResult basicEnumInterface, T t){

        //从UserVoContext中拿到userVoString
        String userVoString = UserVoContext.getUserVoString();
        User user = null;
        if (!EmptyUtil.isNullOrEmpty(userVoString)){
            user = JSONObject.parseObject(userVoString, User.class);
        }else {
            user = new User();
        }
        //构建对象
        return ResponseWrap.<T>builder()
                .code(basicEnumInterface.getCode())
                .msg(basicEnumInterface.getMsg())
                .operationTime(new Date())
                .userId(user.getId()==null?"":user.getId())
                .userName(user.getUsername()==null?"":user.getUsername())
                .data(t)
                .build();
    }

    public static <T>ResponseWrap<T> buildBusinessFail(ErrorCode errorCode, T t){

        //从UserVoContext中拿到userVoString
        String userVoString = UserVoContext.getUserVoString();
        User user = null;
        if (!EmptyUtil.isNullOrEmpty(userVoString)){
            user = JSONObject.parseObject(userVoString, User.class);
        }else {
            user = new User();
        }
        //构建对象
        return ResponseWrap.<T>builder()
                .code(errorCode.getCode())
                .msg(errorCode.getDesc())
                .operationTime(new Date())
                .userId(user.getId() == null ? "" : user.getId())
                .userName(user.getUsername() == null ? "" : user.getUsername())
                .data(t)
                .build();
    }

    public static <T>ResponseWrap<T> buildRuntimeExceptionFail(String errorMsg, T t){

        //从UserVoContext中拿到userVoString
        String userVoString = UserVoContext.getUserVoString();
        User user = null;
        if (!EmptyUtil.isNullOrEmpty(userVoString)){
            user = JSONObject.parseObject(userVoString, User.class);
        }else {
            user = new User();
        }
        //构建对象
        return ResponseWrap.<T>builder()
                .code(CommonErrorCode.RuntimeException.getCode())
                .msg(errorMsg)
                .operationTime(new Date())
                .userId(user.getId() == null ? "" : user.getId())
                .userName(user.getUsername() == null ? "" : user.getUsername())
                .data(t)
                .build();
    }

}