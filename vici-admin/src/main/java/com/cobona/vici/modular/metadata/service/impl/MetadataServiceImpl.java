package com.cobona.vici.modular.metadata.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cobona.vici.common.persistence.dao.MetadataMapper;
import com.cobona.vici.common.persistence.model.Metadata;
import com.cobona.vici.modular.metadata.service.IMetadataService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinchm
 * @since 2018-01-06
 */
@Service
public class MetadataServiceImpl extends ServiceImpl<MetadataMapper, Metadata> implements IMetadataService {
	   
   @Autowired
   MetadataMapper metadataMapper ;
  
   /*
	 * 新建表。
	 * metadataMapper.createTable("testuser", "  id varchar(32) NOT NULL COMMENT '唯一id',PRIMARY KEY (id)","测试用户表");
	 */
   @Override
	public void createTable(Map tableInfo) {
	       metadataMapper.createTable(tableInfo.get("tablename").toString(),tableInfo.get("tableBody").toString(),tableInfo.get("tableComm").toString());
	};
	/*
	 * 新增列。
	 * metadataMapper.newColumn("testuser", " ADD COLUMN name  varchar(32) ,ADD COLUMN sex  varchar(5) ");
	 */  
	@Override
	public void newColumn(Map tableInfo) {
           metadataMapper.newColumn(tableInfo.get("tablename").toString(),tableInfo.get("tableBody").toString());
	};
	/*
	 * 表名改变，还需要更改元数据表的相关数据，包含表名
	 * metadataMapper.alterTable("testuser", "testuser1");
	 */
	@Override
	public void alterTable(Map tableInfo) {
		   metadataMapper.alterTable(tableInfo.get("oldtablename").toString(), tableInfo.get("newtablename").toString());
			//更新元数据下所有的 表名
		   metadataMapper.updateMetadatatablename(tableInfo.get("oldtablename").toString(), tableInfo.get("newtablename").toString());
	};
	/*
	 * 表注释改变，还需要更改元数据表的相关数据，
	 * metadataMapper.alterTableComment("testuser", "aaa");
	 */
	@Override
	public void alterTableComment(Map tableInfo) {
		   metadataMapper.alterTableComment(tableInfo.get("tablename").toString(), tableInfo.get("newtablecomment").toString());
			//更新元数据下所有的 表注释
		   metadataMapper.updateMetadatatablenameComment(tableInfo.get("tablename").toString(), tableInfo.get("newtablecomment").toString());
	};
	/*
	 * 变更表字段，要变更所有的 表字段名称，
	 * metadataMapper.alterColumn("testuser", " CHANGE COLUMN `name` `name1`  varchar(11),CHANGE COLUMN `sex` `sex1`  varchar(11) ");
	 */
	 @Override
	 public void alterColumn(Map tableInfo) {
		    metadataMapper.alterColumn(tableInfo.get("tablename").toString(),tableInfo.get("tableBody").toString());
	 };
	 /*
	  * 变更表字段注释
	  * metadataMapper.alterColumnComment("testuser", " field_name int comment '修改后的字段注释'");
	  */
	@Override
    public void alterColumnComment(Map tableInfo) {
		   metadataMapper.alterColumnComment(tableInfo.get("tablename").toString(),tableInfo.get("tableBody").toString());
	};
	 /*
	  * drop表
	  * metadataMapper.dropTable("testuser");
	  */
	@Override
	public void dropTable(Map tableInfo) {
		   metadataMapper.dropTable(tableInfo.get("tablename").toString());
	};
	/*
	  * 删除表字段
	  * metadataMapper.deleteColumn("testuser", " DROP COLUMN name,DROP COLUMN sex");
	  */
	@Override
	public void deleteColumn(Map tableInfo) {
			metadataMapper.deleteColumn(tableInfo.get("tablename").toString(),tableInfo.get("tableBody").toString());
	};
	/*
	  * 清除主键
	  * 
	  */
	@Override
    public void dropMajorkey(Map tableInfo) {
		   metadataMapper.dropMajorkey(tableInfo.get("tablename").toString());
	};
	/*
	  * 增加主键
	  * 
	  */
	@Override
	public void addMajorkey(Map tableInfo) {
		   metadataMapper.addMajorkey(tableInfo.get("tablename").toString(),tableInfo.get("majorkey").toString());
	};
	/*
	  * 拼接条件
	  * 
	  */
	@Override
	public Map jointCondition(Metadata metadata,String operationType) {
			 HashMap<String,String> addconditionMap=new HashMap();
			 String addcondtionTablebody="";
				if(operationType.equals("createtable")) {
			           addconditionMap.put("tablename", metadata.getTablename());
			        	//"  id varchar(32) NOT NULL COMMENT '唯一id',PRIMARY KEY (id)"
			        	addcondtionTablebody = metadata.getField()+" "+metadata.getFieldtype();
			        	if(metadata.getFieldtype().equals("numeric")||metadata.getFieldtype().equals("double")||metadata.getFieldtype().equals("decimal")) {
			        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+","+metadata.getDecimalpoint()+")";
			        	}else if(!(metadata.getFieldtype().equals("datetime")||metadata.getFieldtype().equals("date")||metadata.getFieldtype().equals("text")||metadata.getFieldtype().equals("longtext"))) {
			        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+")";
			        	}
			        	if(metadata.getIsnullable().equals("1")) {
			        		addcondtionTablebody=addcondtionTablebody+" not null";
			        	}else {
			        		addcondtionTablebody=addcondtionTablebody+" null ";
			        	}
			        	if(metadata.getIsmajorkey().equals("1")) {
			        		addcondtionTablebody=addcondtionTablebody+" AUTO_INCREMENT "+" COMMENT '"+metadata.getTitle()+"' ,PRIMARY KEY ("+metadata.getField()+")";
			        	}else {
			        		addcondtionTablebody=addcondtionTablebody+" COMMENT '"+metadata.getTitle()+"' ";
			        	}
			        	addconditionMap.put("tableBody", addcondtionTablebody);
			        	addconditionMap.put("tableComm", metadata.getTablenamecn());
	        	}
				if(operationType.equals("newColumn")) {
			           addconditionMap.put("tablename", metadata.getTablename());
			        	//"  ADD COLUMN name  varchar(32) ,ADD COLUMN sex  varchar(5)"
			        	addcondtionTablebody = "add COLUMN "+metadata.getField()+" "+metadata.getFieldtype();
			        	if(metadata.getFieldtype().equals("numeric")||metadata.getFieldtype().equals("double")||metadata.getFieldtype().equals("decimal")) {
			        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+","+metadata.getDecimalpoint()+")";
			        	}else if(!(metadata.getFieldtype().equals("datetime")||metadata.getFieldtype().equals("date")||metadata.getFieldtype().equals("text")||metadata.getFieldtype().equals("longtext"))) {
			        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+")";
			        	};
			        	
			        	if(metadata.getIsnullable().equals("1")) {
			        		addcondtionTablebody=addcondtionTablebody+" not null ";
			        	}else {
			        		addcondtionTablebody=addcondtionTablebody+" null ";
			        	};
			        	//暂不支持复核主键和新增主键列
			        	addcondtionTablebody=addcondtionTablebody+" COMMENT '"+metadata.getTitle()+"' ";
			        	if(!metadata.getDefaultvalue().equals("")) {
			        		addcondtionTablebody=addcondtionTablebody+" DEFAULT '"+metadata.getDefaultvalue()+"' ";
			        	}
			        	
			        	addconditionMap.put("tableBody", addcondtionTablebody);
	        	}
				if(operationType.equals("deleteColumn")) {
			           addconditionMap.put("tablename", metadata.getTablename());
			        	//" DROP COLUMN `name`;"
			        	addcondtionTablebody = "DROP COLUMN "+metadata.getField()+" ";
			            addconditionMap.put("tableBody", addcondtionTablebody);
	        	}
        	
        	   return addconditionMap;
			};
			
}
