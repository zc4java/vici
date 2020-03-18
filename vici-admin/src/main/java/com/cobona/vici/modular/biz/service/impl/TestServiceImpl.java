package com.cobona.vici.modular.biz.service.impl;

import com.cobona.vici.common.constant.DSEnum;
import com.cobona.vici.common.persistence.dao.TestMapper;
import com.cobona.vici.common.persistence.model.Test;
import com.cobona.vici.core.mutidatasource.annotion.DataSource;
import com.cobona.vici.modular.biz.service.ITestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试服务
 *
 * @author cobona
 * @date 2017-06-23 23:02
 */
@Service
public class TestServiceImpl implements ITestService {


    @Autowired
    TestMapper testMapper;

    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    public void testBiz() {
        Test test = testMapper.selectById(1);
        test.setId(22);
        test.insert();
    }


    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_VICI)
    public void testVici() {
        Test test = testMapper.selectById(1);
        test.setId(33);
        test.insert();
    }

    @Override
    @Transactional
    public void testAll() {
        testBiz();
        testVici();
        //int i = 1 / 0;
    }

}
