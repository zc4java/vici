package com.cobona.vici.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.cobona.vici.common.persistence.model.OperationLog;
import com.cobona.vici.common.persistence.model.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * dao
 *
 * @author zhangchao
 * @Date 2017/12/16 23:44
 */
public interface SuperDao {

	public static  enum KEYTYPE {
		INT,STRING
	}
    /**
     * 获取分页列表
     *
     */
    List<Map<String, Object>> selectListPage(@Param("page") Page<OperationLog> page, @Param("sql") String sql);
    
    List<Map<String, Object>> selectViewListPage(@Param("page") Page<OperationLog> page, @Param("sql") String sql);
    
    /**
     * 获取列表不分页
     *
     */
    List<Map<String, Object>> selectList(@Param("sql") String sql);
    
    
    void delete(@Param("sql") String sql);
    
    void update(@Param("sql") String sql);
    
    void insert(@Param("sql") String sql);
    
    int insertAndGetId(@Param("sql") String sql);
    /***
     * 批量删除表内数据
     * @param tablename 表名
     * @param key  主键字段
     * @param keytype 主键类型，参考KEYTYPE
     * @param keys 主键值
     * @return
     */
    int deleteListByKey(@Param("tablename") String tablename,@Param("keyfield") String key,
    		@Param("keyType") KEYTYPE keytype,
    		@Param("keys") List<Object> keys);
    
    //void batchInsert(@Param("tablename") String tablename,@Param("data") List<Object> data);
}
