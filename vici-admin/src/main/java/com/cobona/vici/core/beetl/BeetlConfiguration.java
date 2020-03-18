package com.cobona.vici.core.beetl;

import com.cobona.vici.core.util.KaptchaUtil;
import com.cobona.vici.core.util.ToolUtil;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {

        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());

    }

}
