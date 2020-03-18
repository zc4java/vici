package com.cobona.vici.modular.metadata.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.service.IService;
import com.cobona.vici.common.persistence.dao.TestMapper;
import com.cobona.vici.common.persistence.model.Metadata;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinchm
 * @since 2018-01-06
 */
public interface IMetadataService extends IService<Metadata> {

	/**
     * 新增表,新增字段，修改表(重命名)，修改字段，删除表，删除字段
     */
   public void createTable(Map tableInfo);
   public void newColumn(Map tableInfo);
   public void alterTable(Map tableInfo);
   public void alterColumn(Map tableInfo);
   public void dropTable(Map tableInfo);
   public void deleteColumn(Map tableInfo);
   public Map jointCondition(Metadata metadata,String operationType);
   public void alterTableComment(Map tableInfo);
   public void alterColumnComment(Map tableInfo);
   public void dropMajorkey(Map tableInfo);
   public void addMajorkey(Map tableInfo);
  
}
