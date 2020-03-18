package com.cobona.vici.core.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cobona.vici.core.base.tips.ErrorTip;
import com.cobona.vici.core.exception.ViciException;
import com.cobona.vici.core.exception.ViciExceptionEnum;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author cobona
 * @date 2016年11月12日 下午3:19:56
 */
public class BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     *
     * @author cobona
     */
    @ExceptionHandler(ViciException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Pointcut(value = "!execution(public * com.cobona.vici.jicheng.controller.WebSocketController.*(..))")
    public ErrorTip notFount(ViciException e) {
        log.error("业务异常:", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     *
     * @author cobona
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Pointcut(value = "!execution(public * com.cobona.vici.jicheng.controller.WebSocketController.*(..))")
    public ErrorTip notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return new ErrorTip(ViciExceptionEnum.SERVER_ERROR.getCode(), ViciExceptionEnum.SERVER_ERROR.getMessage());
    }

}
