package com.cobona.vici.modular.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cobona.vici.common.constant.DBModelConst;
import com.cobona.vici.common.persistence.dao.DictMapper;
import com.cobona.vici.common.persistence.dao.MetadataMapper;
import com.cobona.vici.common.persistence.model.Metadata;
import com.cobona.vici.config.properties.ViciProperties;
import com.cobona.vici.core.shiro.ShiroKit;
import com.cobona.vici.core.support.HttpKit;
import com.cobona.vici.core.util.ToolUtil;
import com.cobona.vici.modular.metadata.service.IMetadataService;
import com.cobona.vici.modular.system.dao.SuperDao;
import com.cobona.vici.modular.system.service.SuperService;

import cn.afterturn.easypoi.entity.ImageEntity;

@Service
public class SuperServiceImpl implements SuperService {

	@Resource
	public SuperDao superDao;
	@Resource
	private ViciProperties viciProperties;
	@Value("${vici.cache-field}")
	private boolean cacheField; // 是否开启字段缓存，(true/false)
	private Map<String, List<JSONObject>> fieldlistCache;
	private Map<String, List<JSONObject>> dictCache;
	private Map<String, Map<String, String>> dictMap;
	private Map<String, JSONObject> viewRelationCache;
	private Map<String,  List<JSONObject>> viewTreeRelationCache;
	private Map<String, Map<String, String>> viewFieldlistCache;
	// 元数据管理中所有到id和pid之间到对应关系到map，以("0","1,2,3") 存储
	private Map<String, String> tableRrelationMap;
	// 元数据管理中表名和id之间的关系
	private Map<String, String> tablenameAndIdMap;
	@Autowired
	private IMetadataService metadataService;
	@Resource
	MetadataMapper metadataMapper;

	@Resource
	DictMapper dictMapper;
	/*
	 * private Map<String, Map<String,String>> viewRelationTableCache; private
	 * Map<String, String> viewRelationCache; private Map<String, String>
	 * viewAddafterCache;
	 */
	// private Map<String, Map<String,String>> dictMap;

	@Override
	public JSONObject selectById(int id, String tableName, List<String> fieldList) {
		JSONObject JSONObject = null;
		String fieldStr = fieldList2Str(fieldList);
		String sql = "select " + fieldStr + " from  " + tableName + " where id =" + id;
		List<Map<String, Object>> mapList = superDao.selectList(sql);
		if (!mapList.isEmpty()) {
			JSONObject = new JSONObject();
			Map<String, Object> map = mapList.get(0);
			for (String key : map.keySet()) {
				JSONObject.put(key, getSelectMapObj(key, map));
			}
		}
		return JSONObject;
	}

	@Override
	public JSONObject selectInitObjectById(int id, String tableName, List<String> fieldList) {
		JSONObject JSONObject = this.selectById(id, tableName, fieldList);
		List<JSONObject> fieldlist = getFieldList(tableName);
		JSONObject = doInit(JSONObject, fieldlist);
		return JSONObject;
	}
	@Override
	public JSONObject selectInitViewById(int id, String tableName, List<String> fieldList) {
		JSONObject JSONObject = this.selectViewById(id, tableName, fieldList);
		List<JSONObject> fieldlist = getFieldList(tableName);
		JSONObject = doInit(JSONObject, fieldlist);
		return JSONObject;
	}

	public JSONObject doInit(JSONObject JSONObject, List<JSONObject> fieldlist) {
		
		for (JSONObject jsonObject : fieldlist) {
			String dict = jsonObject.getString("dict");
			String inputtype=jsonObject.getString("inputtype");
			
			if (!StringUtils.isEmpty(inputtype)) {
				if(inputtype.equals("editor")) {
					String value=JSONObject.getString(jsonObject.getString("field"));
					if (!StringUtils.isEmpty(value)) {
						value=value.replaceAll("</?[^>]+>", "");
						JSONObject.put(jsonObject.getString("field") + "_simple", value);
						
					}
				}
			}
			
			if (!StringUtils.isEmpty(dict)) {
				if (dict.equals("JSON")) {

					String value = jsonObject.getString(JSONObject.getString(jsonObject.getString("field")));

					if (StringUtils.isNotEmpty(value)) {
						JSONObject.put(jsonObject.getString("field") + "_JSON", JSONObject.parse(value));
					}

				} else {
					Map<String, String> dictMap = getDictMap(dict);
					// System.out.println(jsonObject.getString("field")+"_show:"+jsonObject.getString("field")+jsonObject2.getString(jsonObject.getString("field"))+dictMap.get(jsonObject2.getString(jsonObject.getString("field"))));
					String show = dictMap.get(JSONObject.getString(jsonObject.getString("field")));
					if (null == show) {
						show = "";
					}
					JSONObject.put(jsonObject.getString("field") + "_show", show);
				}

			}
			if (jsonObject.containsKey("viewid")) {// view添加唯一id
				int viewid = jsonObject.getIntValue("viewid");
				if (viewid == 1) {

					JSONObject.put("id", JSONObject.get(jsonObject.getString("field")));

				}

			}
		}
		return JSONObject;
	}

	@Override
	public List<JSONObject> initQureyList(List<JSONObject> fieldlist, List<JSONObject> list) {
		for (JSONObject jsonObject : fieldlist) {
			String dict = jsonObject.getString("dict");
			String inputtype=jsonObject.getString("inputtype");
			
			if (!StringUtils.isEmpty(inputtype)) {
				if(inputtype.equals("editor")) {
					String value=jsonObject.getString(jsonObject.getString("field"));
					if (!StringUtils.isEmpty(value)) {
						value=value.replaceAll("</?[^>]+>", "");
						jsonObject.put(jsonObject.getString("field") + "_simple", value);
					}
				}
			}
			if (!StringUtils.isEmpty(dict)) {
				Map<String, String> dictMap = this.getDictMap(dict);
				for (JSONObject jsonObject2 : list) {
					if (dict.equals("JSON")) {
						String value = jsonObject2.getString(jsonObject.getString("field"));
						try {
							if (StringUtils.isNotEmpty(value)) {
								jsonObject2.put(jsonObject.getString("field") + "_JSON", JSONObject.parse(value));
							}
						} catch (Exception e) {
							System.out.println("解析json异常，字段："+jsonObject.getString("field")+"\t"+value);
						}
						
					} else {
						// System.out.println(jsonObject.getString("field")+"_show:"+jsonObject.getString("field")+jsonObject2.getString(jsonObject.getString("field"))+dictMap.get(jsonObject2.getString(jsonObject.getString("field"))));
						jsonObject2.put(jsonObject.getString("field") + "_show",
								dictMap.get(jsonObject2.getString(jsonObject.getString("field"))));
					}
				}
			}
			if (jsonObject.containsKey("viewid")) {// view添加唯一id
				int viewid = jsonObject.getIntValue("viewid");
				if (viewid == 1) {
					for (JSONObject jsonObject2 : list) {
						jsonObject2.put("id", jsonObject2.get(jsonObject.getString("field")));
					}
				}

			}
		}

		return list;
	}
	private String initRelation(String relation,String viewName){
		HttpServletRequest request = HttpKit.getRequest();
		List<JSONObject> selectplusFieldList= selectplusFieldList(viewName);
		if(null!=selectplusFieldList&&selectplusFieldList.size()>0){
			for (JSONObject jsonObject : selectplusFieldList) {
				String field = jsonObject.getString("field");
				String value = request.getParameter(field);
				if(StringUtils.isNotEmpty(value)){
					if(value.equals("1")){
						String type=jsonObject.getString("type");
						
						if(type.equals("replace")){
							String oldsql=jsonObject.getString("oldsql");
							String newsql=jsonObject.getString("newsql");
							//System.out.println(relation.indexOf(oldsql));
							relation=relation.replace(oldsql, newsql);
						}
						if(type.equals("and")){
							String newsql=jsonObject.getString("newsql");
							relation=relation+newsql;
						}
					}
				}
			}
		}
		if(null!=ShiroKit.getUser()){
			Integer userid = ShiroKit.getUser().getId();// 数据权限控制
			Integer currentDeptId = ShiroKit.getUser().getDeptId();
			relation = relation.replace("#{currentLoginUid}", userid.toString());
			relation = relation.replace("#{currentdeptid}", currentDeptId.toString());
			 
		}
		
		return relation;
	} 

