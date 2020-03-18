package com.cobona.vici.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ElementRuleUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static Object getRuleForm(JSONObject jsonObject) {
		String inputtype = jsonObject.getString("inputtype");
		if(inputtype.equals("checkbox")||inputtype.equals("checkbox1")){
			return JSONArray.parse("[]");
		}else{
			return  "";
		}
	}

	public static JSONArray getRules(JSONObject jsonObject) {
		JSONArray jsonArray=new JSONArray();
		String verifyrole=jsonObject.getString("verifyrole");
		if(null==verifyrole){
			return jsonArray;
		}
		String[] verifyroles=verifyrole.split("|"); 
		
		String inputtype = jsonObject.getString("inputtype");
		String fieldtype = jsonObject.getString("fieldtype");
		String trigger="blur";//change OR blur
		if(fieldtype.equals("date")||fieldtype.equals("time")||fieldtype.equals("datetime")){
			trigger="change";
		}
		for (String string : verifyroles) {
			JSONObject jsonObject2=new JSONObject();
			
			jsonObject2.put("trigger", trigger);
		}
		
		return jsonArray;
	}

}
