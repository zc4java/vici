package com.cobona.vici.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cobona.vici.common.persistence.model.Metadata;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinchm
 * @since 2018-01-06
 */
public interface MetadataMapper extends BaseMapper<Metadata> {

	
	  /**
     * 根据表名和字段，创建sql
     * @param newTableName 表名
     * @param tableBody 字段主体
     * @param tableComm 表注释
     *  create table ${newTableName}(
        ${tableBody}
        )comment='${tableComm}';
     */
    public void createTable(@Param("newTableName") String newTableName,
            @Param("tableBody") String tableBody,@Param("tableComm") String tableComm);
    /**
     * 新增列
     * @param tableName 表名
     * @param tableBody 表主体
     *  ALTER TABLE  ${tableName} ${tableBody};
     */
    public void newColumn(@Param("tableName") String tableName,
            @Param("tableBody") String tableBody);
    /**
     * 修改表名
     * @param oldtableName 旧表名
     * @param newtablename 新表名
     * alter table ${oldtableName} rename ${newtablename};
     */
    public void alterTable(@Param("oldtableName") String tableName,
            @Param("newtablename") String tableBody);
    /**
     * 修改表注释
     * @param tableName 表名
     * @param tableBody 注释
     * ALTER TABLE  ${tableName} comment='${tableBody}';
     */
    public void alterTableComment(@Param("tableName") String tableName,
            @Param("tableBody") String tableBody);
    /**
     * 修改表属性
     * @param tableName
     * @param tableBody
     * alter table ${tableName}  ${tableBody};
     */
    public void alterColumn(@Param("tableName") String tableName,
            @Param("tableBody") String tableBody);
    /**
     * 修改表注释
     * @param tableName
     * @param tableBody
     * alter table ${tableName}  modify column ${tableBody};
     */
    public void alterColumnComment(@Param("tableName") String tableName,
            @Param("tableBody") String tableBody);
    /**
     * drop表
     * @param tableName
     * drop table ${tableName};
     */
    public void dropTable(@Param("tableName") String tableName);
    /**
     * 删除表中的列，支持多列删除。
     * @param tableName 表名
     * @param tableBody 删除的列
     * alter table ${tableName}  ${tableBody};
     */
    public void deleteColumn(@Param("tableName") String tableName,
            @Param("tableBody") String tableBody);
    /**
     * 更新元数据下的相同名称的表名
     * @param oldtablename 旧表名
     * @param newtablename 新表名
     * update  metadata set tablename = '${newtablename} ' where tablename = '${oldtablename}';
     */
    public void updateMetadatatablename(@Param("oldtablename") String tableName,
            @Param("newtablename") String tableBody);
    /**
     * 更新元数据下的相同名称的表注释
     * @param tableName 表名
     * @param newtablecomment 新注释
     * update  metadata set tablenamecn = '${newtablecomment}'  where tablename = '${tableName}';
     */
    public void updateMetadatatablenameComment(@Param("tableName") String tableName,
            @Param("newtablecomment") String tableBody);
    /**
     * 删除表内所有主键
     * @param tableName 表名
     *alter table ${tableName} drop primary key;
     */
    public void dropMajorkey(@Param("tableName") String tableName);
    /**
     * 新增主键
     * @param tableName 表名
     * @param majorkey  主键的列
     * alter table ${tableName} add primary key(${majorkey});
     */
    public void addMajorkey(@Param("tableName") String tableName,
            @Param("majorkey") String majorkey);
    
    public List<Map<String, Object>> getTablenameByid(@Param("id")String id);
    /**
     *从元数据表获取表之间的关联关系
     */
    public List<Map<String, Object>> getTableRelation();
    /**
     *从元数据表获取表之间的关联关系
     */
    public List<Map<String, Object>> getTablenameAndIdMap();
    /**
     *获取是否级联删除下级节点
     * @param tableName 表名
     * @param majorkey  主键的列
     */
    public List<Map<String, Object>> getTablenameIsdelcascade(@Param("tablename")String tablename);
}
