package com.liuhappy.BugDemo.common;

import com.alibaba.fastjson.JSONObject;
import com.liuhappy.BugDemo.enums.IResult;
import com.liuhappy.BugDemo.vo.User;

import java.util.Date;

/**
 * @author yueyue.liu
 */
public class ResponseWrapBuild {

    public static <T>ResponseWrap<T> build(IResult basicEnumInterface, T t){

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
                .userId(user.getId())
                .userName(user.getUsername())
                .data(t)
                .build();
    }

}