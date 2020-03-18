package com.cobona.vici.system;

import com.cobona.vici.common.persistence.dao.UserMapper;
import com.cobona.vici.modular.system.dao.UserMgrDao;
import com.cobona.vici.base.BaseJunit;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * 用户测试
 *
 * @author cobona
 * @date 2017-04-27 17:05
 */
public class UserTest extends BaseJunit {

    @Resource
    UserMgrDao userMgrDao;

    @Resource
    UserMapper userMapper;

    @Test
    public void userTest() throws Exception {

    }

}
