package com.cobona.vici.core.util;

import com.cobona.vici.config.properties.ViciProperties;
import com.cobona.vici.core.util.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     *
     * @author cobona
     * @Date 2017/5/23 22:34
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(ViciProperties.class).getKaptchaOpen();
    }
}