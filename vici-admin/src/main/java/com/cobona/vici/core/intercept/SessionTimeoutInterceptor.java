package com.cobona.vici.core.intercept;

import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.shiro.ShiroKit;
import com.cobona.vici.core.support.HttpKit;

import org.apache.shiro.session.InvalidSessionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 验证session超时的拦截器
 *
 * @author cobona
 * @date 2017年6月7日21:08:48
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "vici", name = "session-open", havingValue = "true")
public class SessionTimeoutInterceptor extends BaseController {

    @Pointcut("execution(* com.cobona.vici.*..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionTimeoutValidate(ProceedingJoinPoint point) throws Throwable {

        String servletPath = HttpKit.getRequest().getServletPath();

        if (servletPath.equals("/kaptcha") || servletPath.equals("/login") || servletPath.equals("/global/sessionError")) {
            return point.proceed();
        }else{
            if(ShiroKit.getSession().getAttribute("sessionFlag") == null){
                ShiroKit.getSubject().logout();
                throw new InvalidSessionException();
            }else{
                return point.proceed();
            }
        }
    }
}
