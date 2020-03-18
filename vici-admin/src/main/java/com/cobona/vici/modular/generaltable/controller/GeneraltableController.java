package com.cobona.vici.modular.generaltable.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.cobona.vici.common.constant.factory.PageFactory;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.DictMapper;
import com.cobona.vici.common.persistence.dao.MetadataMapper;
import com.cobona.vici.config.properties.ViciProperties;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.base.tips.ErrorTip;
import com.cobona.vici.core.base.tips.SuccessTip;
import com.cobona.vici.core.shiro.ShiroKit;
import com.cobona.vici.core.support.HttpKit;
import com.cobona.vici.core.util.ElementRuleUtil;
import com.cobona.vici.core.util.SuperUtil;
import com.cobona.vici.core.util.ValidatorUtil;
import com.cobona.vici.modular.system.dao.SuperDao;
import com.cobona.vici.modular.system.service.SuperService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.word.WordExportUtil;

/**
 * 通用查询控制器
 *
 * @author jinchm
 * @Date 2018-01-11 00:21:41
 */
@Controller
@RequestMapping("/generaltable")
public class GeneraltableController extends BaseController {
	@Value("${vici.view_prefix}")
	private String VIEW_PREFIX;
	@Value("${vici.teble_prefix}")
	private String TABLE_PREFIX;
	// private String TABLE_PREFIX=""; // 业务表前缀
	// @Autowired
	// private IGeneraltableService generaltableService;
	@Resource
	private ViciProperties viciProperties;


	@Autowired
	private SuperService superService;
	@Resource
	DictMapper dictMapper;
	@Resource
	MetadataMapper metadataMapper;
	@Value("${vici.cache-field}")
	private boolean cacheField = false; // 是否开启字段缓存，(true/false)
	/*private Map<String, List<JSONObject>> fieldlistCache;
	private Map<String, List<JSONObject>> dictCache;
	private Map<String, Map<String, String>> dictMap;*/

	@Resource
	SuperDao superDao;

	
	
	
	
	/**
	 * 跳转到通用查询首页
	 */
	@RequestMapping("/{tablename}")
	public String index(@PathVariable String tablename, Model model) {
		model.addAttribute("tablename", tablename);
		addParentPageParams(  model);
		Subject subject = SecurityUtils.getSubject();
		List<JSONObject> menulist = superService.selectSql(
				"select name,icon,num,url from menu where pcode= (select code from menu where url='/generaltable/"
						+ tablename + "')order by num;");
		// String[] permitsStr = new String[] { "add", "update", "delete",
		// "list", "detail", "import", "export","downtemp" };
		List<JSONObject> permissionList = new ArrayList<>();
		for (JSONObject jsonObject : menulist) {
			String url = jsonObject.getString("url");
			if (subject.isPermitted(url)) {
				jsonObject.put("bt", url.substring(url.lastIndexOf("/") + 1));
				permissionList.add(jsonObject);
			}
		}
		// model.addAttribute("permissionList",permissionList);
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		String templetScript=superService.getFieldTempletScript(fieldlist);
		List<JSONObject> showField = superService.getShowField(fieldlist, TABLE_PREFIX +tablename);
		if (null != fieldlist && fieldlist.size() > 0 && null != fieldlist.get(0)) {
			model.addAttribute("tablenamecn", fieldlist.get(0).getString("tablenamecn"));
		}
		JSONObject returnObj=SuperUtil.initSelectField(fieldlist);
		fieldlist=(List<JSONObject>) returnObj.get("list");
		model.addAttribute("date", returnObj.get("date"));
		model.addAttribute("showField", showField);
		model.addAttribute("templetScript", templetScript);
		JSONObject jsonObject = new JSONObject();
		List<JSONObject> selectfieldlist=superService.selectFieldAdd(fieldlist,TABLE_PREFIX +tablename);
		jsonObject.put("fieldlist", selectfieldlist);
		//System.out.println(JSON.toJSONString(permissionList));
		jsonObject.put("permissionList", permissionList);
		model.addAttribute("fieldlist", jsonObject);
		// model.addAttribute("list",list);
		// List<Map<String, Object>> resultlist=
		// generaltableService.getOneTableList(object);
		String page = "list.html";
		
		JSONObject relation = superService.getViewRelation(TABLE_PREFIX + tablename);
		if (null!=relation) {
			String addview = relation.getString("listview");
			if (!StringUtils.isEmpty(addview)) {
				page = addview;
			}
		}else{
			relation= JSON.parseObject("{toolbar:0,toolbarwidth:200}");
		}
		
		JSONObject user =  getCurrentUser();
		
		model.addAttribute("user",user);
		model.addAttribute("relation", relation);
		return VIEW_PREFIX + page;
	}

	
	


	/**
	 * 获取某个页面下的按钮
	 */
	@RequestMapping(value = "/menu/{tablename}")
	@ResponseBody
	public Object getMenuByPage(@PathVariable String tablename) {
		Subject subject = SecurityUtils.getSubject();
		List<JSONObject> menulist = superService.selectSql(
				"select name,icon,url from menu where pcode= (select code from menu where url='/generaltable/"
						+ tablename + "');");
		
		List<JSONObject> permissionList = new ArrayList<>();
		for (JSONObject jsonObject : menulist) {
			String url = jsonObject.getString("url");
			if (subject.isPermitted(url)) {
				jsonObject.put("bt", url.substring(url.lastIndexOf("/") + 1));
				permissionList.add(jsonObject);
			}
		}
		return permissionList;
	}

	
	

