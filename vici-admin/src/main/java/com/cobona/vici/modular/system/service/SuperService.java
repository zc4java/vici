package com.cobona.vici.modular.system.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

public interface SuperService {
	public JSONObject selectById(int id, String tableName,List<String> fieldList);
	public List<JSONObject> selectList(String tableName,String where,List<String> fieldList);
	//public List<JSONObject> selectViewList(String tableName,String where,List<String> fieldList);
	public List<JSONObject> selectListPage(String tableName,String where,Page page,List<String> fieldList);
    public List<JSONObject> selectViewListPage(String tableName, String where, Page page, List<String> fieldList);
    
    public List<JSONObject> selectInitList(String tableName,String where,List<String> fieldList);
	//public List<JSONObject> selectViewList(String tableName,String where,List<String> fieldList);
	public List<JSONObject> selectInitListPage(String tableName,String where,Page page,List<String> fieldList);
    public List<JSONObject> selectInitViewListPage(String tableName, String where, Page page, List<String> fieldList);
	public List<JSONObject> selectSql(String sql);
	public boolean delete(String sql);
	public boolean update(String sql);
	public boolean insert(String sql);

	//public boolean deleteById(int id, String tableName,boolean virtual);
	public boolean delete(String tableName,String where,boolean virtual);
	
	/**
	 * 删除表内的数据，按照主键key进行删除
	 * @param tableName 表名
	 * @param data 数据,可以使JSONArray或者JSONObject类型数据均可
	 * @param key 主键名称
	 * @return 
	 */
	public boolean delete(String tableName,Object data,String key);
	public boolean updateById(JSONObject jsonObject,String tableName,List<String> fieldList);
	public boolean update(JSONObject jsonObject,String tableName,List<String> fieldList,String where);
	public JSONObject add(JSONObject jsonObject, String tableName, List<String> fieldList);
	public boolean addList(List<JSONObject> list, String tableName, List<String> fieldList);
	public Map<String,String> getViewField(String viewName);
	public JSONObject getViewRelation(String viewname);
	//public String getViewRelation(String viewName);
	//public String getViewAddafter(String viewName);
	public JSONObject selectViewById(int id, String viewName, List<String> fieldList);
	//public Map<String, String> getViewRelationTable(String viewName);
	//public boolean updateViewById(JSONObject jsonObject, String tableName, List<String> fieldList);
	/*public boolean deleteViewById(int id, String tableName,boolean virtual);
	public boolean deleteTableByCascade(String tableName, int id,boolean virtual);*/
	public String changecode(String str);
	List<JSONObject> getDictList(String dictName);
	Map<String, String> getDictMap(String dictName);
	List<JSONObject> getFieldList(String tablename);
	List<JSONObject> getShowField(List<JSONObject> fieldlist, String tablename);
	List<JSONObject> initQureyList(List<JSONObject> fieldlist, List<JSONObject> list);
	JSONObject selectInitObjectById(int id, String tableName, List<String> fieldList);
	public String getFieldTempletScript(List<JSONObject> fieldlist);
	public Object doEventhing(int id, JSONObject jsonObject);
	JSONObject selectInitViewById(int id, String tableName, List<String> fieldList);
	boolean delete(int id, String tablename, boolean virtual);
	
	public List<JSONObject> statistic(String tableName, List<JSONObject> groupbyfieldlist, List<JSONObject> fieldlist,  String where, List<JSONObject> orderbyfieldlist );
	public List<JSONObject> statisticView( String tableName, List<JSONObject> groupbyfieldlist, List<JSONObject> fieldlist, String where, List<JSONObject> orderbyfieldlist );
	public JSONObject getdata(String datacode,JSONObject internalParams);
	List<JSONObject> selectViewList(String viewName, String where, List<String> fieldList);
	List<JSONObject> selectInitViewList(String tableName, String where, List<String> fieldList);
	public List<JSONObject> selectFieldAdd(List<JSONObject> fieldlist, String string);
	JSONObject saveOrUpdate(JSONObject jsonObject, String tableName, List<String> fieldList);
	
	public Object getSelectMapObj(String key,Map<String, Object> map);
	/**
	 * 批量插入每1000条执行一次
	 * 
	 * @param list 数据
	 * @param tableName 表名
	 * @param fieldList 最终要添加的字段 
	 * 		 如果fieldList为空则取tablename对应的metadata字段，
	 * 		 如果metadata也为空则取第一条数据的keySet()
	 * @param excludeFieldList 不做添加的字段   这里的字段从fieldList去除掉
	 * @return 
	 */
	boolean addList(List<JSONObject> list, String tableName, List<String> fieldList, List<String> excludeFieldList);
	/**
	 * 批量插入每1000条执行一次
	 * 
	 * @param list 数据
	 * @param tableName 表名
	 * @return 
	 */
	boolean addList(List<JSONObject> list, String tableName);
}
