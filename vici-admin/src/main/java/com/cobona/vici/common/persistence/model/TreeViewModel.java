package com.cobona.vici.common.persistence.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
/***
 * 改造结构，适应图模式构造模型。并且为了满足生成JSON格式要求， 将字段重新规划
 * @author ethanzhao
 * @version V2.0
 *
 */
public class TreeViewModel {

	/***
	 * 视图名称
	 */
	private String viewName;
	
	/***
	 * 视图中主表名称
	 */
	private String mainTableName;
	
	/***
	 * 每个表对应的字段
	 */
	@JSONField(serialize=false)
	private Map<String,List<JSONObject>> tableFields = new  HashMap<String,List<JSONObject>>();
	
	/***
	 * 视图所有字段
	 */
	@JSONField(serialize=false)
	private List<JSONObject> viewAllFields;
	/***
	 * 主表的所有字段
	 */
	private List<JSONObject> fields;
	
	/***
	 * 子表以及与子表关系
	 */
	private List<TreeViewChildModel> relations;

	
	private boolean hasRelations = false;
	
	
	public boolean isHasRelations() {
		return hasRelations;
	}

	public void setHasRelations(boolean hasRelations) {
		this.hasRelations = hasRelations;
	}

	public List<TreeViewChildModel> getRelations() {
		return relations;
	}

	public void setRelations(List<TreeViewChildModel> relations) {
		this.relations = relations;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getMainTableName() {
		return mainTableName;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}


	public List<JSONObject> getFields() {
		return fields;
	}

	public void setFields(List<JSONObject> fields) {
		this.fields = fields;
	}

	public List<JSONObject> getViewAllFields() {
		return viewAllFields;
	}

	public void setViewAllFields(List<JSONObject> viewAllFields) {
		this.viewAllFields = viewAllFields;
	}

	public Map<String, List<JSONObject>> getTableFields() {
		return tableFields;
	}

	public void setTableFields(Map<String, List<JSONObject>> tableFields) {
		this.tableFields = tableFields;
	}
	
	
}