	// 获取要保存是数据和字段List
	private JSONObject initSaveObject(List<JSONObject> fieldlist) {
		HttpServletRequest request = HttpKit.getRequest();
		JSONObject saveObj = new JSONObject();
		List<String> fields = new ArrayList<>();
		List<String> tablenames = new ArrayList<>();
		Map<String, String> tablenameMap = new HashMap<>();
		String tableStr = null;
		String[] tablenamelist =null;
		for (JSONObject jsonObject : fieldlist) {
			String field = jsonObject.getString("field");
			String fieldtype = jsonObject.getString("fieldtype");
			String tablename = jsonObject.getString("tablename");
			String tablenamectual = jsonObject.getString("tablename");
			String vfield = jsonObject.getString("vfield");
			if (tablename.endsWith("view")) {
				tablenamectual = jsonObject.getString("tablenamectual");
				if(null==tablenamelist){
					tablenamelist = superService.getViewField(tablename).get("tablename").split(",");
					for (String tablenameone : tablenamelist) {
						if (!tablenameMap.containsKey(tablenameone)) {
							if (null == tableStr) {
								tableStr = tablenameone;
							} else {
								tableStr = tableStr + "," + tablenameone;
							}
							tablenameMap.put(tablenameone, tablenameone);
							tablenames.add(tablenameone);
						}
					}
				}
			} else {
				if (!tablenameMap.containsKey(tablename)) {
					if (null == tableStr) {
						tableStr = tablename;
					} else {
						tableStr = tableStr + "," + tablename;
					}
					tablenameMap.put(tablename, tablename);
					tablenames.add(tablename);
				}
				vfield = field;
			}
			fields.add(vfield);

			// String fieldtype=jsonObject.getString("fieldtype");
			String value = request.getParameter(field);
			// System.out.println(request.getAttribute(field));;
			// if(!StringUtils.isEmpty(value)){
			// value=value.trim();
			if (StringUtils.isEmpty(value) && (!fieldtype.equals("char") && !fieldtype.equals("varchar")
					&& !fieldtype.equals("text") && !fieldtype.equals("longtext"))) {

			} else {
				saveObj.put(vfield, value);
			}

			// }

		}
		saveObj.put("fields_list", fields);
		saveObj.put("tablename", tablenames);
		return saveObj;
	}

	private String getRequestParameter(String name) {
		HttpServletRequest request = HttpKit.getRequest();
		String value = request.getParameter(name);
		return value;
	}

	

	/*
	 * private JSONObject getExportFields(List<JSONObject> fieldlist) {
	 * JSONObject object = new JSONObject(); List<String> fieldList = new
	 * ArrayList<>(); Map<String, String> fieldTitleMap = new HashMap<>();
	 * Map<String, String> dicNamesMap = new HashMap<>(); for (JSONObject
	 * jsonObject : fieldlist) { int export = jsonObject.getIntValue("export");
	 * if (export == 1) { fieldList.add(jsonObject.getString("field"));
	 * fieldTitleMap.put(jsonObject.getString("field"),
	 * jsonObject.getString("title")); String dict =
	 * jsonObject.getString("dict"); if (!StringUtils.isEmpty(dict)) {
	 * dicNamesMap.put(jsonObject.getString("field"),
	 * jsonObject.getString("dict")); } } } object.put("fieldList", fieldList);
	 * object.put("fieldTitleMap", fieldTitleMap); object.put("dicNamesMap",
	 * dicNamesMap); return object; }
	 */
	
	/**
	 * 将来自页面的参数传回页面
	 * @param model
	 */
	private void addParentPageParams( Model model){
		String parentPageParams=getRequestParameter("parentPageParams");
		JSONObject parentPageParamsObj=new JSONObject();
		try {
			if(StringUtils.isNotBlank(parentPageParams)){
				parentPageParamsObj=JSON.parseObject(parentPageParams);
			}else{
				parentPageParamsObj.put("msg", "没有来自父页面的参数");
			}
		} catch (Exception e) {
			parentPageParamsObj.put("msg", "参数无法解析为json");
			parentPageParamsObj.put("parentPageParams", parentPageParams);
		}
		
		model.addAttribute("parentPageParams", parentPageParamsObj);
	}

