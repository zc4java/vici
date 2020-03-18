package com.cobona.vici.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

	private final static Map<String, String[]> ROLE_MAP = new HashMap<String, String[]>() {
		{
			put("required", new String[] { "[\\S]+", "必填" });
			put("phone", new String[] { "^1\\d{10}$", "手机号格式不正确" });
			put("email", new String[] { "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$",
					"邮箱格式不正确" });
			put("url", new String[] { "(^#)|(^http(s*):\\/\\/[^\\s]+\\.[^\\s]+)", "链接格式不正确" });
			put("number", new String[] { "^\\d*(.)?(\\d*)?", "数字格式不正确" });
			put("date",
					new String[] {
							"^(\\d{4})[-\\/](\\d{1}|0\\d{1}|1[0-2])([-\\/](\\d{1}|0\\d{1}|[1-2][0-9]|3[0-1]))*$",
							"日期格式不正确" });
			put("identity", new String[] { "(^\\d{15}$)|(^\\d{17}(x|X|\\d)$)", "身份证号格式不正确" });
			put("ip", new String[] { "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))", "ip地址格式不正确" });
		}
	};
	private final static Map<String, String> JAVA_ROLE = new HashMap<String, String>() {
		{
			put("minmax", "");
			put("min", "");
			put("max", "");
			put("lengthminmax", "");
			put("lengthmin", "");
			put("lengthmax", "");
		}
	};

	public static String validate(Object object, String verifyRole) {
		String str = "";
		try {
			String[] roles = verifyRole.split("\\|");
			for (String role : roles) {
				if (ROLE_MAP.containsKey(role)) {
					boolean rs = validateRegex(object.toString(), ROLE_MAP.get(role)[0]);
					if (!rs) {
						str += ROLE_MAP.get(role)[1];
					}
				} else {
					str += validateJavaRole(object, role);
				}
			}
			return str;
		} catch (Exception e) {
			return str +="数据校验出错"+verifyRole;
			// TODO: handle exception
		}
	}

	
	public static void main(String[] args) {
		System.out.println(validate("255.20.0.5", "ip"));
		System.out.println(validate("-2", "required|minmax_2_33"));
		System.out.println(validate("33", "required|min_44"));
		System.out.println(validate("222", "required|max_122"));
		System.out.println(validate("1928-1", "required|lengthminmax_2_23"));
		System.out.println(validate("1355263717022222X", "required|lengthmax_2"));
		System.out.println(validate("1355263717022222X", "required|lengthmax_22"));
	}
	private static String validateJavaRole(Object object,String verifyRole){
		String str="";
		String[] roleStrs=verifyRole.split("_");
		if(JAVA_ROLE.containsKey(roleStrs[0])){
			if(roleStrs[0].equals("minmax")){
				double value=Double.parseDouble(object.toString());
				double min=Double.parseDouble(roleStrs[1]);
				double max=Double.parseDouble(roleStrs[2]);
				if(value<min){
					str="不得小于"+roleStrs[1];
				}else if(value>max){
					str="不得大于"+roleStrs[2];
				}
			}else if(roleStrs[0].equals("min")){
				double value=Double.parseDouble(object.toString());
				double min=Double.parseDouble(roleStrs[1]);
				if(value<min){
					str="不得小于"+roleStrs[1];
				}
			}else if(roleStrs[0].equals("max")){
				double value=Double.parseDouble(object.toString());
				double max=Double.parseDouble(roleStrs[1]);
				if(value>max){
					str="不得大于"+roleStrs[1];
				}
			}else  if(roleStrs[0].equals("lengthminmax")){
				int leng=object.toString().length();
				int lengmin=Integer.parseInt(roleStrs[1]);
				int lengmax=Integer.parseInt(roleStrs[2]);
				if(leng<lengmin){
					str="长度不得小于"+roleStrs[1];
				}else if(leng>lengmax){
					str="长度不得大于"+roleStrs[2];
				}
			}else if(roleStrs[0].equals("lengthmin")){
				int leng=object.toString().length();
				int lengmin=Integer.parseInt(roleStrs[1]);
				if(leng<lengmin){
					str="长度不得小于"+roleStrs[1];
				}
			}else if(roleStrs[0].equals("lengthmax")){
				int leng=object.toString().length();
				int lengmax=Integer.parseInt(roleStrs[1]);
				if(leng>lengmax){
					str="长度不得大于"+roleStrs[1];
				}
			}
		}
		
		return str;
	}

	private static boolean validateRegex(String str, String regEx) {
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.matches();
		return rs;
	}
}
