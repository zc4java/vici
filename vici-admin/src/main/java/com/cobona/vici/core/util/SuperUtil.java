package com.cobona.vici.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cobona.vici.core.support.HttpKit;

public class SuperUtil {
	public static String getWhere(List<JSONObject> fieldlist) {
		HttpServletRequest request = HttpKit.getRequest();
		String sql = "";
		for (JSONObject jsonObject : fieldlist) {
			int isSelect = jsonObject.getIntValue("isselect");
			if (isSelect == 1) {
				String field = jsonObject.getString("field");
				String fieldtype = jsonObject.getString("fieldtype");
				String selecttype = jsonObject.getString("selecttype");
				if (StringUtils.isNotEmpty(selecttype) && selecttype.indexOf("and") != -1) {
					String value1 = request.getParameter(field + "-1");
					String value2 = request.getParameter(field + "-2");
					
					//------
					String value3 = request.getParameter(field);
					if(value3!=null) {
						if(value1==null&&value2==null&&!value3.isEmpty()){
							String[] values = value3.split("~");
							value1=values[0];
							value2=values[1];
						}
					}
					//------
					
					if (null != jsonObject.getString("vfield")) {// 如果是view则替换字段为真实字段
						field = jsonObject.getString("vfield");
					}
					if (selecttype.startsWith("un")) {
						String subsql = "";
						String subselect = "";
						if (selecttype.equals("ungtandlt")) {
							subselect = "=";
						}
						if (StringUtils.isNotEmpty(value1)) {
							value1 = value1.trim();
							if (fieldtype.equals("int") || fieldtype.equals("double")) {
								subsql += "  " + field + "<" + subselect + value1 + " ";
							} else {
								subsql += "  " + field + "<" + "'" + value1 + "'";
							}
						}
						if (StringUtils.isNotEmpty(value2)) {
							value2 = value2.trim();
							if (!subsql.equals("")) {
								subsql += " or ";
							}
							if (fieldtype.equals("int") || fieldtype.equals("double")) {
								subsql += "  " + field + ">" + subselect + value2 + " ";
							} else {
								subsql += "  " + field + ">" + "'" + value2 + "'";
							}
						}
						if (!subsql.equals("")) {
							sql += " and (" + subsql + ")";
						}
					} else {
						if (StringUtils.isNotEmpty(value1)) {
							value1 = value1.trim();
							String select = "";
							if (selecttype.equals("gtandlt")) {
								select = ">";
							} else if (selecttype.equals("egtandelt")) {
								select = ">=";
							}
							if (fieldtype.equals("int") || fieldtype.equals("double")) {
								sql += " and " + field + select + value1 + " ";
							} else {
								sql += " and " + field + select + "'" + value1 + "'";
							}
						}
						if (StringUtils.isNotEmpty(value2)) {
							value2 = value2.trim();
							String select = "";
							if (selecttype.equals("gtandlt")) {
								select = "<";
							} else if (selecttype.equals("egtandelt")) {
								select = "<=";
							}
							if (fieldtype.equals("int") || fieldtype.equals("double")) {
								sql += " and " + field + select + value2 + " ";
							} else {
								sql += " and " + field + select + "'" + value2 + "'";
							}
						}
					}

				} else {
					String value = request.getParameter(field);
					if (null != jsonObject.getString("vfield")) {// 如果是view则替换字段为真实字段
						field = jsonObject.getString("vfield");
					}
					if (StringUtils.isNotEmpty(value)) {
						value = value.trim();
						String select = "=";
						if (selecttype.equals("gt")) {
							select = ">";
						} else if (selecttype.equals("egt")) {
							select = ">=";
						} else if (selecttype.equals("lt")) {
							select = "<";
						} else if (selecttype.equals("elt")) {
							select = "<=";
						} else if (selecttype.equals("uneq")) {
							select = "!=";
						} else if (selecttype.equals("like")) {
							select = "like";
							sql += " and " + field + " " + select + " '%" + value + "%' ";
							continue;
						}

						if (fieldtype.equals("int") || fieldtype.equals("double")) {
							sql += " and " + field + select + value + " ";
						} else {
							sql += " and " + field + select + "'" + value + "'";
						}
					}
				}

			}
		}
		System.out.println(sql);
		return sql;
	}

	public static String getPara(String name) {
		return HttpKit.getRequest().getParameter(name);
	}
	
	public static JSONObject initSelectField(List<JSONObject> fieldlist) {
		JSONObject returnObj=new JSONObject();
		List<JSONObject> list =new ArrayList();
		String date="";
		for (JSONObject jsonObject : fieldlist) {
			int isselect=jsonObject.getInteger("isselect");
			String selecttype=jsonObject.getString("selecttype");
			String fieldtype=jsonObject.getString("fieldtype");
			
			if(1==isselect&&StringUtils.isNotEmpty(selecttype)&&selecttype.indexOf("and")!=-1){
				JSONObject object1=(JSONObject) jsonObject.clone();
				JSONObject object2=(JSONObject) jsonObject.clone();
				String title=jsonObject.getString("title");
				String field=jsonObject.getString("field");
				object1.put("display", 0);
				object1.put("add", 0);
				object1.put("update", 0);
				object2.put("display", 0);
				object2.put("add", 0);
				object2.put("update", 0);
				
				
				jsonObject.put("isselect", 2);
				if (fieldtype.equals("date") || fieldtype.equals("datetime")) {
					
					object1.put("title", title);
					object1.put("field", field);
					list.add(object1);
					date += "laydate.render({elem: '#" + jsonObject.get("field") + "',"
							+ "range:'~'});";
					
					/*
					object1.put("title", "起始"+title);
					object1.put("field", field+"-1");
					list.add(object1);
					object2.put("title", "终止"+title);
					object2.put("field", field+"-2");
					list.add(object2);
					date += "var start = laydate.render({"+
			                "elem: '#"+field+"-1',"+
			                "done:function(value,date){"+
			                "    endMax = end.config.max;"+
			                "    end.config.min = date;"+
			                "    end.config.min.month = date.month -1;"+
			                "}"+
			            "});"+
			         "var end = laydate.render({"+
			               "elem: '#"+field+"-2',"+
			               "done:function(value,date){"+
			               "    if($.trim(value) == ''){"+
			               "        var curDate = new Date();"+
			                "       date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};"+
			               "    }"+
			               "    start.config.max = date;"+
			               "    start.config.max.month = date.month -1;"+
			               "}"+
			            "});";
					*/
					

				}else{
					object1.put("title", "最小"+title);
					object1.put("field", field+"-1");
					list.add(object1);
					object2.put("title", "最大"+title);
					object2.put("field", field+"-2");
					
					list.add(object2);
				}	
				
			}else{
				if (fieldtype.equals("date") || fieldtype.equals("datetime")) {
						date += "laydate.render({elem: '#" + jsonObject.get("field") + "'});";
				}	
			}
			list.add(jsonObject);
		}

		returnObj.put("date", date);
		returnObj.put("list", list);
		return returnObj;
	}
}
