package com.cobona.vici.common.persistence.dao;

import com.cobona.vici.common.persistence.model.Metadataviewrelation;
import com.cobona.vici.core.node.ZTreeNode;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 视图关系表 Mapper 接口
 * </p>
 *
 * @author jinchm
 * @since 2018-03-13
 */
public interface MetadataviewrelationMapper extends BaseMapper<Metadataviewrelation> {

	public List<Map<String, Object>> getTableByViewname(@Param("viewname")String  viewname);
	public List<Map<String, Object>> getTablenameByViewname(@Param("viewname")String viewname);
	public List<ZTreeNode> getTablenameTreeByViewname(@Param("viewname")String viewname);
	
}
