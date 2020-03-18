package com.cobona.vici.common.persistence.dao;

import com.cobona.vici.common.persistence.model.Metadataview;
import com.cobona.vici.core.node.ZTreeNode;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinchm123
 * @since 2018-03-13
 */
public interface MetadataviewMapper extends BaseMapper<Metadataview> {
    public List<Map<String, Object>> selectViewListBycondtion(@Param("condtion")String condtion);
    public List<Map<String, Object>> getTablenameByid(@Param("id")String id);
    public List<Map<String, Object>> getTablenamecnBytablename(@Param("tablename")String tablename);
    public List<Map<String, Object>> getFieldByTablename(@Param("tablename")String tablename);
    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
    public List<Map<String, Object>> getViewnameByid(@Param("id")String id);
	public List<Map<String, Object>> getViewnamecnByViewname(@Param("viewname")String viewname);
	public List<ZTreeNode> getTableidsByTablename(@Param("tablename")String tablename);
}
