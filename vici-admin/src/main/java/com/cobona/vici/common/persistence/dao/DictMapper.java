package com.cobona.vici.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cobona.vici.common.persistence.model.Dict;

/**
 * <p>
  * 字典表 Mapper 接口
 * </p>
 *
 * @author cobona
 * @since 2017-07-11
 */
public interface DictMapper extends BaseMapper<Dict> {
	 public List<Map<String, Object>> getDictByname(@Param("name")String name);
}