package com.cobona.vici.common.exception;

import com.cobona.vici.core.exception.ViciException;

/**
 * @author cobona
 * @Description 业务异常的封装
 * @date 2016年11月12日 下午5:05:10
 */
public class BussinessException extends ViciException {

	public BussinessException(BizExceptionEnum bizExceptionEnum) {
		super(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(), bizExceptionEnum.getUrlPath());
	}
}
