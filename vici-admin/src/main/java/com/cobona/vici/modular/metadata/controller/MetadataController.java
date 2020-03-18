package com.cobona.vici.modular.metadata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.MetadataMapper;
import com.cobona.vici.common.persistence.dao.MetadataviewMapper;
import com.cobona.vici.common.persistence.model.Metadata;
import com.cobona.vici.common.persistence.model.SunServicecategory;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.exception.ViciException;
import com.cobona.vici.core.log.LogObjectHolder;
import com.cobona.vici.core.util.ToolUtil;
import com.cobona.vici.modular.metadata.service.IMetadataService;
import com.cobona.vici.modular.metadata.service.impl.GeneraltableServiceImpl;
import com.cobona.vici.modular.system.service.SuperService;
import com.cobona.vici.modular.system.warpper.MetadataWarpper;
import com.cobona.vici.modular.system.warpper.SunProductWarpper;

/**
 * 元数据管理控制器
 *
 * @author cobona
 * @Date 2018-01-06 22:42:58
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController extends BaseController {

    private String PREFIX = "/metadata/metadata/";

    @Autowired
    private IMetadataService metadataService;
    @Autowired
    private GeneraltableServiceImpl generaltableService;
    @Autowired
	private SuperService superService;
    @Resource
    MetadataMapper metadataMapper;
    
    /**
     * 跳转到元数据管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "metadata.html";
    }

    /**
     * 跳转到添加元数据管理
     */
    @RequestMapping("/metadata_add")
    public String metadataAdd() {
        return PREFIX + "metadata_add.html";
    }
    /**
     * 跳转到添加元数据管理
     */
    @RequestMapping("/metadata_add_list")
    public String metadataAddList() {
        return PREFIX + "metadata_add_list.html";
    }

    /**
     * 跳转到修改元数据管理
     */
    @RequestMapping("/metadata_update/{metadataId}")
    public String metadataUpdate(@PathVariable Integer metadataId, Model model) {
        Metadata metadata = metadataService.selectById(metadataId);
        if(metadata.getIsmajorkey()==null) {
        	metadata.setIsmajorkey("0");
        }
        model.addAttribute("item",metadata);
        LogObjectHolder.me().set(metadata);
        return PREFIX + "metadata_edit.html";
    }
    /**
     * 跳转到修改元数据管理
     */
    @RequestMapping("/metadata_update_list/{metadataId}")
    public String metadataListUpdate(@PathVariable Integer metadataId, Model model) {
       Metadata metadata = metadataService.selectById(metadataId);
        model.addAttribute("item",metadata);
        //LogObjectHolder.me().set(metadata);
        return PREFIX + "metadata_edit_list.html";
    }

    /**
     * 获取元数据管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String tablename,String field) {
    	EntityWrapper<Metadata> metadataEntityWapper=new EntityWrapper();
     	Wrapper metadataCondtion= metadataEntityWapper.like("tablename", tablename );
     	List<Map<String, Object>> resultlist =null;
     	if(ToolUtil.isEmpty(tablename)) {
     		metadataCondtion=null;
     	}
     	if(ToolUtil.isNotEmpty(field)) {
     		metadataCondtion= metadataEntityWapper.like("field", field );
     	}
     	 resultlist=metadataService.selectMaps(metadataCondtion);
     	return new MetadataWarpper(resultlist).warp();
    	//metadataService.createTable(null);
    	//metadataService.newColumn(null);
    	//metadataService.dropTable(null);
    	
//    	 String str = "{\"sqltype\":\"query\","
//    	 		+ "\"tablename\":\"teststudent\","
//    	 		+ "\"columns\":\"id,stuname,stunumber\","
//    	 		+ "\"condition\":\" \","
//    	 		+ "\"order\":\"\","
//    	 		+ "\"groupby\":\"\","
//    	 		+ "\"limit\":\"\","
//    	 		+ "\"offset\":\"\"}"; 
    	
    	 //-- String str =readtxt("f:/testquery.txt");
      	  //JSONObject obj=JSON.parseObject(str);
      	 // String str2 = JSON.toJSONString(obj);
      	//List<Map<String, Object>> aaa;
//     	 try {
      //--	List<Map<String, Object>> resultlist= generaltableService.getOneTableList(str);
//            	 }catch(Exception e){
//            		final Throwable cause = e.getCause();
//            		return metadataService.selectList(null);
//         }
       	
       	 //Object json= JSON.toJSON(aaa);
      	//String json = JSON.toJSONString(aaa);
     	 //System.out.println(json);
      	//generaltableService.insertOne(str2);
      	//generaltableService.updateOne(str2);
      	//generaltableService.deleteOne(str2);
           //return generaltableService.selectList(null);
  
      //--	String json = JSON.toJSONString(resultlist, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
     
       //-- JSONArray jsonArray = JSONArray.parseArray(json);  
  
        //JSONArray  jjj= JSONArray.
//      System.out.println(json);
//        System.out.println("----------------");
//      System.out.println(jsonArray);
    	
    }
    @RequestMapping(value = "/list/init/{tablename}")
    @ResponseBody
    public Object listInt(@PathVariable String tablename) {
    	
    	EntityWrapper<Metadata> metadataEntityWapper=new EntityWrapper();
    	List<Map<String, Object>> resultlist =null;
    	if(ToolUtil.isNotEmpty(tablename)) {
    		Wrapper metadataCondtion=metadataEntityWapper.eq("tablename", tablename );
         	resultlist=metadataService.selectMaps(metadataCondtion);
     	}
		for (int i = 0; i < resultlist.size(); i++) {
			
			if (resultlist.get(i).get("defaultvalue") == null) {
				resultlist.get(i).put("defaultvalue", "");
			}
			if (resultlist.get(i).get("title") == null) {
				resultlist.get(i).put("title", "");
			}
			if (resultlist.get(i).get("isnullable") == null) {
				resultlist.get(i).put("isnullable", "0");
			}
			if (resultlist.get(i).get("field") == null) {
				resultlist.get(i).put("field", "");
			}
			if (resultlist.get(i).get("ismajorkey") == null) {
				resultlist.get(i).put("ismajorkey", "0");
			}
			if (resultlist.get(i).get("fieldtype") == null) {
				resultlist.get(i).put("fieldtype", "");
			}
			if (resultlist.get(i).get("fieldlength") == null) {
				resultlist.get(i).put("fieldlength",0);
			}
			if (resultlist.get(i).get("decimalpoint") == null) {
				resultlist.get(i).put("decimalpoint", "0");
			}

		}
    	
    	//resultlist.get(0).remove("templet");
    	//resultlist.get(0).remove("templetscript");  
     
        //resultlistone.add(resultone);
     	//return new MetadataWarpper(resultlistone).warp();
       return resultlist;
     	
    }

    /**
     * 新增元数据管理
     * 判断表名是否存在，如果存在，则补充上完整信息，不存在则调用新建表语句
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public Object add(Metadata metadata) {
		HashMap<String, String> addconditionMap = new HashMap();
		EntityWrapper<Metadata> metadataEntityWapper = new EntityWrapper();
		Wrapper metadataCondtion = metadataEntityWapper.eq("tablename", metadata.getTablename());
		List<Metadata> metadataList = metadataService.selectList(metadataCondtion);
		int swit = 0;

		try {
			if (ToolUtil.isEmpty(metadataList)) {
				addconditionMap = (HashMap<String, String>) metadataService.jointCondition(metadata, "createtable");
				metadataService.createTable(addconditionMap);
				swit = 1;
			} else {
				metadata.setTablenamecn(metadataList.get(0).getTablenamecn());
				addconditionMap = (HashMap<String, String>) metadataService.jointCondition(metadata, "newColumn");
				metadataService.newColumn(addconditionMap);
				swit = 2;
			}

			// String sql = "select EEE from user";
			// superService.selectSql(sql);
			metadataService.insert(metadata);

		} catch (Exception e) {
			if (swit == 1) {// 创建表成功，插入metadata失败
				addconditionMap.put("tablename", metadata.getTablename());
				metadataService.dropTable(addconditionMap);
			} else if (swit == 2) {// 增加字段成功，插入metadata失败
				addconditionMap = (HashMap<String, String>) metadataService.jointCondition(metadata, "deleteColumn");
				metadataService.deleteColumn(addconditionMap);
			}

			String errorMessage = "字段 <span style='color:red;'>";
			errorMessage += metadata.getField();
			errorMessage += "</span> 添加失败<br>";
			if(e.getCause()==null) {
				errorMessage += e.toString();
			}else {
				errorMessage += e.getCause().toString();
			}
			BizExceptionEnum.ADD_FIELD_ERROR.setMessage(errorMessage);
			throw new BussinessException(BizExceptionEnum.ADD_FIELD_ERROR);
		}

		return super.SUCCESS_TIP;
	}
    @RequestMapping(value = "/addList")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object addList(@RequestParam String allTableDatastr) {
    	allTableDatastr=superService.changecode(allTableDatastr);

    	List<Metadata> metadataList;
    	try {
    		 metadataList = JSON.parseArray(allTableDatastr, Metadata.class); 
		} catch (Exception e) {
			String errorMessage="保存失败<br>";
			
			if(e.getCause()==null) {
				errorMessage += e.toString();
			}else {
				errorMessage += e.getCause().toString();
			}
			BizExceptionEnum.ADD_FIELD_ERROR.setMessage(errorMessage);
			
			throw new BussinessException(BizExceptionEnum.ADD_FIELD_ERROR);
		}

    		
    	List<Metadata> metadataResults=(List<Metadata>) listInt(metadataList.get(0).getTablename());

    	for(int i = 0 ; i < metadataList.size() ; i++) {


    		 if(i<metadataResults.size()) {
    			 String jsonString = JSONObject.toJSONString(metadataResults.get(i));

    			 Integer id = JSONObject.parseObject(jsonString).getInteger("id");

    			 metadataList.get(i).setId(id);
    		 }
    		 if(ToolUtil.isEmpty(metadataList.get(i).getId())) {
    			 this.add(metadataList.get(i));
    		 }else {
    		  this.update(metadataList.get(i));}
    		}
        
        return metadataList;
    }

    /**
     * 删除元数据管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object delete(@RequestParam Integer metadataId) {
    	 HashMap<String,String> addconditionMap=new HashMap();
     	EntityWrapper<Metadata> metadataEntityWapper=new EntityWrapper();
     	EntityWrapper<Metadata> metadataEntityWappernew=new EntityWrapper();
     	Wrapper metadataCondtion= metadataEntityWapper.eq("id", metadataId);
        List<Metadata> metadataList=metadataService.selectList(metadataCondtion);
        addconditionMap=(HashMap<String, String>) metadataService.jointCondition(metadataList.get(0),"deleteColumn");
        //如果字段都被删除，则整个表也进行删除
        Wrapper metadataCondtionnew= metadataEntityWappernew.eq("tablename", metadataList.get(0).getTablename());
        metadataList=metadataService.selectList(metadataCondtionnew);
        if(metadataList.size()==1) {
        	metadataService.dropTable(addconditionMap); 
       }else {
    		 metadataService.deleteColumn(addconditionMap);
        }
        
        metadataService.deleteById(metadataId);
      
        return SUCCESS_TIP;
    }

    /**
     * 修改元数据管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object update(Metadata metadata) {
        HashMap<String,String> addconditionMap=new HashMap();
    	EntityWrapper<Metadata> metadataEntityWapper=new EntityWrapper();
     	Wrapper metadataCondtion= metadataEntityWapper.eq("id", metadata.getId());
        List<Metadata> metadataList=metadataService.selectList(metadataCondtion);
        
        if(metadataList.size()==0) {
			BizExceptionEnum.ADD_FIELD_ERROR.setMessage("保存失败，请检查数据库!");
			throw new BussinessException(BizExceptionEnum.ADD_FIELD_ERROR);
        }
        try {
        /*
        //表名变更
        if(!metadata.getTablename().equals(metadataList.get(0).getTablename())) {
        	addconditionMap.put("oldtablename", metadataList.get(0).getTablename());
        	addconditionMap.put("newtablename", metadata.getTablename());
        	metadataService.alterTable(addconditionMap);
        }
        //表注释变更
        if(!metadata.getTablenamecn().equals(metadataList.get(0).getTablenamecn())) {
        	addconditionMap.put("tablename", metadata.getTablename());
        	addconditionMap.put("newtablecomment", metadata.getTablenamecn());
        	metadataService.alterTableComment(addconditionMap);
         }
         */
        //字段注释变更--字段默认值变更不一起的话会覆盖掉一个     	
        if (!metadata.getTitle().equals(metadataList.get(0).getTitle())||!metadata.getDefaultvalue().equals(metadataList.get(0).getDefaultvalue())) {
        	addconditionMap.put("tablename", metadata.getTablename());
        	
        	String addcondtionTablebody = metadata.getField()+" "+metadata.getFieldtype();
        	if(metadata.getFieldtype().equals("numeric")||metadata.getFieldtype().equals("double")||metadata.getFieldtype().equals("decimal")) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+","+metadata.getDecimalpoint()+")";
        	}else if(!(metadata.getFieldtype().equals("datetime")||metadata.getFieldtype().equals("date")||metadata.getFieldtype().equals("text")||metadata.getFieldtype().equals("longtext"))) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+")";
        	}
        		addcondtionTablebody= addcondtionTablebody+" comment '"+metadata.getTitle()+"' ";
        		if(!metadata.getField().equals("id")&&!metadata.getDefaultvalue().trim().equals("")) {
        			//如果字段为id或者默认值为空则不生效否则会报错
        			addcondtionTablebody= addcondtionTablebody+" default '"+metadata.getDefaultvalue()+"' ";
        		}
        	addconditionMap.put("tableBody", addcondtionTablebody);
        	metadataService.alterColumnComment(addconditionMap);
        	
        	/*
        	if(!metadata.getDefaultvalue().equals(metadataList.get(0).getDefaultvalue())&&!metadata.getField().equals("id")) {//如果默认值发生改变且不为id
        		String sql="update "+ metadata.getTablename()+" set "+metadata.getField()+" = '"+metadata.getDefaultvalue()+"'";
        		//更改所有之前的默认值;
        		superService.update(sql);
        	}
        	*/
        }

        

        //字段名变更
        if(!metadata.getField().equals(metadataList.get(0).getField())) {
        	addconditionMap.put("tablename", metadata.getTablename());
        	String addcondtionTablebody = "CHANGE COLUMN "+metadataList.get(0).getField() +" "+metadata.getField()+" "+metadata.getFieldtype();
        	if(metadata.getFieldtype().equals("numeric")||metadata.getFieldtype().equals("double")||metadata.getFieldtype().equals("decimal")) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+","+metadata.getDecimalpoint()+")";
        	}else if(!(metadata.getFieldtype().equals("datetime")||metadata.getFieldtype().equals("date")||metadata.getFieldtype().equals("text")||metadata.getFieldtype().equals("longtext"))) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+")";
        	}
        	
        	addcondtionTablebody = addcondtionTablebody+" comment '"+metadata.getTitle()+"' ";
        	addconditionMap.put("tableBody", addcondtionTablebody);
        	metadataService.alterColumn(addconditionMap);
        }
        //是否为空变更
        if(!metadata.getIsnullable().equals(metadataList.get(0).getIsnullable())) {
        	addconditionMap.put("tablename", metadata.getTablename());
        	String addcondtionTablebody = "CHANGE COLUMN "+metadataList.get(0).getField() +" "+metadata.getField()+" "+metadata.getFieldtype();
        	if(metadata.getFieldtype().equals("numeric")||metadata.getFieldtype().equals("double")||metadata.getFieldtype().equals("decimal")) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+","+metadata.getDecimalpoint()+")";
        	}else if(!(metadata.getFieldtype().equals("datetime")||metadata.getFieldtype().equals("date")||metadata.getFieldtype().equals("text")||metadata.getFieldtype().equals("longtext"))) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+")";
        	}
        	if(metadata.getIsnullable().equals("1")) {
        		addcondtionTablebody=addcondtionTablebody+" not null ";
        	}else {
        		addcondtionTablebody=addcondtionTablebody+" null ";
        	};
        	addcondtionTablebody = addcondtionTablebody+" comment '"+metadata.getTitle()+"' ";
        	addconditionMap.put("tableBody", addcondtionTablebody);
        	metadataService.alterColumn(addconditionMap);
        }
        //是否值主键变更
        if(metadataList.get(0).getIsmajorkey()==null) {
        	metadataList.get(0).setIsmajorkey("0");
        }
        if(!metadata.getIsmajorkey().equals(metadataList.get(0).getIsmajorkey())) {
        	if(!metadata.getField().equals("id")) {
        		BizExceptionEnum.ADD_FIELD_ERROR.setMessage("保存失败！只有id能为主键");
    			throw new BussinessException(BizExceptionEnum.ADD_FIELD_ERROR);
        	}
        	 addconditionMap.put("tablename", metadata.getTablename());
    		 addconditionMap.put("majorkey", metadata.getField());
        	if(metadata.getIsmajorkey().equals("1")) {
        		//查询之前是否有主键字段。如果有先删除。
        		EntityWrapper<Metadata> metadataEntityWapperkey=new EntityWrapper();
        		Wrapper metadataCondtionkey= metadataEntityWapperkey.eq("ismajorkey","1");
        		metadataCondtionkey= metadataEntityWapperkey.eq("tablename",metadata.getTablename());
        		 List<Metadata> metadataListkey=metadataService.selectList(metadataCondtion);
        		if(ToolUtil.isEmpty(metadataListkey)) {
        			metadataService.dropMajorkey(addconditionMap);
        		}else {
        			metadataService.addMajorkey(addconditionMap);
        		}
        		
        	}else {
        		metadataService.dropMajorkey(addconditionMap);
        	}
        
        }
        //fieldtype fieldlength decimalpoint
        if((!metadata.getFieldtype().equals(metadataList.get(0).getFieldtype()))||(
        		metadata.getFieldlength()!=metadataList.get(0).getFieldlength())||
        		(!metadata.getDecimalpoint().equals(metadataList.get(0).getDecimalpoint()))
        		){
        	addconditionMap.put("tablename", metadata.getTablename());
        	String addcondtionTablebody = "MODIFY "+metadata.getField()+" "+metadata.getFieldtype();
        	if(metadata.getFieldtype().equals("numeric")||metadata.getFieldtype().equals("double")||metadata.getFieldtype().equals("decimal")) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+","+metadata.getDecimalpoint()+")";
        	}else if(!(metadata.getFieldtype().equals("datetime")||metadata.getFieldtype().equals("date")||metadata.getFieldtype().equals("text")||metadata.getFieldtype().equals("longtext"))) {
        		addcondtionTablebody=addcondtionTablebody+"("+metadata.getFieldlength()+")";
        	}
        	addcondtionTablebody = addcondtionTablebody+" comment '"+metadata.getTitle()+"' ";
        	addconditionMap.put("tableBody", addcondtionTablebody);
        	       metadataService.alterColumn(addconditionMap);
        }
        metadataService.updateById(metadata);
        
        } catch (Exception e) {
        	String errorMessage="字段 <span style='color:red;'>";
        	errorMessage += metadata.getField();
        	errorMessage += "</span> 修改失败<br>";
			if(e.getCause()==null) {
				errorMessage += e.toString();
			}else {
				errorMessage += e.getCause().toString();
			}
			BizExceptionEnum.ADD_FIELD_ERROR.setMessage(errorMessage);
			throw new BussinessException(BizExceptionEnum.ADD_FIELD_ERROR);
        }
        return super.SUCCESS_TIP;
    }
    
   
    @RequestMapping(value = "/updateList")//更新操作与添加操作一致现更新调用的也为addList方法
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object updateList(@RequestParam String allTableDatastr) {
    	 //JSONArray jsonArray = JSONArray.fromObject(allTableDatastr);
    	allTableDatastr=superService.changecode(allTableDatastr);
    	 List<Metadata> metadataList = JSON.parseArray(allTableDatastr, Metadata.class); 
    	 for(int i = 0 ; i < metadataList.size() ; i++) {
    		 if(ToolUtil.isEmpty(metadataList.get(i).getId())) {
    			 this.add(metadataList.get(i));
    		 }else {
    		  this.update(metadataList.get(i));}
    		}
        
        return super.SUCCESS_TIP;
    }



    /**
     * 元数据管理详情
     */
    @RequestMapping(value = "/detail/{metadataId}")
    @ResponseBody
    public Object detail(@PathVariable("metadataId") Integer metadataId) {
        return metadataService.selectById(metadataId);
    }
    /**
     * 根据id viewname的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/getfathernodeBytablename/{tablename}")
    @ResponseBody
    public Object getfathernodeBytablename(@PathVariable("tablename") String tablename) {
    
    	List<JSONObject> resultlist = null;
		String tablenameAll="";
		String sqlQuery="";
		if(ToolUtil.isEmpty(tablename)) {
			sqlQuery="select id num ,concat(tablename,concat('.',field)) name from metadata " + 
					"where field='id'";
		}else {
			sqlQuery="select id num,concat(tablename,concat('.',field))  name from metadata " + 
					"where field='id' and tablename = '"+tablename+"'";
		}
		
		resultlist = superService.selectSql(sqlQuery);
    		return resultlist;
    	
    };
    public String readtxt(String path) {
    	 //读取文件  
        BufferedReader br = null;  
        StringBuffer sb = null;  
        try {  
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"GBK")); //这里可以控制编码  
            sb = new StringBuffer();  
            String line = null;  
            while((line = br.readLine()) != null) {  
                sb.append(line);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                br.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }     
        }  
    	return sb.toString();
    }
    /**
     * 根据id viewname的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/tablenameByid/{id}")
    @ResponseBody
    public Object getTablenameByid(@PathVariable("id") String id) {
    

    	    	List<Map<String, Object>> resultlist =null;
    	      if (ToolUtil.isEmpty(id)) {
    	    	  resultlist=metadataMapper.getTablenameByid("");
    	      }else{
    	    	  id= "id = "+id;
    	    	  resultlist= metadataMapper.getTablenameByid(id);
    	      }
    		return resultlist;
    	
    };
}
