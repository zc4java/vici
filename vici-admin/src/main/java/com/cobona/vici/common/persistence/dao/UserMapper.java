package com.cobona.vici.common.persistence.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cobona.vici.common.persistence.model.User;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author cobona
 * @since 2017-07-11
 */
public interface UserMapper extends BaseMapper<User> {
	  /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

}