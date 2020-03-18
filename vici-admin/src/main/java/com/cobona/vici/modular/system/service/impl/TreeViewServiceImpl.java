package com.cobona.vici.modular.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cobona.vici.common.persistence.model.RelationEnum;
import com.cobona.vici.common.persistence.model.TreeViewChildModel;
import com.cobona.vici.common.persistence.model.TreeViewModel;
import com.cobona.vici.common.persistence.model.TreeViewRelation;
import com.cobona.vici.modular.system.dao.SuperDao;
import com.cobona.vici.modular.system.service.ITreeViewService;
import com.cobona.vici.modular.system.service.SuperService;
@Service
public class TreeViewServiceImpl  implements ITreeViewService{

	 @Autowired
	 private SuperService superService;
	 @Resource
	public SuperDao superDao;
	 
	public static Map<String,TreeViewModel> treeViewModelCache = new HashMap<String,TreeViewModel>();
	@Override
	public String getMainTableInViewModel(String viewModelName) {
		TreeViewModel model = getTreeViewModel(viewModelName);
		return model.getMainTableName();
	}

	@Override
	public TreeViewModel getTreeViewModel(String viewModelName) {
		if(treeViewModelCache.get(viewModelName) != null){
			return treeViewModelCache.get(viewModelName);
		}else{
			TreeViewModel root = new TreeViewModel();
			root.setViewName(viewModelName);
			List<Map<String, Object>> result = superDao.selectList("select * from metadatatreerelation where viewname='" + viewModelName + "'");
			for(Map<String, Object> row : result){
				if(row.get("parenttablename") == null){
					root.setMainTableName(row.get("tablename").toString());
					result.remove(row);
					break;
				}
			}
			root.setViewAllFields(superService.getFieldList(root.getViewName()));
			recurseCreateTreeView(root,root,result);
			return root;
		}
		
	}
	/***
	 * 循环生成TreeViewModel模型的结构
	 * @param root 根节点或者初始节点，始终传递该节点
	 * @param node 当前根节点
	 * @param result 结果集
	 * 
	 * @author ethanzhao
	 */
	private void recurseCreateTreeView(TreeViewModel root,TreeViewModel node,List<Map<String, Object>> result){
		
		node.setFields(getTreeViewModelFields(root,node.getMainTableName()));
		Map<String, List<JSONObject>> tableFields = root.getTableFields();
		tableFields.put(node.getMainTableName(), node.getFields());
		List<TreeViewChildModel> nodeRelations = node.getRelations();
		if(nodeRelations == null)
			nodeRelations = new ArrayList<TreeViewChildModel>();
		
		for(Map<String, Object> row : result){
			if(row.get("parenttablename") !=null && 
					StringUtils.equals(row.get("parenttablename").toString(), node.getMainTableName())){
				node.setHasRelations(true);
				TreeViewChildModel childnode = new TreeViewChildModel();
				TreeViewModel relationNode = new TreeViewModel();
				relationNode.setMainTableName(row.get("tablename").toString());
				childnode.setRelationNode(relationNode);
				
				TreeViewRelation relation = new TreeViewRelation();
				relation.setColumn(row.get("joinfield").toString());
				relation.setParentColumn(row.get("parentjoinfield").toString());
				relation.setParentTableName(row.get("parenttablename").toString());
				relation.setRelationName(RelationEnum.valueOf(row.get("joinrelation").toString()));
				childnode.setRef(relation);
				nodeRelations.add(childnode);
				
				node.setRelations(nodeRelations);
				
				//result.remove(row);
				recurseCreateTreeView(root,relationNode,result);
			}
		}
	}

	@Override
	public List<JSONObject> getTreeViewModelFields(TreeViewModel root, String tableName) {
		List<JSONObject> allFields = null;
		if(root.getViewAllFields() == null){
			root.setViewAllFields(superService.getFieldList(root.getViewName()));
		}
		allFields = root.getViewAllFields();
		List<JSONObject> newFields = new ArrayList<JSONObject>();
		for(JSONObject row : allFields){
			if(StringUtils.equals(row.getString("tablenamectual"), tableName)){
				newFields.add(row);
			}
		}
		return newFields;
	}

