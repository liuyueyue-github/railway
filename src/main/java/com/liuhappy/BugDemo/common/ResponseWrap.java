package com.liuhappy.BugDemo.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yueyue.liu
 * @Description 返回结果
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrap<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    private String userId;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "UTC+8")
    private Date operationTime;

}
