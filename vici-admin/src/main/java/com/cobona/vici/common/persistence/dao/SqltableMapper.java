package com.cobona.vici.common.persistence.dao;

import com.cobona.vici.common.persistence.model.Sqltable;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinchm1123
 * @since 2018-09-20
 */
public interface SqltableMapper extends BaseMapper<Sqltable> {

	List<Map<String, Object>> selectViewListBycondtion( @Param("condtion") String condtion);

}
