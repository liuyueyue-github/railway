package com.liuhappy.BugDemo.exception;

import com.liuhappy.BugDemo.common.ResponseWrap;
import com.liuhappy.BugDemo.common.ResponseWrapBuild;
import com.liuhappy.BugDemo.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yueyue.liu
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseWrap customException(BusinessException e) {

		log.error("【系统异常】{}",e.getMessage(),e);

		ErrorCode errorCode = e.getErrorCode();

		return ResponseWrapBuild.buildBusinessFail(errorCode,"");
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseWrap exception(Exception e) {

		log.error("【系统异常】{}",e.getMessage(),e);

		return ResponseWrapBuild.buildRuntimeExceptionFail(getMsg(e),"");
	}

	private static String getMsg(Exception e) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(e.toString()).append("\r\n");
		for (StackTraceElement stackTraceElement : stackTrace) {
			sb.append("\tat ").append(stackTraceElement.getClassName()).append(".")
					.append(stackTraceElement.getMethodName())
					.append("(").append(stackTraceElement.getFileName()).append(":").append(stackTraceElement.getLineNumber())
					.append(")\r\n");
		}
		return sb.toString();
	}
}