	@Override
	public List<JSONObject> getDataListAsTable(String viewModelName, String where) {
		String sql = getBaseSQL(viewModelName);
		sql +=" where 1=1 ";
		sql += where;
		List<JSONObject> list = new ArrayList<>();
		List<Map<String, Object>> mapList = superDao.selectList( sql);
		JSONObject JSONObject = null;
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, superService.getSelectMapObj(key, map));
					}
				}
				list.add(JSONObject);
			}
		}
		return list;
	}

	@Override
	public List<JSONObject> getDataListAsTree(String viewModelName, String where) {
		List<JSONObject> rs = getDataListAsTable(viewModelName,where);
		TreeViewModel model = getTreeViewModel(viewModelName);
		JSONObject resultObject = new JSONObject();
		recurseCreateInitTreeResult(model,resultObject);
		
		List<JSONObject> treeResults = new ArrayList<JSONObject>();
		//循环结果
		for(JSONObject row : rs){
			Iterator<String> it= resultObject.keySet().iterator();
			while(it.hasNext()){
				String tableName = it.next();
				JSONArray table_rs = (JSONArray)resultObject.get(tableName);
				List<JSONObject> fields = getFieldsInViewModelByTableName(viewModelName,tableName);
				JSONObject sub_row = new JSONObject();
				if(!isExistIdentifyByKey(table_rs,"UUID",row.get(tableName+"_UUID"))){
					for(JSONObject fieldInfo : fields){
						sub_row.put(fieldInfo.get("field").toString(), row.get(fieldInfo.get("aliasfield"))) ;
					}
					table_rs.add(sub_row);
					resultObject.put(tableName, table_rs);
				}
			}
		}
		treeResults.add(resultObject);
		return treeResults;
	}

	/***
	 * 判断数组中是否存在key为value的对象
	 * @param dataRows 所有数据集合
	 * @param key 判断的主键
	 * @param value 数值
	 * @return
	 */
	private boolean isExistIdentifyByKey(JSONArray dataRows,String key,Object value){
		for(int i=0;i<dataRows.size();i++){
			JSONObject data = dataRows.getJSONObject(i);
			if(data.get(key).equals(value) ){
				return true;
			}
		}
		return false;
	}
	@Override
	public String getBaseSQL(String viewModelName) {
		TreeViewModel model = getTreeViewModel(viewModelName);
		String baseSql = "select %s from %s ";
		String baseSql_other ="";
		String mainTableName = model.getMainTableName();
		String fieldStr = superService.getViewField(viewModelName).get("field");
		baseSql = String.format(baseSql, fieldStr,mainTableName);
		baseSql_other = recurseCreateSQL(model);
		baseSql = baseSql + baseSql_other;
		return baseSql;
	}
	/***
	 * 递归生成sql语句
	 * @param model
	 * @param sql
	 * @return
	 */
	private String  recurseCreateSQL(TreeViewModel model){
		List<TreeViewChildModel>relations = model.getRelations();
		if(model.isHasRelations()){
			String sql = "";
			for(TreeViewChildModel relation : relations){
				TreeViewModel m = relation.getRelationNode();
				sql += " left join " + m.getMainTableName() + " on " +
						m.getMainTableName()+"." + relation.getRef().getColumn()  + "=" +
						model.getMainTableName() + "." + relation.getRef().getParentColumn();
				sql += recurseCreateSQL(m);
			}
			return sql;
		}
		return "";
	}
	
	private void recurseCreateInitTreeResult(TreeViewModel model,JSONObject initResult){
		if(!initResult.containsKey(model.getMainTableName()))
			initResult.put(model.getMainTableName(), new JSONArray());
		List<TreeViewChildModel>relations = model.getRelations();
		if(model.isHasRelations()){
			for(TreeViewChildModel relation : relations){
				TreeViewModel m = relation.getRelationNode();
				initResult.put(m.getMainTableName(), new JSONArray());
				recurseCreateInitTreeResult(m,initResult);
			}
		}
	}
	

	@Override
	public List<JSONObject> getFieldsInViewModelByTableName(String viewModelName,String tableName) {
		TreeViewModel model = this.getTreeViewModel(viewModelName);
		return model.getTableFields().get(tableName);
	}
}