	/**
	 * 跳转到添加通用新增或编辑
	 */
	@RequestMapping("/{tablename}/add")
	public String add(@PathVariable String tablename, Model model) {
		model.addAttribute("tablename", tablename);
		addParentPageParams(model);
		String id = getRequestParameter("id");
		
		String title = "新增";
		String page = "add.html";
		JSONObject valueObject = null;
		if (!StringUtils.isEmpty(id)) {
			title = "编辑";
			if (tablename.endsWith("view")) {
				valueObject = superService.selectViewById(Integer.parseInt(id), TABLE_PREFIX + tablename, null);
			} else {
				valueObject = superService.selectById(Integer.parseInt(id), TABLE_PREFIX + tablename, null);
			}
			page = "edit.html";
		}
		JSONObject relation = superService.getViewRelation(TABLE_PREFIX + tablename);
		if (null!=relation) {
			if (page.equals("add.html")) {
				String addview = relation.getString("addview");
				if (!StringUtils.isEmpty(addview)) {
					page = addview;
				}
			} else {
				String editview = relation.getString("editview");
				if (!StringUtils.isEmpty(editview)) {
					page = editview;
				}
			}
		}
		String date = "";// 日期时间组件初始化
		String rate = "";
		String upload = "";
		String editor="";
		String evaluate="";
		String selects="";
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		JSONObject ruleForm = new JSONObject();
		JSONObject rules = new JSONObject();
		// 添加日期编辑
		for (JSONObject jsonObject : fieldlist) {
			jsonObject.remove("templetscript");//删除模版脚本
			String fieldtype = jsonObject.getString("fieldtype");
			String field = jsonObject.getString("field");
			String inputtype = jsonObject.getString("inputtype");
		
			ruleForm.put(field, ElementRuleUtil.getRuleForm(jsonObject));
			rules.put(field, ElementRuleUtil.getRules(jsonObject));
			if (fieldtype.equals("date") || fieldtype.equals("datetime")) {
				SimpleDateFormat sdf = null;
				if (fieldtype.equals("date")) {
					date += "laydate.render({elem: '#" + jsonObject.get("field") + "'});";
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					date += "laydate.render({elem: '#" + jsonObject.get("field") + "',type: 'datetime'});";
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
				if (null == valueObject || null == valueObject.getDate(jsonObject.getString("field"))) {
					jsonObject.put("value", null);
				} else {
					jsonObject.put("value", sdf.format(valueObject.getDate(jsonObject.getString("field"))));
				}

			} else if (inputtype.equals("rate")) {
				if (null != valueObject) {
					jsonObject.put("value", valueObject.get(jsonObject.getString("field")));
					rate += "rate.render({elem: '#" + jsonObject.get("field") + "',text: true,value:"
							+ valueObject.get(jsonObject.getString("field")) + ",choose: function(value){$('input[name=\""+jsonObject.get("field")+"\"]').val(value)}});";
				} else {
					jsonObject.put("value", 0);
					rate += "rate.render({elem: '#" + jsonObject.get("field") + "',text: true,choose: function(value){$('input[name=\""+jsonObject.get("field")+"\"]').val(value)}});";
				}

			}else if (inputtype.equals("tags")) {
				if (null != valueObject) {
					jsonObject.put("value", valueObject.get(jsonObject.getString("field")));
				} else {
					jsonObject.put("value", 0);
				}
				evaluate+="var evalute_list=[];"+
						 "  $('.evaluate_label').each(function(i,e){"+
							"	evalute_list.push($(this).text());"+
							"	 list_values=$('#"+jsonObject.getString("field")+"').val();"+
							"   	list_values=list_values.replace(/(\\s*$)/g,'');"+
							"   	 list_value=list_values.split(' ');"+
							"	 for(var j=0;j<list_value.length;j++){"+
							"		   if(list_value[j]==$(this).text()){"+
							"			   $(this).toggleClass('layui-bg-blue');"+
							"		   }"+
							"	   }  "+
							"   $(this).click(function(){"+
							"	   var list_values=$('#"+jsonObject.getString("field")+"').val();"+
							"	   list_values=list_values.replace(/(\\s*$)/g,'');"+
							"	   var list_value=list_values.split(',');"+
							"	   var swit;"+
							"	   var value;"+
							"	   for(var j=0;j<list_value.length;j++){"+
							"		   if(list_value[j]==evalute_list[i]){"+
							"			   swit=true;"+
							"			   value=j;"+
							"		   }"+
							"	   }"+
							"	   if(swit){"+
							"		   list_value.splice(value,1);"+
							"	  	   for(var j=0;j<list_value.length;j++){"+
							"		      if(list_value[j]==$(this).html()){"+
							"				 $('#"+jsonObject.getString("field")+"').val(list_value.join(' ') +' ');"+
							"                 return;"+
							"		       } "+
							"	       }"+
							"		   $(this).removeClass('layui-bg-blue');"+
							"	   }else  {"+
							"		  list_value.push(evalute_list[i]);"+
							"		$(this).addClass('layui-bg-blue');"+
							"	   }"+
							//" console.log(list_value)"+
							"		$('#"+jsonObject.getString("field")+"').val(list_value.join(',') +' ');console.log(list_value)"+
							"   })"+
							"  });";

			}else if (inputtype.equals("editor")) {
				editor+="var E = window.wangEditor;"+
				        "var editor = new E('#"+jsonObject.getString("field")+"');"+
				        "var $text1 = $('[name=\""+jsonObject.getString("field")+"\"]');"+
				        "editor.customConfig.onchange = function (html) {"+
				       // "debugger;html=filterXSS(html); "+
				         " $text1.val(html);"+
				        "};"+
				        "editor.customConfig.uploadImgShowBase64 = true;"+
				        " editor.customConfig.zIndex = 1;editor.create();";
				if (null != valueObject) {
					jsonObject.put("value", valueObject.get(jsonObject.getString("field")));
					
					//editor+="editor.txt.html('"+valueObject.get(jsonObject.getString("field"))+"');";
					//editor+="console.log('"+valueObject.get(jsonObject.getString("field"))+"');";
					//editor+="console.log($('[name=\""+jsonObject.getString("field")+"\"]').html());";
					
					editor+="editor.txt.html(HTMLDecode($('[name=\""+jsonObject.getString("field")+"\"]').html()));";
				} 
				
			}else if (inputtype.equals("selects")||inputtype.equals("tree")||inputtype.equals("trees")) {
				if(inputtype.equals("selects")||inputtype.equals("trees")){
					selects+=" formSelects.config('"+jsonObject.getString("field")+"', {keyVal: 'num'}).data('"+jsonObject.getString("field")+"', 'local', {"+
							"arr: "+JSON.toJSONString(jsonObject.get("dictData")).replaceAll("\"", "'")+"});";
				}else{
					selects+=" formSelects.config('"+jsonObject.getString("field")+"', {keyVal: 'id'}).data('"+jsonObject.getString("field")+"', 'local', {"+
							"arr: "+JSON.toJSONString(jsonObject.get("dictData")).replaceAll("\"", "'")+",linkage: true});";
				}
				selects+="formSelects.on('"+jsonObject.getString("field")+"', function(id, vals, val, isAdd, isDisabled){var value=formSelects.value(id, 'val');jQuery('#'+id).val( JSON.stringify(value));}, true);";
				if (null != valueObject) {
					Object vObject=valueObject.get(jsonObject.getString("field"));
					if(null==vObject||vObject.equals("")) {
						vObject="[]";
					}
					jsonObject.put("value",vObject );
					selects+="formSelects.value('"+jsonObject.getString("field")+"', "+vObject+");";
				} 
				
			}else if (inputtype.equals("images")||inputtype.equals("multipleImages")||inputtype.equals("file")||inputtype.equals("video")||inputtype.equals("audio")) {
				jsonObject.put("uploadbutton", jsonObject.get("field")+"uploadbutton");
				jsonObject.put("demoid", jsonObject.get("field")+"demoid");
				jsonObject.put("demoText", jsonObject.get("field")+"demoText");
				if (null != valueObject) {
					Object v=valueObject.get(jsonObject.getString("field"));
					jsonObject.put("value", v);
				    if(inputtype.equals("multipleImages")){
				    	List<String> srcList=new ArrayList<>();
				    	List<String> vList=new ArrayList<>();
				    	if(null!=v){
				    		String vString=v.toString();
				    		if(StringUtils.isNotEmpty(vString)){
				    			String[] vStringArray=vString.split(",");
				    			for (int i = 0; i < vStringArray.length; i++) {
				    				if(StringUtils.isNotEmpty(vStringArray[i])){
				    					String str=vStringArray[i].replace("[", "").replace("]", "");
							    		srcList.add("/generaltable/filedownload/"+str);
							    		vList.add(str);
				    				}
								}
				    		}
				    	}
				    	jsonObject.put("srcList", srcList);
				    	jsonObject.put("vList", vList);
				    }else{
				    	jsonObject.put("src", "/generaltable/filedownload/"+v);
				    }
				}
				String hrefcode="src";
				if(inputtype.equals("file")){
					hrefcode="href";
				}
				if(inputtype.equals("multipleImages")){
					upload+=" upload.render({"+
					   " elem: '#"+jsonObject.get("field")+"uploadbutton'"+
					    ",url: '/generaltable/upload/'"+
					    ",multiple: true"+
					    ",before: function(obj){"+
					    "  obj.preview(function(index, file, result){"+
					    
					     " });"+
					    "}"+
					   " ,done: function(res){"+
						   "if(res.code !=200){"+
				             "return layer.msg('上传失败');"+
						   "}else{"+ 
				             "debugger;"+
				             "    $('#"+jsonObject.get("field")+"demoid').append('<img src=\"/generaltable/filedownload/'+res.message.id+'\"  class=\"layui-upload-img\" fieldid=\""+jsonObject.get("field")+"\" value='+res.message.id+' onclick=\"showMultipleImages(this)\">');"+
				            "$('#"+jsonObject.get("field")+"').val($('#"+jsonObject.get("field")+"').val()+\",[\"+res.message.id+\"]\");"+ 
				            "}},"+
					  "});";
				}else{
					upload+="var "+jsonObject.get("field")+"uploadInst = upload.render("+
							"{elem: '#"+jsonObject.get("field")+"uploadbutton',"+
							   "url: '/generaltable/upload/',"+
							   "accept: '"+inputtype+"',"+
					         "done: function(res){ "+
							   "if(res.code !=200){"+
					             "return layer.msg('上传失败');"+
							   "}else{"+ 
					             "debugger;"
					             + "$('#"+jsonObject.get("field")+"demoid').attr('"+hrefcode+"', '/generaltable/filedownload/'+res.message.id);"+
					              "$('#"+jsonObject.get("field")+"demoid').parent().show();"+
							     "$('#"+jsonObject.get("field")+"').val(res.message.id);"+ 
					            "}},"+
					         "error: function(){"+
					            "var demoText = $('#"+jsonObject.get("field")+"demoText');"+
					            "demoText.html('<span style=\"color: #FF5722;\">上传失败</span> <a class=\"layui-btn layui-btn-mini "+jsonObject.get("field")+"demo-reload\">重试</a>');"+
					            "demoText.find('."+jsonObject.get("field")+"demo-reload').on('click', function(){"+
					    	     jsonObject.get("field")+"uploadInst.upload(); }); "
					    	 + "} });";
				}
				
				
				
				/*var uploadInst = upload.render({
				    elem: '#test1'
				    ,url: '/upload/'
				    ,before: function(obj){
				      //预读本地文件示例，不支持ie8
				      obj.preview(function(index, file, result){
				        $('#demo1').attr('src', result); //图片链接（base64）
				      });
				    }
				    ,done: function(res){
				      //如果上传失败
				      if(res.code > 0){
				        return layer.msg('上传失败');
				      }
				      //上传成功
				    }
				    ,error: function(){
				      //演示失败状态，并实现重传
				      var demoText = $('#demoText');
				      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
				      demoText.find('.demo-reload').on('click', function(){
				        uploadInst.upload();
				      });
				    }
				  });*/
				
				
			} else if (null != valueObject) {
				jsonObject.put("value", valueObject.get(jsonObject.getString("field")));
			}

		}
		if (null != fieldlist && fieldlist.size() > 0 && null != fieldlist.get(0)) {
			model.addAttribute("tablenamecn", title + fieldlist.get(0).getString("tablenamecn"));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fieldlist", fieldlist);
		JSONObject user =  getCurrentUser();
		
		model.addAttribute("user",user);
	
		
		model.addAttribute("fieldlist", JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect));
		model.addAttribute("date", date);
		model.addAttribute("rate", rate);
		model.addAttribute("upload", upload);
		model.addAttribute("editor", editor);
		model.addAttribute("evaluate", evaluate);
		model.addAttribute("selects", selects);
		
		return VIEW_PREFIX + page;
	}
	
	private JSONObject getCurrentUser(){
		JSONObject user = new JSONObject();
		Integer currentLoginUid = ShiroKit.getUser().getId();
		Integer currentDeptId = ShiroKit.getUser().getDeptId();
		List<Integer> roleList = ShiroKit.getUser().getRoleList();
		user.put("userid", currentLoginUid);
		user.put("deptid", currentDeptId);
		user.put("roleList", roleList);
		return user;
	}

	@RequestMapping("/{tablename}/detail/{id}")
	public String detail(@PathVariable("tablename") String tablename, @PathVariable("id") int id, Model model) {
		model.addAttribute("tablename", tablename);
		addParentPageParams(  model);
		// String id=getRequestParameter("id");
		String title = "详情";
		String page = "detail.html";
		JSONObject valueObject = null;
		JSONObject relation = superService.getViewRelation(TABLE_PREFIX + tablename);

		if (null!=relation) {
			String detailview = relation.getString("detailview");
			if (!StringUtils.isEmpty(detailview)) {
				page = detailview;
			}
		}
		if (tablename.endsWith("view")) {
			valueObject = superService.selectInitViewById(id, TABLE_PREFIX + tablename, null);
		} else {
			valueObject = superService.selectInitObjectById(id, TABLE_PREFIX + tablename, null);
		}
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		for (JSONObject jsonObject : fieldlist) {
			jsonObject.remove("templetscript");//删除模版脚本
			String inputtype=jsonObject.getString("inputtype");
			Object v=valueObject.get(jsonObject.getString("field"));
			jsonObject.put("value", v);
		    if(inputtype.equals("multipleImages")){
		    	List<String> srcList=new ArrayList<>();
		    	List<String> vList=new ArrayList<>();
		    	if(null!=v){
		    		String vString=v.toString();
		    		if(StringUtils.isNotEmpty(vString)){
		    			String[] vStringArray=vString.split(",");
		    			for (int i = 0; i < vStringArray.length; i++) {
		    				if(StringUtils.isNotEmpty(vStringArray[i])){
		    					String str=vStringArray[i].replace("[", "").replace("]", "");
					    		srcList.add("/generaltable/filedownload/"+str);
					    		vList.add(str);
		    				}
						}
		    		}
		    	}
		    	valueObject.put(jsonObject.getString("field")+"srcList", srcList);
		    	valueObject.put(jsonObject.getString("field")+"vList", vList);
		    }else if(inputtype.equals("images")||inputtype.equals("file")||inputtype.equals("video")||inputtype.equals("audio")){
		    	valueObject.put(jsonObject.getString("field")+"src", "/generaltable/filedownload/"+v);
		    	
		    }
		}
		if (null != fieldlist && fieldlist.size() > 0 && null != fieldlist.get(0)) {
			title= fieldlist.get(0).getString("tablenamecn")+title ;
		}
		model.addAttribute("tablenamecn", title );
		JSONObject jsonObject=new JSONObject();
		JSONObject user =  getCurrentUser();
		model.addAttribute("user",user);
		jsonObject.put("data", valueObject);
		jsonObject.put("fieldlist", fieldlist);
		model.addAttribute("data", jsonObject);
		
		return VIEW_PREFIX + page;
	}

	/**
	 * 跳转到修改通用查询
	 *//*
		 * @RequestMapping("/generaltable_update/{generaltableId}") public
		 * String generaltableUpdate(@PathVariable Integer generaltableId, Model
		 * model) { Generaltable generaltable =
		 * generaltableService.selectById(generaltableId);
		 * model.addAttribute("item", generaltable);
		 * LogObjectHolder.me().set(generaltable); return VIEW_PREFIX +
		 * "generaltable_edit.html"; }
		 */

	@RequestMapping(value = "/{tablename}/selectbyid/{id}")
	@ResponseBody
	public Object selectById(@PathVariable String tablename,@PathVariable("id") String id) {
		
		JSONObject valueObject = null;
		if (!StringUtils.isEmpty(id)) {
		
			if (tablename.endsWith("view")) {
				valueObject = superService.selectViewById(Integer.parseInt(id), TABLE_PREFIX + tablename, null);
			} else {
				valueObject = superService.selectById(Integer.parseInt(id), TABLE_PREFIX + tablename, null);
			}
			
		}
		return valueObject;
	}
	
	/**
	 * 获取通用查询列表
	 */
	@RequestMapping(value = "/{tablename}/list")
	@ResponseBody
	public Object list(@PathVariable String tablename) {
		Page page = new PageFactory().defaultPage();
		/*
		 * JSONObject object=JSON.parseObject(condition); List<Map<String,
		 * Object>> resultlist= generaltableService.getOneTableList(object);
		 * String json = JSON.toJSONString(resultlist,
		 * SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.
		 * WriteDateUseDateFormat); JSONArray jsonArray =
		 * JSONArray.parseArray(json);
		 */
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		String where = SuperUtil.getWhere(fieldlist);
		List<JSONObject> list;
		if (tablename.endsWith("view")) {
			list = superService.selectViewListPage(TABLE_PREFIX + tablename, where, page, null);
		} else {
			list = superService.selectListPage(TABLE_PREFIX + tablename, where, page, null);
		}
		list = superService.initQureyList(fieldlist, list);
		JSONObject object = new JSONObject();
		
		object.put("data", list);
		object.put("code", 0);
		object.put("msg", "");
		object.put("count", page.getTotal());
		return object;
	}

	/**
	 * 获取通用查询列表
	 */
	@RequestMapping(value = "/select/{id}")
	@ResponseBody
	public Object select(@PathVariable int id) {
		

		return superService.doEventhing(id,new JSONObject());
	}

	/**
	 * excel通用导出
	 */
	@RequestMapping(value = "/{tablename}/export")
	public void export(@PathVariable String tablename, HttpServletResponse resp) {
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		List<JSONObject> list = null;
		String temp = HttpKit.getRequest().getParameter("temp");// temp=1时导出空模版
		if (null != temp && temp.equals("1")) {
			list = new ArrayList<>();
			temp = "imp";
		} else {
			temp = "exp";
			Page page = new PageFactory().defaultPage();
			page.setCurrent(1);
			page.setSize(60000);// 当前只支持60000条数据导出
			String where = SuperUtil.getWhere(fieldlist);
			if (tablename.endsWith("view")) {
				list = superService.selectViewListPage(TABLE_PREFIX + tablename, where, page, null);
			} else {
				list = superService.selectListPage(TABLE_PREFIX + tablename, where, page, null);
			}
			list = superService.initQureyList(fieldlist, list);
		}
		String tablenamecn = "";
		if (null != fieldlist && fieldlist.size() > 0 && null != fieldlist.get(0)) {
			tablenamecn = fieldlist.get(0).getString("tablenamecn");
		}
		try {
			Workbook workbook = excelExport(tablenamecn, "sheet", fieldlist, list, temp);
			OutputStream output = resp.getOutputStream();
			resp.reset();
			tablenamecn = new String(tablenamecn.getBytes("UTF-8"), "ISO-8859-1");
			resp.setHeader("Content-disposition", "attachment; filename=" + tablenamecn + ".xls");
			resp.setContentType("application/msexcel");
			workbook.write(output);
			if (null != output) {
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * excel模版下载
	 */
	@RequestMapping(value = "/download/{filename}")
	public void download(@PathVariable String filename, HttpServletResponse resp) {
		filename = filename + ".xls";
		String filenameiso = "";
		try {
			filenameiso = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		resp.setHeader("content-type", "application/octet-stream");
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=" + filenameiso);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = resp.getOutputStream();
			String fileSavePath = viciProperties.getFileUploadPath();
			bis = new BufferedInputStream(new FileInputStream(new File(fileSavePath + filename)));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * 模版导出excel或word
	 */
	@RequestMapping(value = "/tempexport/{id}")
	public void tempexport(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject tempExport=superService.selectById(id, "tempexport", null);
		String filename=tempExport.getString("name")+".docx";
		JSONObject savefile=superService.selectById(tempExport.getIntValue("templeid"), "file", null);
		
		JSONObject dataObj=superService.getdata(tempExport.getString("datacode"), null);
		String tempFilename =viciProperties.getFileUploadPath()+File.separator+savefile.getString("path");
		if(1==tempExport.getIntValue("type")){
			exportWord(tempFilename,viciProperties.getFileUploadPath()+File.separator+"temp1",filename,dataObj,request,response);
		}else{
			exportExcel(tempFilename,viciProperties.getFileUploadPath()+File.separator+"temp1",filename,dataObj,request,response);
		}
	        
		
	}
	
	private static void exportExcel(String tempFilename, String string, String filename, JSONObject dataObj,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}


	/**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     * @param templatePath word模板地址
     * @param temDir 生成临时文件存放地址
     * @param fileName 文件名
     * @param params 替换的参数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(templatePath,"模板路径不能为空");
        Assert.notNull(temDir,"临时文件路径不能为空");
        Assert.notNull(fileName,"导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
        if (!temDir.endsWith("/")){
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //delAllFile(temDir);//这一步看具体需求，要不要删
        }

    }
	/**
	 * excel导入
	 */
	@RequestMapping(value = "/{tablename}/import")
	@ResponseBody
	public Object importexcel(@PathVariable String tablename, @RequestPart("file") MultipartFile file) {
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
		String pictureName = df.format(System.currentTimeMillis());
		String fileSavePath = "";
		try {
			fileSavePath = viciProperties.getFileUploadPath();
			pictureName = pictureName + file.getOriginalFilename();
			fileSavePath = fileSavePath + pictureName;
			file.transferTo(new File(fileSavePath));
		} catch (Exception e) {
			throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(1);
		params.setHeadRows(1);
		// params.setDataHandler(new MapImportHandler());
		List<Map<String, Object>> datalist = ExcelImportUtil.importExcel(new File(fileSavePath), Map.class, params);
		JSONObject result = checkData(datalist, fieldlist);
		if (result.getBooleanValue("pass")) {

			List<JSONObject> dataValueList = (List<JSONObject>) result.get("dataValueList");
			superService.addList(dataValueList, TABLE_PREFIX + tablename, null);
			SuccessTip successTip = super.SUCCESS_TIP;
			return super.SUCCESS_TIP;
		} else {
			List<JSONObject> errorCellList = (List<JSONObject>) result.get("errorCellList");
			String msg = result.getString("msg");

			setCellComment(fileSavePath, errorCellList);
			JSONObject JSONStr = new JSONObject();
			JSONStr.put("msg", msg);
			JSONStr.put("filename", pictureName);
			SuccessTip successTip = super.SUCCESS_TIP;
			successTip.setCode(333);
			successTip.setMessage(JSONStr.toJSONString());
			return super.SUCCESS_TIP;
		}
	}

	/**
	 * 文件上传
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Object upload(@RequestPart("file") MultipartFile file) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String pictureName = df.format(System.currentTimeMillis());
		String fileSavePath = "";
		JSONObject JSONStr = new JSONObject();
		
		try {
			fileSavePath = viciProperties.getFileUploadPath();
			String dir=pictureName.split(" ")[0];
			File filepath=new File(fileSavePath+"/"+dir);
			if(!filepath.isDirectory()){
				filepath.mkdir();
			}
			pictureName = pictureName + file.getOriginalFilename();
			fileSavePath = fileSavePath + pictureName.replaceAll(" " , "/");
			file.transferTo(new File(fileSavePath));
			JSONObject savefile=new JSONObject();
			savefile.put("name", file.getOriginalFilename());
			savefile.put("size", file.getSize());
			savefile.put("path", fileSavePath.replace(viciProperties.getFileUploadPath(), ""));
			savefile.put("exts",  file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
			
			savefile=superService.add(savefile, "file", null);
			JSONStr.put("id", savefile.get("id"));
			JSONStr.put("path",  savefile.get("path"));
		} catch (Exception e) {
			throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
			
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code", 200);
		jsonObject.put("message", JSONStr);
		
		return jsonObject;
	}
	
	/**
	 * 文件下载
	 */
	@RequestMapping(value = "/filedownload/{id}")
	public void filedownload(@PathVariable int id, HttpServletResponse resp) {
		JSONObject savefile=superService.selectById(id, "file", null);
		String filename =savefile.getString("path");
		String filenameiso = "";
		try {
			filenameiso = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		resp.setHeader("content-type", "application/octet-stream");
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=" + filenameiso);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = resp.getOutputStream();
			String fileSavePath = viciProperties.getFileUploadPath();
			bis = new BufferedInputStream(new FileInputStream(new File(fileSavePath + filename)));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setCellComment(String fileSavePath, List<JSONObject> errorCellList) {
		FileInputStream in = null;
		try {

			File file = new File(fileSavePath);
			in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);// 拿到文件转化为javapoi可操纵类型
			in.close();
			CellStyle rightCellStyle = workbook.createCellStyle();
			Font rightfont = workbook.createFont();
			rightfont.setColor(Font.COLOR_NORMAL);
			rightCellStyle.setFont(rightfont);
			HSSFSheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {// 删除批注
				for (Cell cell : row) {
					Comment comment = cell.getCellComment();
					if (comment != null) {
						cell.removeCellComment();
						cell.setCellStyle(rightCellStyle);
					}
				}
			}
			CellStyle errorCellStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(Font.COLOR_RED);
			errorCellStyle.setFont(font);
			// 创建绘图对象
			HSSFPatriarch p = sheet.createDrawingPatriarch();
			// 创建单元格对象,批注插入到4行,1列,B5单元格
			if(null!=errorCellList&&errorCellList.size()>0){
				for (JSONObject jsonObject : errorCellList) {
					HSSFRow row = sheet.getRow(jsonObject.getIntValue("row"));// 得到行
					HSSFCell cell = row.getCell(jsonObject.getIntValue("col"));// 得到列
					if (null == cell) {
						cell = row.createCell(jsonObject.getIntValue("col"));
					}
					// 获取批注对象
					// (int dx1, int dy1, int dx2, int dy2, short col1, int row1,
					// short col2, int row2)
					// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
					HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
					// 输入批注信息
					comment.setString(new HSSFRichTextString(jsonObject.getString("msg")));
					// 添加作者,选中B5单元格,看状态栏
					// comment.setAuthor("toad");
					// 将批注添加到单元格对象中
					cell.setCellComment(comment);
					cell.setCellStyle(errorCellStyle);
				}
			}
			
			FileOutputStream excelFileOutPutStream = new FileOutputStream(fileSavePath);// 写数据到这个路径上
			workbook.write(excelFileOutPutStream);
			excelFileOutPutStream.flush();
			excelFileOutPutStream.close();

		} catch (ExcelImportException e) {
			throw new ExcelImportException(e.getType(), e);
		} catch (Exception e) {
			throw new ExcelImportException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(in);
		}

	}

	private JSONObject checkData(List<Map<String, Object>> datalist, List<JSONObject> fieldlist) {
		JSONObject returnJsonObject = new JSONObject();
		returnJsonObject.put("pass", true);
		if (datalist.size() == 0) {
			returnJsonObject.put("pass", false);
			returnJsonObject.put("msg", "导入的文件没有数据");
			return returnJsonObject;
		}
		List<JSONObject> impFieldlist = new ArrayList<>();
		for (JSONObject field : fieldlist) {
			if (1 == field.getIntValue("imp")) {
				impFieldlist.add(field);
			}
		}
		if (impFieldlist.size() == 0) {
			returnJsonObject.put("pass", false);
			returnJsonObject.put("msg", "模版错误，没有找到可以导入的属性，请通知管理员");
			return returnJsonObject;
		}
		Map<String, Object> data1 = datalist.get(0);
		if (impFieldlist.size() != data1.size()) {
			returnJsonObject.put("pass", false);
			returnJsonObject.put("msg", "模版不匹配，请重新下载模版");
			return returnJsonObject;
		}
		int keyindex = 0;
		for (String key : data1.keySet()) {
			JSONObject jsonObject = impFieldlist.get(keyindex);
			String title = jsonObject.getString("title");
			if (!key.equals(title)) {
				returnJsonObject.put("pass", false);
				returnJsonObject.put("msg", "模版不匹配，请重新下载模版");
				return returnJsonObject;
			}
			keyindex++;
		}
		List<JSONObject> errorCellList = new ArrayList<JSONObject>();// 错误的cell
		List<JSONObject> dataValueList = new ArrayList<JSONObject>();// 返回的excel数据
		for (int row = 0; row < datalist.size(); row++) {
			JSONObject dataValue = new JSONObject();
			Map<String, Object> data = datalist.get(row);
			for (int Col = 0; Col < impFieldlist.size(); Col++) {

				JSONObject fieldObject = impFieldlist.get(Col);
				String title = fieldObject.getString("title");
				Object value = data.get(title);
				String dict = fieldObject.getString("dict");
				if (!StringUtils.isEmpty(dict)) {
					List<JSONObject> list = superService.getDictList(dict);
					for (JSONObject jsonObject : list) {
						if (value.toString().equals(jsonObject.get("name"))) {
							value = jsonObject.get("num");
							break;
						}
					}
				}
				String fieldtype=fieldObject.getString("fieldtype");
				
				if("date".equals(fieldtype)||"datetime".equals(fieldtype)||"time".equals(fieldtype)){
					if(null!=value&&StringUtils.isNotBlank(value.toString())){
						SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

						//java.util.Date对象
						Date date;
						try {
							date = (Date) sdf.parse(value.toString());
							if("date".equals(fieldtype)){
								value= new SimpleDateFormat("yyyy-MM-dd").format(date);
							}else if("datetime".equals(fieldtype)){
								value=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
							}else if("time".equals(fieldtype)){
								value=new SimpleDateFormat("HH:mm:ss").format(date);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					
					}
					
				}
				String checkResult = ValidatorUtil.validate(value, fieldObject.getString("verifyrole"));
				;
				if (!checkResult.equals("")) {
					returnJsonObject.put("pass", false);
					JSONObject errorCellObject = new JSONObject();
					errorCellObject.put("row", row + 2);
					errorCellObject.put("col", Col);
					errorCellObject.put("msg", checkResult);
					errorCellList.add(errorCellObject);
				} else {
					dataValue.put(fieldObject.getString("field"), value);
				}
			}
			dataValueList.add(dataValue);
		}
		returnJsonObject.put("errorCellList", errorCellList);
		returnJsonObject.put("dataValueList", dataValueList);
		if (!returnJsonObject.getBooleanValue("pass")) {
			returnJsonObject.put("msg", "数据有误，请更正有误的数据");
		}
		return returnJsonObject;
	}

	/**
	 * 通用保存
	 */
	@RequestMapping(value = "/{tablename}/save")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Object save(@PathVariable String tablename) {
		int returnid = 0;
		List<JSONObject> fieldlist = superService.getFieldList(TABLE_PREFIX +tablename);
		JSONObject saveObj = initSaveObject(fieldlist);

		List<String> fields = (List<String>) saveObj.get("fields_list");
		List<String> tablenames = (List<String>) saveObj.get("tablename");
		saveObj.remove("fields_list");
		saveObj.remove("tablename");
		// Map maps = (Map)JSON.parse(saveObj.toJSONString());
		Map mapTypes = JSON.parseObject(saveObj.toJSONString());
		if (tablename.endsWith("view")) {

			JSONObject saveObjview = new JSONObject();
			JSONObject ViewRelation = superService.getViewRelation(TABLE_PREFIX + tablename);
			String updatestr = ViewRelation.getString("addafter");
			String mastertable = ViewRelation.getString("mastertable");
			// if (StringUtils.isEmpty(id)) {
			for (String tablenametemp : tablenames) {
				saveObjview.clear();
				for (Object tempobj : mapTypes.keySet()) {
					if (tempobj.toString().startsWith(tablenametemp + '.')) {
						saveObjview.put(tempobj.toString().replace(tablenametemp + '.', ""),
								mapTypes.get(tempobj).toString());
					}
				}
				String id = saveObjview.getString("id");
				if (StringUtils.isEmpty(id)) {
					saveObjview = superService.add(saveObjview, tablenametemp, null);
				} else {
					superService.updateById(saveObjview, tablenametemp, null);
				}
				if (tablenametemp.equals(mastertable)){
					returnid = saveObjview.getIntValue("id");
				}
				mapTypes.put(tablenametemp + ".id", saveObjview.getIntValue("id"));
			}
			if (!StringUtils.isEmpty(updatestr.trim())) {// 执行后续语句
				for (Object tempobj : mapTypes.keySet()) {
					updatestr = updatestr.replace("#{" + tempobj.toString() + "}", mapTypes.get(tempobj).toString());
				}
				superDao.update(updatestr);
			}
			// } else {
			//
			// superService.updateViewById(saveObj, TABLE_PREFIX + tablename,
			// fields);
			//
			// // superService.updateById(saveObj, TABLE_PREFIX+tablename,
			// // fields);
			// }

		} else {
			String id = saveObj.getString("id");
			if (StringUtils.isEmpty(id)) {
				saveObj = superService.add(saveObj, TABLE_PREFIX + tablename, null);

			} else {

				superService.updateById(saveObj, TABLE_PREFIX + tablename, fields);
			}
			returnid = saveObj.getIntValue("id");
		}

		SuccessTip successTip = super.SUCCESS_TIP;
		successTip.setId(returnid);
		return super.SUCCESS_TIP;
	}

	/**
	 * 新增通用查询
	 */
	/*
	 * @RequestMapping(value = "/add")
	 * 
	 * @ResponseBody public Object add(String insertjosn) {
	 * generaltableService.insertOne(insertjosn); return super.SUCCESS_TIP; }
	 */
	/**
	 * 通用删除
	 */
	@RequestMapping(value = "/{tablename}/delete")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Object delete(@PathVariable String tablename) {
		return innerdelete(tablename, false);
	}
	
	private Object innerdelete(String tablename,boolean virtual){
		String id = getRequestParameter("id");
		List<Map<String, Object>> isdelcascadelist;
		if (!StringUtils.isEmpty(id)) {
			int idint = Integer.parseInt(id);
			superService.delete(idint,TABLE_PREFIX + tablename,virtual);
			/*if (tablename.endsWith("view")) {
				superService.deleteViewById(idint, TABLE_PREFIX + tablename,virtual);
			} else {
				superService.deleteById(idint, TABLE_PREFIX + tablename,virtual);
				isdelcascadelist = metadataMapper.getTablenameIsdelcascade(TABLE_PREFIX + tablename);
				if (ToolUtil.isNotEmpty(isdelcascadelist)
						&& "1".equals(isdelcascadelist.get(0).get("isdelcascade").toString())) {
					System.out.println(isdelcascadelist.get(0).get("isdelcascade").toString());
					superService.deleteTableByCascade(TABLE_PREFIX + tablename, idint,virtual);
				}
			}*/
			return SUCCESS_TIP;
		} else {
			return new ErrorTip(500, "删除失败,id不能为空");
		}
	}
	/**
	 * 虚拟删除
	 * 表中需要存在status字段
	 */
	@RequestMapping(value = "/{tablename}/vdelete")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Object virtualDelete(@PathVariable String tablename) {
		return innerdelete(tablename, true);

	}

	private Workbook excelExport(String title, String subTitle, List<JSONObject> fieldlist, List<JSONObject> list,
			String impOrExp) {
		int colSize = 0;
		List<String> exportFieldsList = new ArrayList<>();

		Map<Integer, String[]> dicMap = new HashMap<>();
		List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
		for (JSONObject jsonObject : fieldlist) {
			int export = jsonObject.getIntValue(impOrExp);
			if (export == 1) {
				String field = jsonObject.getString("field");
				exportFieldsList.add(field);
				String fieldTitle = jsonObject.getString("title");
				String dict = jsonObject.getString("dict");
				ExcelExportEntity entity = new ExcelExportEntity(fieldTitle, field);
				if (!StringUtils.isEmpty(dict)) {
					List<JSONObject> dic = superService.getDictList(dict);
					String[] dicArray = new String[dic.size() - 1];
					String[] dicRArray = new String[dic.size() - 1];
					for (int i = 1; i < dic.size(); i++) {
						dicArray[i - 1] = dic.get(i).getString("name");
						dicRArray[i - 1] = dic.get(i).getString("name") + "_" + dic.get(i).getString("num");
					}
					entity.setReplace(dicRArray);
					entity.setDict(dict);
					dicMap.put(colSize, dicArray);
				}

				entityList.add(entity);
				colSize++;
			}
		}

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		for (JSONObject jsonObject : list) {
			map = new HashMap<String, String>();
			for (String field : exportFieldsList) {
				map.put(field, null == jsonObject.getString(field) ? "" : jsonObject.getString(field));
			}
			dataList.add(map);
		}

		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, subTitle), entityList, dataList);

		for (int key : dicMap.keySet()) {
			workbook = setHSSFValidation(workbook, dicMap.get(key), 2, 60000, key, key);
		}
		// workbook= setAutoSizeColumn( workbook, colSize);
		return workbook;
	}

	private Workbook setAutoSizeColumn(Workbook workbook, int colSize) {

		Sheet sheet = workbook.getSheetAt(0);
		for (int i = 0; i < colSize; i++) {
			sheet.autoSizeColumn((short) i);
		}
		return workbook;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * 
	 * @param sheet
	 *            要设置的sheet.
	 * @param textlist
	 *            下拉框显示的内容
	 * @param firstRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param endCol
	 *            结束列
	 * @return 设置好的sheet.
	 */
	private Workbook setHSSFValidation(Workbook workbook, String[] textlist, int firstRow, int endRow, int firstCol,
			int endCol) {
		Sheet sheet = workbook.getSheetAt(0);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 加载下拉列表内容
		DVConstraint constraint =null;
		if(textlist.length>20){//如果生成下拉列表数据>20创建隐藏sheet
			Sheet hiddenSheet=workbook.getSheet("hidden");
			if(null==hiddenSheet){
				hiddenSheet = workbook.createSheet("hidden");
				workbook.setSheetHidden(1, true); 
			}
			 HSSFCell cell = null;
		        for (int i = 0, length= textlist.length; i < length; i++) { 
		           String name = textlist[i]; 
		           HSSFRow row = (HSSFRow) hiddenSheet.createRow(i); 
		           cell = row.createCell(firstCol); 
		           cell.setCellValue(name); 
		         } 
		        Name namedCell = workbook.createName(); 
		        namedCell.setNameName("hidden"+firstCol); 
		        String firstColStr=getColumnNum(firstCol);
		        String str="hidden!"+firstColStr+"1:"+firstColStr+ textlist.length;
		        //str="hidden!C1:C108";
		        namedCell.setRefersToFormula(str); 
		        //加载数据,将名称为hidden的
		        constraint = DVConstraint.createFormulaListConstraint("hidden"+firstCol); 
		}else{
			// 加载下拉列表内容
		  constraint = DVConstraint.createExplicitListConstraint(textlist);
		}
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return workbook;
		
	}
	private  String getColumnNum(int i) {  
	    String strResult = "";  
	    int intRound = i / 26;  
	    int intMod = i % 26;  
	    if (intRound != 0) {  
	        strResult = String.valueOf(((char) (intRound + 64)));  
	    }  
	    strResult += String.valueOf(((char) (intMod + 64)));  
	    return strResult;  
	}  
	/**
	 * 通用保存
	 */
	@RequestMapping(value = "/getdata/{datacode}")
	@ResponseBody
	public Object getData(@PathVariable String datacode) {
		return superService.getdata(datacode,null);
	}  

}