	@Override
	public JSONObject selectViewById(int id, String viewName, List<String> fieldList) {

		// List<JSONObject> list = new ArrayList<>();
		JSONObject JSONObject = null;
		Map<String, String> viewField = getViewField(viewName);
		String fieldStr = viewField.get("field");
		String tablename = viewField.get("tablename");
		JSONObject viewRelatio = getViewRelation(viewName);
		String relation = viewRelatio.getString("relation");
		String mastertable = viewRelatio.getString("mastertable");
		relation=initRelation(relation,viewName);
		String sql = "select " + fieldStr + " from  " + tablename + " where 1=1  " + relation + " and " + mastertable
				+ ".id=" + id;
		// String sql = relation+" and viewname = '"+viewName +"'"+ where;
		List<Map<String, Object>> mapList = superDao.selectList(sql);
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, getSelectMapObj(key, map));
					}
				}
				// list.add(JSONObject);
			}
		}
		return JSONObject;
	}

	private String fieldList2Str(List<String> fieldList) {
		String fieldStr = "*";
		if (fieldList != null && fieldList.size() > 0) {
			
			StringBuffer sb = new StringBuffer(fieldStr);
			for (String string : fieldList) {
				if(!string.equals("")){
					sb.append(",").append(string);
				}
			}
			fieldStr = sb.substring(2);
		}
		return fieldStr;
	}

	@Override
	public List<JSONObject> selectList(String tableName, String where, List<String> fieldList) {
		if (StringUtils.isEmpty(where)) {
			where = "";
		}
		List<JSONObject> list = new ArrayList<>();
		String fieldStr = fieldList2Str(fieldList);
		String sql = "select " + fieldStr + " from  " + tableName + " where 1=1 " + where;
		List<Map<String, Object>> mapList = superDao.selectList(sql);
		JSONObject JSONObject = null;
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, getSelectMapObj(key, map));
					}
				}
				list.add(JSONObject);
			}
		}
		return list;
	}

	/*
	 * @Override public List<JSONObject> selectViewFieldList(String viewName,
	 * String where) { if(null==where){ where=""; } String
	 * sql=" and viewname = '"+viewName +"'"+ where; List<JSONObject> list =
	 * this.selectSql(sql); return list; }
	 */

	@Override
	public List<JSONObject> selectSql(String sql) {
		List<JSONObject> list = new ArrayList<>();

		List<Map<String, Object>> mapList = superDao.selectList(sql);
		JSONObject JSONObject = null;
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, getSelectMapObj(key, map));
					}
				}
				list.add(JSONObject);
			}
		}
		return list;
	}

	@Override
	public List<JSONObject> selectListPage(String tableName, String where, Page page, List<String> fieldList) {
		if (StringUtils.isEmpty(where)) {
			where = "";
		}
		List<JSONObject> list = new ArrayList<>();
		String fieldStr = fieldList2Str(fieldList);
		String sql = "select " + fieldStr + " from  " + tableName + " where 1=1  " + where;
		List<Map<String, Object>> mapList = superDao.selectListPage(page, sql);
		JSONObject JSONObject = null;
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, getSelectMapObj(key, map));
					}
				}
				list.add(JSONObject);
			}
		}
		return list;
	}
	public Object getSelectMapObj(String key,Map<String, Object> map){
		if(map.get(key) instanceof Date){
			if(map.get(key).toString().endsWith(".0")) {
				return map.get(key).toString().substring(0,map.get(key).toString().length()-2); 
			}
			return map.get(key).toString();
		}else{
			return map.get(key);
		}
	}

	@Override
	public List<JSONObject> selectViewListPage(String viewName, String where, Page page, List<String> fieldList) {
		if (StringUtils.isEmpty(where)) {
			where = "";
		}
		List<JSONObject> list = new ArrayList<>();
		String fieldStr = getViewField(viewName).get("field");
		String tablename = getViewField(viewName).get("tablename");
		String relation = getViewRelation(viewName).getString("relation");
		String orderfield = getViewRelation(viewName).getString("orderfield");
		relation=initRelation(relation,viewName);
		String sql = "select " + fieldStr + " from  " + tablename + " where 1=1  " + relation + where;
		if(!StringUtils.isEmpty(orderfield)){
			if(orderfield.indexOf("#{currentLoginUid}")!=-1) {
				orderfield=initRelation(orderfield,viewName);
			}
			sql+=" order by "+orderfield;
		}
		// String sql = relation+" and viewname = '"+viewName +"'"+ where;
		List<Map<String, Object>> mapList = superDao.selectViewListPage(page, sql);
		JSONObject JSONObject = null;
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, getSelectMapObj(key, map));
					}
				}
				list.add(JSONObject);
			}
		}
		return list;
	}
	@Override
	public List<JSONObject> selectViewList(String viewName, String where,  List<String> fieldList) {
		if (StringUtils.isEmpty(where)) {
			where = "";
		}
		List<JSONObject> list = new ArrayList<>();
		String fieldStr = getViewField(viewName).get("field");
		String tablename = getViewField(viewName).get("tablename");
		String relation = getViewRelation(viewName).getString("relation");
		String orderfield = getViewRelation(viewName).getString("orderfield");
		relation=initRelation(relation,viewName);
		orderfield=initRelation(orderfield,viewName);
		String sql = "select " + fieldStr + " from  " + tablename + " where 1=1  " + relation + where;
		if(!StringUtils.isEmpty(orderfield)){
			if(orderfield.indexOf("#{currentLoginUid}")!=-1) {
				orderfield=initRelation(orderfield,viewName);
			}
			
			sql+=" order by "+orderfield;
		}
		// String sql = relation+" and viewname = '"+viewName +"'"+ where;
		List<Map<String, Object>> mapList = superDao.selectList( sql);
		JSONObject JSONObject = null;
		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				JSONObject = new JSONObject();
				if (!map.isEmpty()) {
					for (String key : map.keySet()) {
						JSONObject.put(key, getSelectMapObj(key, map));
					}
				}
				list.add(JSONObject);
			}
		}
		return list;
	}

	@Override
	public Map<String, String> getViewField(String viewName) {
		if (null == viewFieldlistCache) {
			viewFieldlistCache = new HashMap<>();
		}
		// List<JSONObject> fieldStr = new ArrayList<>();\
		Map<String, String> fieldMap = new HashMap<>();
		Map<String, String> tablenameMap = new HashMap<>();
		String fieldStr = null;
		String tableStr = null;
		if (!viewFieldlistCache.containsKey(viewName) || !cacheField) {

			// String sql = "select " + fieldStr + " from " + tableName + "
			// where 1=1 " + where;
			String sql = "select CONCAT(tablename,\".\",field) field,tablename  from  metadata ,metadataview   where metadata.id= metadataview.metadataid  "
					+ " and viewname = '" + viewName + "'";
			List<Map<String, Object>> mapList = superDao.selectList(sql);
			if (!mapList.isEmpty()) {
				for (Map<String, Object> map : mapList) {
					if (!map.isEmpty()) {
						String field = map.get("field").toString();
						String tablename = map.get("tablename").toString();
						if (null == fieldStr) {
							fieldStr = field + " " + field.replace(".", "_");
						} else {
							fieldStr = fieldStr + "," + field + " " + field.replace(".", "_");
						}
						if (!tablenameMap.containsKey(tablename)) {
							if (null == tableStr) {
								tableStr = tablename;
							} else {
								tableStr = tableStr + "," + tablename;
							}
							tablenameMap.put(tablename, tablename);
						}
					}
				}
				fieldMap.put("field", fieldStr);
				fieldMap.put("tablename", tableStr);
				viewFieldlistCache.put(viewName, fieldMap);
			}
		} else {
			fieldMap = viewFieldlistCache.get(viewName);
		}

		return fieldMap;
	}

	/*
	 * @Override public String getViewRelation(String viewName) { if(null==
	 * viewRelationCache){ viewRelationCache=new HashMap<>(); } String
	 * relation=null; if(!viewRelationCache.containsKey(viewName)||
	 * !cacheField){
	 * 
	 * //String sql = "select " + fieldStr + " from  " + tableName +
	 * " where 1=1  " + where; String sql =
	 * "select relation from  metadataviewrelation  where 1 = 1  "
	 * +" and viewname = '"+viewName +"'"; List<Map<String, Object>> mapList =
	 * superDao.selectList(sql); if (!mapList.isEmpty()) { for (Map<String,
	 * Object> map : mapList) { if (!map.isEmpty()) { if(null==relation) {
	 * relation=map.get("relation").toString(); }else{
	 * relation=relation+","+map.get("relation").toString(); } } }
	 * viewRelationCache.put(viewName, relation); } }else {
	 * relation=viewRelationCache.get(viewName); } return relation; }
	 * 
	 * 
	 * @Override public String getViewAddafter(String viewName) { if(null==
	 * viewAddafterCache){ viewAddafterCache=new HashMap<>(); } String
	 * addafter=null; if(!viewAddafterCache.containsKey(viewName)||
	 * !cacheField){
	 * 
	 * //String sql = "select " + fieldStr + " from  " + tableName +
	 * " where 1=1  " + where; String sql =
	 * "select addafter from  metadataviewrelation  where 1 = 1  "
	 * +" and viewname = '"+viewName +"'"; List<Map<String, Object>> mapList =
	 * superDao.selectList(sql); if (!mapList.isEmpty()) { for (Map<String,
	 * Object> map : mapList) { if (!map.isEmpty()) { if(null==addafter) {
	 * addafter=map.get("addafter").toString(); }else{
	 * addafter=addafter+","+map.get("addafter").toString(); } } }
	 * viewAddafterCache.put(viewName, addafter); } }else {
	 * addafter=viewAddafterCache.get(viewName); } return addafter; }
	 */
	private boolean deleteById(int id, String tableName, boolean virtual) {
		if (virtual) {
			JSONObject jsonObject = JSON.parseObject("{status:-1,id:" + id + "}");
			return this.updateById(jsonObject, tableName, null);
		} else {
			String sql = "delete  from  " + tableName + " where id=" + id;
			// System.out.println(sql);
			superDao.delete(sql);
			return true;
		}

	}
	@Override
	public boolean delete(int id, String tablename, boolean virtual) {
		List<Map<String, Object>> isdelcascadelist;
		if (tablename.endsWith("view")) {
			return deleteViewById(id, tablename,virtual);
		} else {
			deleteById(id,  tablename,virtual);
			isdelcascadelist = metadataMapper.getTablenameIsdelcascade( tablename);
			if (ToolUtil.isNotEmpty(isdelcascadelist)
					&& "1".equals(isdelcascadelist.get(0).get("isdelcascade").toString())) {
				System.out.println(isdelcascadelist.get(0).get("isdelcascade").toString());
				deleteTableByCascade( tablename, id,virtual);
			}
			return true;
		}
	}
	

	/*
	 * 删除表关系使用以下格式删除 delete A,B FROM A,B,C WHERE A.ID=B.AID AND A.ID=C.AID AND
	 * C.ID =''
	 */
	private boolean deleteViewById(int id, String viewName, boolean virtual) {
		JSONObject viewRelation = getViewRelation(viewName);
		List<JSONObject> fieldlist = null;
		String tablenameAll = "";
		String sqlQuery = "select tablename from metadata "
				+ "where exists (select 1 from metadataview where metadata.id= metadataview.metadataid and metadataview.viewname= '"
				+ viewName + "' )" + "group by tablename";
		fieldlist = this.selectSql(sqlQuery);
		for (JSONObject jsonObject : fieldlist) {
			if (ToolUtil.isEmpty(tablenameAll)) {
				tablenameAll = jsonObject.getString("tablename");
			} else {
				tablenameAll = tablenameAll + "," + jsonObject.getString("tablename");
			}
		}
		String mastertable = viewRelation.getString("mastertable");
		String deletetables = viewRelation.getString("deletetables");
		String relation = viewRelation.getString("relation");
		relation=initRelation(relation,viewName);
		if (virtual) {
			String sql = "update " + tablenameAll + "  set status=-1 where 1=1 " + relation + " and " + mastertable
					+ ".id = " + id;
			superDao.update(sql);
		} else {
			String sql = "delete   from " + tablenameAll + " where 1=1 " + relation + " and " + mastertable + ".id = "
					+ id;
			// System.out.println(sql);

			superDao.delete(sql);
		}
		return true;
	}

	@Override
	public boolean delete(String tableName, String where, boolean virtual) {
		if (virtual) {
			String sql = "update " + tableName + " set status=-1  where " + where;
			superDao.update(sql);
		} else {
			String sql = "delete  from  " + tableName + " where " + where;
			System.out.println(sql);
			superDao.delete(sql);
		}

		return true;
	}

	@Override
	public boolean updateById(JSONObject jsonObject, String tableName, List<String> fieldList) {
		int id = 0;
		if (jsonObject != null && jsonObject.containsKey("id")) {
			id = jsonObject.getIntValue("id");
		} else {
			return false;
		}
		String where = "  id=" + id;
		return update(jsonObject, tableName, fieldList, where);
	}

	/*
	 * @Override public boolean updateViewById(JSONObject jsonObject, String
	 * viewName, List<String> fieldList) { int id = 0; if (jsonObject != null &&
	 * jsonObject.containsKey("id")) { id = jsonObject.getIntValue("id"); } else
	 * { return false; } Map<String,String> viewRelationTable
	 * =getViewRelationTable(viewName); String tablename =
	 * getViewField(viewName).get("tablename"); String relation =
	 * viewRelationTable.get("relation"); String mastertable =
	 * viewRelationTable.get("mastertable"); String where =" 1=1  "+relation+
	 * " and "+ mastertable +".id=" + id; return update(jsonObject, tablename,
	 * fieldList, where); }
	 */

	@Override
	public boolean update(JSONObject jsonObject, String tableName, List<String> fieldList, String where) {
		String setString = jsonObject2setString(jsonObject, fieldList);
		String sql = "update " + tableName + " set " + setString + " where " + where;
		sql = changecode(sql);
		superDao.update(sql);
		return true;
	}

	private String jsonObject2setString(JSONObject jsonObject, List<String> fieldList) {
		String setString = "";
		if (fieldList != null && fieldList.size() > 0) {
			StringBuffer sb = new StringBuffer(setString);
			for (String string : fieldList) {
				if (null != jsonObject.get(string)) {
					sb.append(",").append(string).append("='").append(jsonObject.get(string)).append("'");
				}
			}
			setString = sb.substring(1);
		} else {
			StringBuffer sb = new StringBuffer(setString);
			for (String string : jsonObject.keySet()) {
				if (null != jsonObject.get(string)) {
					sb.append(",").append(string).append("='").append(jsonObject.get(string)).append("'");
				}
			}
			setString = sb.substring(1);
		}
		return setString;
	}

	@Override
	public JSONObject add(JSONObject jsonObject, String tableName, List<String> fieldList) {
		String fieldStr = "";
		String valueStr = "";
		if (null != fieldList && fieldList.size() > 0) {
			fieldStr = fieldList2Str(fieldList);
			StringBuffer sb = new StringBuffer();
			for (String string : fieldList) {
				if (string.equals("id") ) {
					fieldStr = fieldStr.replaceFirst("id,", "");
					continue;
				}else if (null == jsonObject.get(string)) {
					//fieldStr = fieldStr.replaceAll(string+",", "");
					sb.append(",").append("null");
					continue;
				}
				sb.append(",'").append(jsonObject.get(string)).append("'");
			}
			valueStr = sb.substring(1);
		} else {
			StringBuffer sbField = new StringBuffer();
			StringBuffer sbValue = new StringBuffer();
			for (String string : jsonObject.keySet()) {
				if (string.equals("id") || null == jsonObject.get(string)) {
					continue;
				}
				sbField.append(",").append(string);
				sbValue.append(",'").append(jsonObject.get(string)).append("'");
			}
			fieldStr = sbField.substring(1);
			valueStr = sbValue.substring(1);
		}

		String insertSql = "insert into " + tableName + " (" + fieldStr + ") values(" + valueStr + ")";
		insertSql = changecode(insertSql);
		int a = superDao.insertAndGetId(insertSql);
		List<Map<String, Object>> list = superDao.selectList("select  LAST_INSERT_ID() as id");
		jsonObject.put("id", list.get(0).get("id"));
		return jsonObject;
	}

	public JSONObject add(JSONObject jsonObject, String tableName, List<String> fieldList,List<String> excludeFieldList) {
		String fieldStr = "";
		String valueStr = "";
		StringBuffer sb = new StringBuffer();
		
		if (null == fieldList || fieldList.size() ==0) {
			List<JSONObject> fieldObjList =getFieldList(tableName);
			if(null==fieldObjList||fieldObjList.size()==0){
				
				fieldList=new ArrayList<>();
				for (JSONObject fieldObj : fieldObjList) {
					int ifadd=fieldObj.getIntValue("add");
					if(ifadd>0){
						String field=fieldObj.getString("field");
						fieldList.add(field);
					}
				}
			}else{
				fieldList=new ArrayList<>(jsonObject.keySet());
			}
		} 
		if(null != excludeFieldList&& excludeFieldList.size() >0){
			Map<String, String> excludefieldMap=new HashMap<>();
			for (String string : excludeFieldList) {
				excludefieldMap.put(string, string);
			}
			for (int i = 0; i < fieldList.size(); i++) {
				if(excludefieldMap.containsKey(fieldList.get(i))){
					fieldList.set(i, "");
				} 
			}
		}
		
	
		fieldStr = fieldList2Str(fieldList);
		for (String string : fieldList) {
			if(StringUtils.isBlank(string)){
				continue;
			}
			if(!jsonObject.keySet().contains(string)||null==jsonObject.get(string)) {
				sb.append(",").append("null");
			}else{
				sb.append(",'").append(jsonObject.get(string)).append("'");
			}
			
		}
		valueStr = sb.substring(1);
		String insertSql = "insert into " + tableName + " (" + fieldStr + ") values(" + valueStr + ")";
		insertSql = changecode(insertSql);
		int a = superDao.insertAndGetId(insertSql);
		List<Map<String, Object>> list = superDao.selectList("select  LAST_INSERT_ID() as id");
		jsonObject.put("id", list.get(0).get("id"));
		return jsonObject;
	}
	@Override
	public boolean addList(List<JSONObject> list, String tableName, List<String> fieldList,List<String> excludeFieldList){
		if (null == list) {
			return false;
		}
		String fieldStr = "";
		String valueStr = "";
		if (null == fieldList || fieldList.size() ==0) {
			List<JSONObject> fieldObjList =getFieldList(tableName);
			if(null!=fieldObjList&&fieldObjList.size()>0){
				
				fieldList=new ArrayList<>();
				for (JSONObject fieldObj : fieldObjList) {
					int ifadd=fieldObj.getIntValue("add");
					if(ifadd>0){
						String field=fieldObj.getString("field");
						fieldList.add(field);
					}
				}
			}else{
				fieldList=new ArrayList<>(list.get(0).keySet());
			}
		} 
		if(null != excludeFieldList&& excludeFieldList.size() >0){
			Map<String, String> excludefieldMap=new HashMap<>();
			for (String string : excludeFieldList) {
				excludefieldMap.put(string, string);
			}
			for (int i = 0; i < fieldList.size(); i++) {
				if(excludefieldMap.containsKey(fieldList.get(i))){
					fieldList.set(i, "");
				} 
			}
		}
		
		fieldStr = fieldList2Str(fieldList);
		if (null != fieldList && fieldList.size() > 0) {
			fieldStr = fieldList2Str(fieldList);
			StringBuffer sbValue = new StringBuffer();
			int size=0;
			for (JSONObject jsonObject : list) {
				int i = 0;
				sbValue.append(",(");
				for (String string : fieldList) {
					if(StringUtils.isBlank(string)){
						continue;
					}
					if(!jsonObject.keySet().contains(string)||null==jsonObject.get(string)) {
						if (i == 0) {
							sbValue.append("null");
						} else {
							sbValue.append(",null");
						}
					}else{
						if (i == 0) {
							sbValue.append("'").append(jsonObject.get(string)).append("'");
						} else {
							sbValue.append(",'").append(jsonObject.get(string)).append("'");
						}
					}
					i++;
				}
				sbValue.append(")");
				size++;
				if(size==1000){//1000条执行插入
					valueStr = sbValue.substring(1);
					String insertSql = "insert into " + tableName + " (" + fieldStr + ") values" + valueStr;
					insertSql = changecode(insertSql);
					superDao.insert(insertSql);
					sbValue=new StringBuffer();
					size=0;
				}
			}
			valueStr = sbValue.substring(1);
		} 

		String insertSql = "insert into " + tableName + " (" + fieldStr + ") values" + valueStr;
		insertSql = changecode(insertSql);
		superDao.insert(insertSql);
		return true;
	}
	@Override
	public boolean addList(List<JSONObject> list, String tableName) {
		return addList(list, tableName, null, null);
	}
	@Override
	public boolean addList(List<JSONObject> list, String tableName, List<String> fieldList) {
		return addList(list, tableName, fieldList, null);
		/*if (null == list) {
			return false;
		}
		String fieldStr = "";
		String valueStr = "";
		if (null != fieldList && fieldList.size() > 0) {
			fieldStr = fieldList2Str(fieldList);
			StringBuffer sbValue = new StringBuffer();
			for (JSONObject jsonObject : list) {
				int i = 0;
				sbValue.append(",(");
				for (String string : fieldList) {
					if(!jsonObject.keySet().contains(string)||null==jsonObject.get(string)) {
						if (i == 0) {
							sbValue.append("null");
						} else {
							sbValue.append(",null");
						}
					}else{
						if (i == 0) {
							sbValue.append("'").append(jsonObject.get(string)).append("'");
						} else {
							sbValue.append(",'").append(jsonObject.get(string)).append("'");
						}
					}
					i++;
				}
				sbValue.append(")");
			}
			valueStr = sbValue.substring(1);
		} else {
			StringBuffer sbField = new StringBuffer();
			StringBuffer sbValue = new StringBuffer();

			int j = 0;
			for (JSONObject jsonObject : list) {
				int i = 0;
				sbValue.append(",(");
				for (String string : jsonObject.keySet()) {
					if (j == 0) {
						sbField.append(",").append(string);
					}
					if(null==jsonObject.get(string)) {
						if (i == 0) {
							sbValue.append("null");
						} else {
							sbValue.append(",null");
						}
					}else{
						if (i == 0) {
							sbValue.append("'").append(jsonObject.get(string)).append("'");
						} else {
							sbValue.append(",'").append(jsonObject.get(string)).append("'");
						}
					}
					i++;
				}
				j++;
				sbValue.append(")");
			}

			fieldStr = sbField.substring(1);
			valueStr = sbValue.substring(1);
		}

		String insertSql = "insert into " + tableName + " (" + fieldStr + ") values" + valueStr;
		insertSql = changecode(insertSql);
		superDao.insert(insertSql);
		return true;*/

	}

	// 获取view的关联关系对象
	public JSONObject getViewRelation(String viewname) {
		if (null == viewRelationCache) {
			viewRelationCache = new HashMap<>();
		}
		if (!viewRelationCache.containsKey(viewname) || !cacheField) {
			JSONObject viewRelation = null;
			List<JSONObject> list = this.selectList("metadataviewrelation", " and  viewname='" + viewname + "'", null);
			if (null != list && list.size() > 0) {
				viewRelation = list.get(0);
			}
			viewRelationCache.put(viewname, viewRelation);
		}
		return viewRelationCache.get(viewname);
	}
	


	// 元数据管理中所有到id和pid之间到对应关系到map，以("0","1,2,3") 存储
	// private Map<String, String> tableRrelationMap;
	// 某个表的所有的节点集合
	// private LinkedHashSet<String> tableNodeSet;
	private void loadTableRelationMap() {

		if (null == tableRrelationMap) {
			tableRrelationMap = new HashMap<>();
		}
		if (ToolUtil.isNotEmpty(tableRrelationMap)) {
			return;
		}

		List<Map<String, Object>> tableRelationlist = metadataMapper.getTableRelation();
		for (Map<String, Object> obj : tableRelationlist) {
			if (ToolUtil.isEmpty(tableRrelationMap)) {
				tableRrelationMap.put(obj.get("pid").toString(), obj.get("id").toString());
			} else if (tableRrelationMap.containsKey(obj.get("pid"))) {
				String ids = tableRrelationMap.get(obj.get("pid")) + "," + obj.get("id");
				tableRrelationMap.put(obj.get("pid").toString(), ids);
			} else {
				tableRrelationMap.put(obj.get("pid").toString(), obj.get("id").toString());
			}
		}
	}

	private void loadTablenameAndIdMap() {
		if (null == tablenameAndIdMap) {
			tablenameAndIdMap = new HashMap<>();
		}
		if (ToolUtil.isNotEmpty(tablenameAndIdMap)) {
			return;
		}
		List<Map<String, Object>> tablenameAndIdMaplist = metadataMapper.getTablenameAndIdMap();
		for (Map<String, Object> obj : tablenameAndIdMaplist) {
			tablenameAndIdMap.put(obj.get("tablename").toString(), obj.get("id").toString());
		}
	}

	private void findNode(String node, LinkedHashSet tableNodeSet, Map<String, String> tableValueMap) {

		String temp = tableRrelationMap.get(node);
		String ids = null;
		Metadata metadata = null;
		if (temp != null) {
			String[] nodes = temp.split(",");
			for (String nodeone : nodes) {
				tableNodeSet.add(nodeone);
				metadata = metadataService.selectById(node);
				ids = tableValueMap.get(metadata.getTablename());
				gettableids(nodeone, ids, tableValueMap);
				// System.out.println("递归获取"+nodeone);
				findNode(nodeone, tableNodeSet, tableValueMap);
			}
		}

	}

	/*
	 * 根据节点和父节点id串，获取子节点id传
	 */
	private void gettableids(String nodes, String ids, Map<String, String> tableValueMap) {
		List<JSONObject> resultlist = null;
		Metadata metadata = null;
		metadata = metadataService.selectById(nodes);

		String sqlQuery = "select id from " + metadata.getTablename() + " where " + metadata.getField() + " in (" + ids
				+ ")";
		String nodesIds = "";
		if (ToolUtil.isEmpty(ids)) {
			tableValueMap.put(metadata.getTablename(), nodesIds);
		} else {
			resultlist = this.selectSql(sqlQuery);

			for (int i = 0; i < resultlist.size(); i++) {
				if ("".equals(nodesIds)) {
					nodesIds = resultlist.get(i).get("id").toString();
				} else {
					nodesIds = nodesIds + "," + resultlist.get(i).get("id").toString();
				}
			}
			tableValueMap.put(metadata.getTablename(), nodesIds);
		}

	}

	/**
	 *
	 * 
	 * @param tableName
	 *            要级联删除的表名
	 * @param id
	 *            要级联删除的id
	 */
	private boolean deleteTableByCascade(String tableName, int id, boolean virtual) {
		// 1、加载表关系
		loadTableRelationMap();
		// 2、转换表名和id之间到关系
		loadTablenameAndIdMap();
		String node = tablenameAndIdMap.get(tableName);
		// 3、递归输出子节点集合
		LinkedHashSet<String> tableNodeSet = new LinkedHashSet();
		Map<String, String> tableValueMap = new HashMap();
		tableValueMap.put(tableName, id+"");
		findNode(node, tableNodeSet, tableValueMap);
		Metadata metadata = null;
		String condtion = null;
		// 循环删除
		for (String deleteid : tableNodeSet) {
			metadata = metadataService.selectById(deleteid);
			condtion = " id in (" + tableValueMap.get(metadata.getTablename()).toString() + ")";
			if (ToolUtil.isNotEmpty(tableValueMap.get(metadata.getTablename()).toString())) {

				this.delete(metadata.getTablename(), condtion, virtual);

			}

		}
		return true;
	}

	/**
	 * 去掉前台传递过来的特殊字符。
	 * 
	 * @param 字符串
	 *            要去掉特殊字符的字符串
	 * @param
	 * 
	 */
	public String changecode(String str) {
		str = str.replace("& lt;", "<");
		str = str.replace("& gt;", ">");
		str = str.replace("& #40;", "(");
		str = str.replace("& #41;", ")");
		return str;
	}

	@Override
	public List<JSONObject> getFieldList(String tablename) {

		if (null == fieldlistCache) {
			fieldlistCache = new HashMap<>();
		}
		if (!fieldlistCache.containsKey(tablename) || !cacheField) {
			List<JSONObject> fieldlist = null;
			if (tablename.endsWith(DBModelConst.MODEL_VIEW_SUFFIX)) {
				String sql = "  select v.id,v.viewname tablename,v.viewnamecn tablenamecn,m.tablename tablenamectual,CONCAT(m.tablename,'_',m.field) field,CONCAT(m.tablename,'.',m.field) vfield,m.fieldtype,m.fieldlength,m.decimalpoint,m.isnullable,m.title,m.fathernode,m.ismajorkey,m.isvalid,m.updatetime,m.operator,m.operatnum,m.operatnumbefore,v.display,v.update,v.add,m.verifyrole,v.isselect,m.dict,m.inputtype,v.onclick,v.ondblclick,v.onfocus,v.onblur,v.fieldsort,v.issort,v.imp,v.exp,v.viewid,v.readonly,IFNULL(v.templetscript,m.templetscript) templetscript,IFNULL(v.templet,m.templet) templet,IFNULL(IFNULL(v.clickevent,r.rowclickevent),m.clickevent) clickevent,IFNULL(v.minwidth,m.minwidth) minwidth,m.selecttype,IF(v.defaultvalue=\"\",m.defaultvalue,v.defaultvalue) defaultvalue from metadata m,metadataview v,metadataviewrelation r where v.viewname=r.viewname and m.id= v.metadataid and   v.viewname = '"
						 + tablename + "'   order by v.fieldsort ";
				fieldlist = selectSql(sql);
			}else if(tablename.endsWith(DBModelConst.MODEL_VIEW_TREE_SUFFIX)){
				String sql = "  select v.id,v.viewname tablename,v.viewnamecn tablenamecn,m.tablename tablenamectual,CONCAT(m.tablename,'_',m.field) aliasfield,"
						+ "m.field field,CONCAT(m.tablename,'.',m.field) vfield,m.fieldtype,m.fieldlength,m.decimalpoint,m.isnullable,m.title,m.fathernode,m.ismajorkey,m.isvalid,m.updatetime,m.operator,m.operatnum,m.operatnumbefore,v.display,v.update,v.add,m.verifyrole,v.isselect,m.dict,m.inputtype,v.onclick,v.ondblclick,v.onfocus,v.onblur,v.fieldsort,v.issort,v.imp,v.exp,v.viewid,v.readonly,IFNULL(v.templetscript,m.templetscript) templetscript,IFNULL(v.templet,m.templet) templet,IFNULL(v.minwidth,m.minwidth) minwidth,m.selecttype,IF(v.defaultvalue=\"\",m.defaultvalue,v.defaultvalue) defaultvalue from metadata m,metadataview v where  m.id= v.metadataid and   v.viewname = '"
						 + tablename + "'   order by v.fieldsort ";
				fieldlist = selectSql(sql);
			}else {
				fieldlist = selectList("metadata",
						" and tablename='" + tablename + "' order by fieldsort ", null);
			}
			for (JSONObject jsonObject : fieldlist) {
				String dict = jsonObject.getString("dict");
				if (!StringUtils.isEmpty(dict)) {
					jsonObject.put("dictData", getDictList(dict));
				}
				String inputtype=jsonObject.getString("inputtype");
				int readonly = jsonObject.getIntValue("readonly");
				if (readonly == 1) {
					if("radio".equals(inputtype)||"select".equals(inputtype)||"checkbox".equals(inputtype)||"checkbox1".equals(inputtype)){
						jsonObject.put("readonly", "disabled");
					}else{
						jsonObject.put("readonly", "readonly");
					}
					
				} else {
					jsonObject.put("readonly", null);
				}
			}
			fieldlistCache.put(tablename, fieldlist);
		}
		return fieldlistCache.get(tablename);
	}

	@Override
	public Map<String, String> getDictMap(String dictName) {
		if (null == dictMap||null ==dictMap.get(dictName)) {
			getDictList(dictName);
		}
		Map<String, String> map = dictMap.get(dictName);
		return map;
	}

	@Override
	public List<JSONObject> getDictList(String dictName) {
		if (null == dictCache) {
			dictCache = new HashMap<>();
			dictMap = new HashMap<>();
		}
		if (!dictCache.containsKey(dictName) || dictName.toLowerCase().startsWith("sql:")|| dictName.toLowerCase().startsWith("tree:")||dictName.toLowerCase().startsWith("selectswithgroup:")) {
			List<JSONObject> dictList = new ArrayList<>();
			JSONObject pleaseSelect = new JSONObject();
			Map<String, String> dict = new HashMap<>();
			pleaseSelect.put("num", "");
			pleaseSelect.put("name", "请选择");
			dictList.add(pleaseSelect);
			if (dictName.toLowerCase().startsWith("sql:")) {// sql字段需要转换为num,name
				List<JSONObject> dictList1 = selectSql(dictName.replace("sql:", ""));
				for (JSONObject jsonObject : dictList1) {
					dictList.add(jsonObject);
					dict.put(jsonObject.getString("num"), jsonObject.getString("name"));
				}
			}else if (dictName.toLowerCase().startsWith("tree:")) {// sql字段需要转换为num,name
				dictList=getDictTreeList("dict", -1, dictName.replace("tree:", ""), "id", "name", "pid");
			}else if (dictName.toLowerCase().startsWith("selectswithgroup:")) {
				String[] strs=dictName.split(":");
				if(strs.length==3){
					dictList=getDictSelectsOptionWithGroup(strs[1], strs[2]);
				}
			} else {
				List<Map<String, Object>> fieldlist = dictMapper.getDictByname(dictName);
				for (Map<String, Object> map : fieldlist) {
					JSONObject object = new JSONObject(map);
					dictList.add(object);
					dict.put(object.getString("num"), object.getString("name"));
				}
			}
			dictMap.put(dictName, dict);
			dictCache.put(dictName, dictList);
		}
		return dictCache.get(dictName);
	}
	private List<JSONObject> getDictSelectsOptionWithGroup(String optionSqlId, String groupSqlId) {
		// TODO Auto-generated method stub
		List<JSONObject> list=new ArrayList<>();
		List<JSONObject> optionList=(List<JSONObject>) doEventhing(Integer.parseInt(optionSqlId), null);//name num groupid
		Map<String, List<JSONObject>> optionsMap=new HashMap<>();
		for (JSONObject jsonObject : optionList) {
			String groupid=jsonObject.getString("groupid");
			if(optionsMap.containsKey(groupid)){
				List<JSONObject> oneGroupList=optionsMap.get(groupid);
				oneGroupList.add(jsonObject);
			}else{
				List<JSONObject> oneGroupList=new ArrayList<>();
				oneGroupList.add(jsonObject);
				optionsMap.put(groupid, oneGroupList);
			}
		}
		List<JSONObject> groupList=(List<JSONObject>) doEventhing(Integer.parseInt(groupSqlId), null);//name groupid type="optgroup"
		for (JSONObject jsonObject : groupList) {
			list.add(jsonObject);
			String groupid=jsonObject.getString("groupid");
			List<JSONObject> oneGroupList=optionsMap.get(groupid);
			if(null!=oneGroupList){
				for (JSONObject jsonObject2 : oneGroupList) {
					list.add(jsonObject2);
				}
			}
		}
		return list;
	}

	private List<JSONObject> getDictTreeList(String table,int pid,String name,String idField,String nameField,String pidField){
		List<JSONObject> list=new ArrayList<JSONObject>();
		String sql ="select "+idField+","+nameField+","+pidField+" from "+table;
		if(StringUtils.isNotEmpty(name)){
			sql+=" where " +nameField+"='"+name+"'";
		}else{
			sql+=" where " +pidField+"="+pid+"";
		}
		list=selectSql(sql);
		for (JSONObject jsonObject : list) {
			int id=jsonObject.getIntValue(idField);
			jsonObject.put("children", getDictTreeList(table, id, null, idField, nameField, pidField));
		}
		
		return list;
	}
	@Override
	public String getFieldTempletScript(List<JSONObject> fieldlist) {
		String script="";
		for (JSONObject jsonObject : fieldlist) {
			int display = jsonObject.getIntValue("display");
			if (display == 1) {
				
				String templetscript = jsonObject.getString("templetscript");
				if (!StringUtils.isEmpty(templetscript)) {
					script+=templetscript;
				} 
			}
			jsonObject.remove("templetscript");
		}
		return script;
	}
	@Override
	public List<JSONObject> getShowField(List<JSONObject> fieldlist, String tablename) {

		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject checkbox = new JSONObject();
		checkbox.put("type", "checkbox");
		jsonList.add(checkbox);
		for (JSONObject jsonObject : fieldlist) {
			int display = jsonObject.getIntValue("display");
			if (display == 1) {
				JSONObject field = new JSONObject();
				String dict = jsonObject.getString("dict");
				if (!StringUtils.isEmpty(dict)) {
					field.put("field", jsonObject.getString("field") + "_show");
				} else {
					field.put("field", jsonObject.getString("field"));
				}

				field.put("event", jsonObject.getString("clickevent"));
				
				field.put("templet", jsonObject.getString("templet"));
				field.put("minWidth", jsonObject.getString("minwidth"));
				field.put("title", jsonObject.getString("title"));
				field.put("sort", jsonObject.getIntValue("issort") == 1 ? true : false);
				jsonList.add(field);
			}
		}
		/*
		 * if(tablename.startsWith("view")){ JSONObject
		 * relation=superService.getViewRelation(tablename); int
		 * toolbar=relation.getIntValue("toolbar"); if(toolbar==1){ JSONObject
		 * toolbarObj=JSONObject.
		 * parseObject("{fixed: 'right',  align:'center', toolbar: '#barDemo'}"
		 * ); jsonList.add(toolbarObj); } }
		 */
		return jsonList;
	}
 
	@Override
	public List<JSONObject> selectInitList(String tableName, String where, List<String> fieldList) {
		List<JSONObject> fieldlist = getFieldList(tableName);
		List<JSONObject> list = selectList(tableName, where, fieldList);
		return initQureyList(fieldlist, list);
	}

	@Override
	public List<JSONObject> selectInitListPage(String tableName, String where, Page page, List<String> fieldList) {
		List<JSONObject> fieldlist = getFieldList(tableName);
		List<JSONObject> list = selectListPage(tableName, where, page, fieldList);
		return initQureyList(fieldlist, list);
	}

	@Override
	public List<JSONObject> selectInitViewListPage(String tableName, String where, Page page, List<String> fieldList) {
		List<JSONObject> fieldlist = getFieldList(tableName);
		List<JSONObject> list = selectViewListPage(tableName, where, page, fieldList);
		return initQureyList(fieldlist, list);
	}
	@Override
	public List<JSONObject> selectInitViewList(String tableName, String where,  List<String> fieldList) {
		List<JSONObject> fieldlist = getFieldList(tableName);
		List<JSONObject> list = selectViewList(tableName, where,  fieldList);
		return initQureyList(fieldlist, list);
	}

	@Override
	public Object doEventhing(int id, JSONObject internalParams) {
		
		JSONObject object =selectById(id, "sqltable", null);
		int type = object.getIntValue("type");
		String sql = object.getString("sql");
		String params = object.getString("params");
		if (StringUtils.isNotEmpty(params)) {
			JSONObject sqlobj=initSql(sql, params, internalParams);
			if(500==sqlobj.getIntValue("code")){
				return JSON.parse("{msg:'"+sqlobj.getString("message")+"'}");
			}
			sql=sqlobj.getString("sql");
			/*HttpServletRequest request = HttpKit.getRequest();
			String[] paramlist = params.split(";");
			for (String string : paramlist) {
				if (string.equals("currentLoginUid")) {
					if(null==ShiroKit.getUser()){
						
					}
					Integer currentLoginUid = ShiroKit.getUser().getId();
					sql = sql.replace("#{" + string + "}", "'" + currentLoginUid + "'");
				} else {
					String value =null;
					if(null!=internalParams){
						value =internalParams.getString(string);
					}
					if(null==value){
						value=request.getParameter(string);
					}
					sql = sql.replace("#{" + string + "}", "'" + value + "'");
				}
			}*/
		}
		if (type < 3) {
			List<JSONObject> list =selectSql(sql);
			if(list.size()==0){
				return null;
			}
			if (type == 1) {
				return list.get(0);
			}
			return list;
		} else {
			if (type == 3) {
				superDao.update(sql);
				return JSON.parse("{msg:'更新成功'}");
			} else if (type == 4) {
				int returnid=superDao.insertAndGetId(sql);
				return JSON.parse("{id:"+returnid+"}");
			} else if (type == 5) {
				superDao.delete(sql);
				return JSON.parse("{msg:'删除成功'}");
			}
			return null;
		}
	}
	@Override
	public List<JSONObject> statistic(String tableName, List<JSONObject> groupbyfieldlist, List<JSONObject> fieldlist,
			String where, List<JSONObject> orderbyfieldlist) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<JSONObject> statisticView(String tableName, List<JSONObject> groupbyfieldlist,
			List<JSONObject> fieldlist, String where, List<JSONObject> orderbyfieldlist) {
		// TODO Auto-generated method stub
		return null;
	}
	//查询结果整合接口
	@Override
	public JSONObject getdata(String datacode,JSONObject internalParams) {
		JSONObject returnObject=new JSONObject();
		returnObject.put("datacode", datacode);
		returnObject.put("code", 200);
		returnObject.put( "message", "操作成功");
		if(StringUtils.isEmpty(datacode)){
			returnObject.put("code", 500);
			returnObject.put( "message", "datacode参数错误");
			return returnObject;
		}
		List<JSONObject> objList=selectList("dataobject"," and code='"+datacode+"'",null);
		if(null==objList||objList.isEmpty()){
			returnObject.put("code", 500);
			returnObject.put( "message", "datacode没找到");
			return returnObject;
		}
		for (JSONObject jsonObject2 : objList) {
			try {
				String code=jsonObject2.getString("code");
				String subcode=jsonObject2.getString("subcode");
				String where=jsonObject2.getString("where");
				String params=jsonObject2.getString("params");
				int resulttype=jsonObject2.getIntValue("resulttype");
				int ifinit=jsonObject2.getIntValue("ifinit");
				
				if(StringUtils.isNumeric(subcode)){
					Object obj=doEventhing(Integer.parseInt(subcode), null);
					if(3==resulttype&&null!=obj){
						
						JSONObject o=(JSONObject) obj;
						String id=o.getString("fileid");
						List<ImageEntity> imgList=createWordImageEntity(id);
						for (int i = 0; i < imgList.size(); i++) {
							returnObject.put(subcode+"_"+i, imgList.get(i));
						}
						
					}else if(4==resulttype&&null!=obj){
						List<JSONObject> lo=(List<JSONObject>) obj;
						int i=0;
						for (JSONObject jsonObject : lo) {
							String id=jsonObject.getString("fileid");
							List<ImageEntity> imgList=createWordImageEntity(id);
							for (ImageEntity jsonObject3 : imgList) {
								returnObject.put(subcode+"_"+i, jsonObject3);
								i++;
							}
						}
					}else{
						returnObject.put(subcode, obj);
					}
				}else{
					HttpServletRequest request = HttpKit.getRequest();
					if(subcode.endsWith("view")){
						if(1==resulttype){
							String idStr=null;
							if(null!=internalParams){
								idStr =internalParams.getString(params);
							}
							if(null==idStr){
								idStr=request.getParameter(params);
							}
							int id=Integer.parseInt(idStr);
							if(ifinit==1){
								returnObject.put(subcode, selectInitViewById(id, subcode, null));
							}else{
								returnObject.put(subcode, selectViewById(id, subcode, null));
							}
						}else{
							JSONObject sqlobj =initSql(where, params, internalParams);
							if(500==sqlobj.getIntValue("code")){
								returnObject.put("code", 201);
								returnObject.put( "message", returnObject.getString("message")+"\t"+sqlobj.getString("message"));
								return returnObject;
							}
							where=sqlobj.getString("sql");
							if(ifinit==1){
								returnObject.put(subcode, selectInitViewList(subcode, where, null));
							}else{
								returnObject.put(subcode, selectViewList(subcode, subcode, null));
							}
						}
					}else{
						if(1==resulttype){
							String idStr=null;
							if(null!=internalParams){
								idStr =internalParams.getString(params);
							}
							if(null==idStr){
								idStr=request.getParameter(params);
							}
							int id=Integer.parseInt(idStr);
							if(ifinit==1){//thc
								returnObject.put(subcode, selectInitObjectById(id, subcode, null));
							}else{
								returnObject.put(subcode, selectById(id, subcode, null));
							}
						}else{
							JSONObject sqlobj =initSql(where, params, internalParams);
							if(500==sqlobj.getIntValue("code")){
								returnObject.put("code", 201);
								returnObject.put( "message", returnObject.getString("message")+"\t"+sqlobj.getString("message"));
								return returnObject;
							}
							where=sqlobj.getString("sql");
							if(ifinit==1){
								returnObject.put(subcode, selectInitList(subcode, where, null));
							}else{
								returnObject.put(subcode, selectList(subcode, subcode, null));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				returnObject.put("code", 500);
				returnObject.put( "message", "查询错误");
			}
		}
		return returnObject;
	}
	private List<ImageEntity> createWordImageEntity(String id) {
		List<ImageEntity> list=new ArrayList<ImageEntity>();
		if(StringUtils.isEmpty(id)){
			return list;
		}
		if(StringUtils.isNumeric(id)){
			JSONObject savefile=selectById(Integer.parseInt(id), "file", null);
			/*byte[] image =getFile(viciProperties.getFileUploadPath()+savefile.getString("path"));
			if(null!=image){*/
				ImageEntity imageEntity = new ImageEntity(viciProperties.getFileUploadPath()+savefile.getString("path"), 500, 300);
				list.add(imageEntity);
			//}
		}else{
			id=id.replaceAll("\\[", "").replaceAll("\\]", "");
			for (String str : id.split(",")) {
				if(StringUtils.isNotEmpty(str)){
					JSONObject savefile=selectById(Integer.parseInt(str), "file", null);
						ImageEntity imageEntity = new ImageEntity(viciProperties.getFileUploadPath()+savefile.getString("path"), 500, 300);
						list.add(imageEntity);
				}
			}
		}
		return list;
	}
	/*private  byte[]  getFile(String path){
		byte[] buffer = null;  
		File file = new File(path);
		if(!file.exists()){
			return buffer;
		}
       
        try {  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //删除临时文件
        //file.delete();
        return buffer; 
	}*/

	private JSONObject initSql(String sql,String params,JSONObject internalParams){
		JSONObject returnObject=new JSONObject();
		returnObject.put("code", 200);
		if(StringUtils.isEmpty(sql)){
			returnObject.put("sql", sql);
			return returnObject;
		}
		HttpServletRequest request = HttpKit.getRequest();
		
		String[] paramlist = params.split(";");
		for (String string : paramlist) {
			if (string.equals("currentLoginUid")) {
				if(null==ShiroKit.getUser()){
					returnObject.put("code", 500);
					returnObject.put( "message", "登陆超时，请重新登陆");
					return returnObject;
				}
				Integer currentLoginUid = ShiroKit.getUser().getId();
				sql = sql.replace("#{" + string + "}", "'" + currentLoginUid + "'");
			} else {
				String value =null;
				if(null!=internalParams){
					value =internalParams.getString(string);
				}
				if(null==value){
					value=request.getParameter(string);
				}
				
				if(!StringUtils.isNumeric(value)) {
					value="'"+value+"'";
				}
				
				sql = sql.replace("#{" + string + "}", value);
			}
		}
		returnObject.put("sql", sql);
		return returnObject;
	}

	@Override
	public boolean delete(String sql) {
		// TODO Auto-generated method stub
		superDao.delete(sql);
		return true;
	}

	@Override
	public boolean update(String sql) {
		// TODO Auto-generated method stub
		superDao.update(sql);
		return true;
	}

	@Override
	public boolean insert(String sql) {
		// TODO Auto-generated method stub
		superDao.insert(sql);
		return true;
	}

	@Override
	public List<JSONObject> selectFieldAdd(List<JSONObject> fieldlist, String viewName) {
		if(!viewName.endsWith("_view")){
			return fieldlist;
		}else{
			List<JSONObject> selectplusFieldList= selectplusFieldList( viewName);
			
			if(null!=selectplusFieldList&&selectplusFieldList.size()>0){
				for (JSONObject jsonObject : selectplusFieldList) {
					fieldlist.add(jsonObject);
				}
			}
			return fieldlist;
		}
		
	}
	private List<JSONObject> selectplusFieldList(String viewName){
		if (null == fieldlistCache) {
			fieldlistCache = new HashMap<>();
		}
		String selectField=viewName+"_selectPlus";
		if (!fieldlistCache.containsKey(selectField) || !cacheField) {
			JSONObject viewRelation = getViewRelation(viewName);
			String selectplus = viewRelation.getString("selectplus");
			if(StringUtils.isNotEmpty(selectplus)){
				List<JSONObject> selectplusFieldList=new ArrayList();
				JSONArray list=JSONArray.parseArray(selectplus);
				for (Object object : list) {
					JSONObject jsonObject= (JSONObject) object;
					String dict = jsonObject.getString("dict");
					if (!StringUtils.isEmpty(dict)) {
						jsonObject.put("dictData", getDictList(dict));
					}
					selectplusFieldList.add(jsonObject);
				}
				fieldlistCache.put(selectField, selectplusFieldList);
			}
		}
		return fieldlistCache.get(selectField);
	} 
	
	@Override
	public JSONObject saveOrUpdate(JSONObject jsonObject, String tableName, List<String> fieldList) {
		if(!jsonObject.containsKey("id")||StringUtils.isEmpty(jsonObject.getString("id"))){
			return add(jsonObject, tableName, fieldList);
		}else if(updateById(jsonObject, tableName, fieldList)){
			return jsonObject;
		}else{
			return null;
		}
	}

	@Override
	public boolean delete(String tableName, Object data, String key) {
		if(data == null)
			return false;
		List<Object> keys = new ArrayList<Object>();
		if(data instanceof JSONObject){
			JSONObject o = (JSONObject)data;
			if(o.isEmpty())
				return true;
			keys.add(o.getString(key));
		}else if(data instanceof  JSONArray){
			JSONArray d = (JSONArray)data;
			if(d.isEmpty())
				return true;
			for(int i =0; i < d.size(); i++){
				JSONObject o = d.getJSONObject(i);
				keys.add(o.getString(key));
			}
		}
		int count = superDao.deleteListByKey(tableName, key, SuperDao.KEYTYPE.STRING,keys);
		return (count>0);
	}
}