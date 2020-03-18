package com.cobona.vici.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.dao.DataAccessException;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cobona.vici.common.persistence.model.Generaltable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinchm
 * @since 2018-01-07
 */
public interface GeneraltableMapper extends BaseMapper<Generaltable> {
	
	 public List<Map<String, Object>> getOneTableList(@Param("tablename")String tablename,
			 @Param("columns")String columns,@Param("condition") String condition,@Param("othercondition") String othercondition);
	 public void insertOne(@Param("tablename")String tablename,@Param("insertset") String insertset);
	 public void updateOne(@Param("tablename")String tablename,@Param("updateset") String updateset,@Param("updatecondition") String updatecondition);
	 public void deleteOne(@Param("tablename")String tablename,@Param("delcondition") String delcondition);

}
