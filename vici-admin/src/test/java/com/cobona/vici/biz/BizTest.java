package com.cobona.vici.biz;

import com.cobona.vici.modular.biz.service.ITestService;
import com.cobona.vici.base.BaseJunit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务测试
 *
 * @author cobona
 * @date 2017-06-23 23:12
 */
public class BizTest extends BaseJunit {

    @Autowired
    ITestService testService;

    @Test
    public void test() {
        //testService.testVici();

        testService.testBiz();

        //testService.testAll();
    }
}
