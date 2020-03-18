package com.cobona.vici.modular.system.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cobona.vici.common.persistence.model.TreeViewModel;

/***
 * TreeViewmode 需要的接口函数
 * @author ethanzhao
 *
 */
public interface ITreeViewService {
		/***
		 * 根据view模型返回该模型的主表表名
		 * @param viewModelName
		 * @return 主表名称
		 */
		public String getMainTableInViewModel(String viewModelName);
		
		/***
		 * 根据表名获取模型中表名的字段信息
		 * @param viewModelName 视图名称
		 * @param tableName 表名
		 * @return 返回字段结果集
		 */
		public List<JSONObject> getFieldsInViewModelByTableName(String viewModelName,String tableName);
		
		/***
		 * 
		 * @param viewModelName
		 * @return
		 */
		public TreeViewModel getTreeViewModel(String viewModelName);
		/***
		 * 获取view模型中某个表的所有字段
		 * @param model
		 * @param tableName
		 * @return
		 */
		public List<JSONObject> getTreeViewModelFields(TreeViewModel model, String tableName);
		
		/***
		 * 该函数理论上不应该放到模型这里，应该放到数据操作中。
		 * 主要是取得viewmodelname的数据，作为平铺数据展示出来，适合列表展示
		 * @param viewModelName
		 * @param where
		 * @return
		 */
		public List<JSONObject> getDataListAsTable(String viewModelName,String where);
		
		/***
		 * 按照树形结构展示数据
		 * @param viewModelName
		 * @param where
		 * @return
		 */
		public List<JSONObject> getDataListAsTree(String viewModelName,String where);
		
		/***
		 * 根据配置文件获取基础的查询语句
		 * @param viewModelName 视图名称
		 * @return
		 */
		public String getBaseSQL(String viewModelName);
}
