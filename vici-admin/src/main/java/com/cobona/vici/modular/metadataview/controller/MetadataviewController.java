package com.cobona.vici.modular.metadataview.controller;

import com.cobona.vici.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import com.cobona.vici.core.log.LogObjectHolder;
import com.cobona.vici.core.node.ZTreeNode;
import com.cobona.vici.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.MetadataviewMapper;
import com.cobona.vici.common.persistence.dao.SunServicecategoryMapper;
import com.cobona.vici.common.persistence.model.Metadata;
import com.cobona.vici.common.persistence.model.Metadataview;
import com.cobona.vici.common.persistence.model.SunProduct;
import com.cobona.vici.modular.metadata.service.IMetadataService;
import com.cobona.vici.modular.metadataview.service.IMetadataviewService;
import com.cobona.vici.modular.system.service.SuperService;

/**
 * 视图管理控制器
 *
 * @author jinchm
 * @Date 2018-03-13 23:18:51
 */
@Controller
@RequestMapping("/metadataview")
public class MetadataviewController extends BaseController {

    private String PREFIX = "/metadataview/metadataview/";

    @Autowired
    private IMetadataviewService metadataviewService;
    @Autowired
    private IMetadataService metadataService;
    @Autowired
 	private SuperService superService;
    
    @Resource
    MetadataviewMapper metadataviewMapper;

    /**
     * 跳转到视图管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "metadataview.html";
    }

    /**metadataview_add_list
     * 跳转到添加视图管理
     */
    @RequestMapping("/metadataview_add")
    public String metadataviewAdd() {
        return PREFIX + "metadataview_add.html";
    }
    @RequestMapping("/metadataview_add_list")
    public String metadataviewAddList() {
        return PREFIX + "metadataview_add_list.html";
    }

    /**
     * 跳转到修改视图管理
     */
    @RequestMapping("/metadataview_update/{metadataviewId}")
    public String metadataviewUpdate(@PathVariable Integer metadataviewId, Model model) {
        Metadataview metadataview = metadataviewService.selectById(metadataviewId);
        model.addAttribute("item",metadataview);
        Metadata metadata = metadataService.selectById(metadataview.getMetadataid());
        model.addAttribute("itemtable",metadata);
        LogObjectHolder.me().set(metadataview);
        return PREFIX + "metadataview_edit.html";
    }
    @RequestMapping("/metadataview_update_list/{metadataviewId}")
    public String metadataviewUpdateList(@PathVariable Integer metadataviewId, Model model) {
        Metadataview metadataview = metadataviewService.selectById(metadataviewId);
        model.addAttribute("item",metadataview);
        Metadata metadata = metadataService.selectById(metadataview.getMetadataid());
        model.addAttribute("itemtable",metadata);
        LogObjectHolder.me().set(metadataview);
        return PREFIX + "metadataview_edit_list.html";
    }
    /**
     * 获取视图管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String viewname,String tablename,String field) {
     	String condtion=null;
     			if(ToolUtil.isNotEmpty(viewname)) {
     				condtion = "and viewname = '"+viewname+"'";
     			} 
     			if(ToolUtil.isNotEmpty(tablename)) {
     				if(ToolUtil.isNotEmpty(condtion)) {
     				condtion = condtion+ "and tablename like '%"+tablename+"%'";
     				}else {
     					condtion = "and tablename like '%"+tablename+"%'";
     				}
     			} 
     			if(ToolUtil.isNotEmpty(field)) {
     				if(ToolUtil.isNotEmpty(condtion)) {
     					condtion =condtion+ "and field like '%"+field+"%'";
     				}else {
     					condtion = "and field like '%"+field+"%'";
     				}
     			} 
        return metadataviewService.selectViewList(condtion);
    }
    
    @RequestMapping(value = "/list/init/{viewname}")
    @ResponseBody
    public Object listInit(@PathVariable String viewname) {
     	String condtion=null;
     	List<Map<String, Object>> resultlist =null;
     			if(ToolUtil.isNotEmpty(viewname)) {
     				condtion = "and viewname = '"+viewname+"'";
     				resultlist=metadataviewService.selectViewList(condtion);
     			} 
     	    	for(int i=0;i<resultlist.size();i++) {
     	    		if(resultlist.get(i).get("defaultvalue")==null) {
     	    			resultlist.get(i).put("defaultvalue","");
     	    		}
     	    		if(resultlist.get(i).get("minwidth")==null) {
     	    			resultlist.get(i).put("minwidth","80");
     	    		}
     	    		
     	    	}
        return resultlist;
    }

    /**add_list
     * 新增视图管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object add(Metadataview metadataview) {
    	try {
            metadataviewService.insert(metadataview);
        } catch (Exception e) {
        	String errorMessage="字段 <span style='color:red;'>";
        	
        	String metadataid = metadataview.getMetadataid();
    		JSONObject paramObject=new JSONObject();
    		paramObject.put("metadataid", metadataid);
        	JSONObject field =(JSONObject) superService.doEventhing(115, paramObject);
        	if(field!=null&&!field.equals("")) {
        		errorMessage+=field.getString("field");
        	}
        	
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
    @RequestMapping(value = "/add_list")//功能已被update_list完成，暂不使用
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object addList(@RequestParam String allTableDatastr) {
    	allTableDatastr=superService.changecode(allTableDatastr);
    	 List<Metadataview> metadatavewList = JSON.parseArray(allTableDatastr, Metadataview.class);  
    	 for(int i = 0 ; i < metadatavewList.size() ; i++) {
    		  this.add(metadatavewList.get(i));
    		}
        
        return super.SUCCESS_TIP;
    }

    /**
     * 删除视图管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object delete(@RequestParam Integer metadataviewId) {
        metadataviewService.deleteById(metadataviewId);
        return SUCCESS_TIP;
    }

    /**
     * 修改视图管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object update(Metadataview metadataview) {
    	try {
    		if(metadataview.getDefaultvalue()==null) {
    			metadataview.setDefaultvalue("");
    		}
    		metadataviewService.updateById(metadataview);
        } catch (Exception e) {
        	String errorMessage="字段 <span style='color:red;'>";
        	 String metadataid = metadataview.getMetadataid();
    		JSONObject paramObject=new JSONObject();
    		paramObject.put("metadataid", metadataid);
        	JSONObject field =(JSONObject) superService.doEventhing(115, paramObject);
        	if(field!=null&&!field.equals("")) {
        		errorMessage+=field.getString("field");
        	}
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
    /**
     * 修改视图管理
     */
    @RequestMapping(value = "/update_list")
    @ResponseBody
    @Transactional(propagation =Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public Object updateList(@RequestParam String allTableDatastr) {
    	allTableDatastr=superService.changecode(allTableDatastr);
    	List<Metadataview> metadatavewList;
    	try {//如果有不符合pojo格式的报错
    		metadatavewList = JSON.parseArray(allTableDatastr, Metadataview.class);
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
    	
    	List<Metadata> metadataViewResults=(List<Metadata>) listInit(metadatavewList.get(0).getViewname());
    	
    	 for(int i = 0 ; i < metadatavewList.size() ; i++) {
    		 
    		 if(i<metadataViewResults.size()) {//保存时，保存成功的字段获取id。并走update分支
    			 String jsonString = JSONObject.toJSONString(metadataViewResults.get(i));

    			 Integer id = JSONObject.parseObject(jsonString).getInteger("id");

    			 metadatavewList.get(i).setId(id);
    		 }
    		 
    		 if(ToolUtil.isEmpty(metadatavewList.get(i).getId())) {
    			 this.add(metadatavewList.get(i));
    		 }else {
    		  this.update(metadatavewList.get(i));}
    		}
        return super.SUCCESS_TIP;
    }

    /**
     * 视图管理详情
     */
    @RequestMapping(value = "/detail/{metadataviewId}")
    @ResponseBody
    public Object detail(@PathVariable("metadataviewId") Integer metadataviewId) {
        return metadataviewService.selectById(metadataviewId);
    }
    
    /**
     * 根据tableid tablename的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/tablenameBytableid/{tableid}")
    @ResponseBody
    public Object getTablenameBytableid(@PathVariable("tableid") String tableid) {
    

    	    	//List<Map<String, Object>> resultlist =null;
//    	      if (ToolUtil.isEmpty(tableid)) {
//    	    	  resultlist=metadataviewMapper.getTablenameByid("");
//    	      }else{
//    	    	  tableid= " and   id = '"+tableid+"'";
//    	    	  resultlist= metadataviewMapper.getTablenameByid(tableid);
//    	      }
    	List<JSONObject> resultlist = null;
    	      String sqlQuery="";
  			if(ToolUtil.isEmpty(tableid)) {
  				sqlQuery=" SELECT max(id) num, tablename   name FROM metadata a where 1=1   GROUP BY a.tablename";
  			}else {
  				sqlQuery=" SELECT max(id) num, tablename   name FROM metadata a where 1=1  and   id = '"+tableid+"' GROUP BY a.tablename";
  			}
  			
  			resultlist = superService.selectSql(sqlQuery);
    		return resultlist;
    	
    };
    /**
     * 根据tablename tablenamecn的值
     */
    @RequestMapping(value = "/tablenamecnBytablename/{tablename}")
    @ResponseBody
    public Object getTablenamecnBytablename(@PathVariable("tablename") String tablename) {
    

//    	    	List<Map<String, Object>> resultlist =null;
//    	      if (ToolUtil.isEmpty(tablename)) {
//    	    	  resultlist=metadataviewMapper.getTablenamecnBytablename("");
//    	      }else{
//    	    	  tablename= " and tablename = '"+tablename+"'";
//    	    	  resultlist= metadataviewMapper.getTablenamecnBytablename(tablename);
//    	      }
    	  	List<JSONObject> resultlist = null;
  	      String sqlQuery="";
			if(ToolUtil.isEmpty(tablename)) {
				sqlQuery="  SELECT max(id) num, tablenamecn   name FROM metadata a where 1=1  GROUP BY a.tablename";
			}else {
				sqlQuery="  SELECT max(id) num, tablenamecn   name FROM metadata a where 1=1  and tablename = '"+tablename+"' GROUP BY a.tablename";
			}
			resultlist = superService.selectSql(sqlQuery);
    		return resultlist;
    	
    };
    /**
     * 根据tablename field的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/fieldByTablename/{tablename}")
    @ResponseBody
    public Object getFieldByTablenameid(@PathVariable("tablename") String tablename) {
    

//    	    	List<Map<String, Object>> resultlist =null;
//    	      if (ToolUtil.isEmpty(tablename)) {
//    	    	  resultlist=metadataviewMapper.getFieldByTablename("");
//    	      }else{
//    	    	  tablename= " and tablename = '"+tablename+"'";
//    	    	  resultlist= metadataviewMapper.getFieldByTablename(tablename);
//    	      }
      	  List<JSONObject> resultlist = null;
	      String sqlQuery="";
			if(ToolUtil.isEmpty(tablename)) {
				sqlQuery="   SELECT id num, field   name FROM metadata a where 1=1 ";
			}else {
				sqlQuery="   SELECT id num, field   name FROM metadata a where 1=1 and tablename = '"+tablename+"'";
			}
			resultlist = superService.selectSql(sqlQuery);
    		return resultlist;
    	
    };
    /**
     * 根据id title的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/titleByid/{id}")
    @ResponseBody
    public Object getTitleByid(@PathVariable("id") String tableid) {
    

//    	   List<Map<String, Object>> resultlist =null;
//    	      if (ToolUtil.isEmpty(tableid)) {
//    	    	  resultlist=metadataviewMapper.getTitleByid("");
//    	      }else{
//    	    	  tableid= " and id = "+tableid;
//    	    	  resultlist= metadataviewMapper.getTitleByid(tableid);
//    	      }
    	  List<JSONObject> resultlist = null;
	      String sqlQuery="";
			if(ToolUtil.isEmpty(tableid)) {
				sqlQuery="    SELECT id num, title   name FROM  metadata a where 1=1 ";
			}else {
				sqlQuery="    SELECT id num, title   name FROM  metadata a where 1=1  and id = '"+tableid+"'";
			}
			resultlist = superService.selectSql(sqlQuery);
    		return resultlist;
    	
    };
    /**
     * 根据id viewname的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/viewnameByid/{id}")
    @ResponseBody
    public Object getViewnameByid(@PathVariable("id") String id) {
    

//    	    	List<Map<String, Object>> resultlist =null;
//    	      if (ToolUtil.isEmpty(id)) {
//    	    	  resultlist=metadataviewMapper.getViewnameByid("");
//    	      }else{
//    	    	  id= "id = "+id;
//    	    	  resultlist= metadataviewMapper.getViewnameByid(id);
//    	      }
    	      List<JSONObject> resultlist = null;
    	      String sqlQuery="";
    			if(ToolUtil.isEmpty(id)) {
    				sqlQuery=" SELECT 	max(id) num, viewname   name 	FROM metadataview a where 1=1  GROUP BY a.viewname ";
    			}else {
    				sqlQuery="  SELECT 	max(id) num, viewname   name 	FROM metadataview a where 1=1 and id ='"+id+"' GROUP BY a.viewname";
    			}
    			resultlist = superService.selectSql(sqlQuery);
    		return resultlist;
    	
    };
    /**
     * 根据id viewname的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/viewnamecnByViewname/{viewnameid}")
    @ResponseBody
    public Object getViewnamecnByViewname(@PathVariable("viewnameid") String viewnameid) {
    

//    	    	List<Map<String, Object>> resultlist =null;
//    	      if (ToolUtil.isEmpty(viewnameid)) {
//    	    	  resultlist=metadataviewMapper.getViewnamecnByViewname("");
//    	      }else{
//    	    	  viewnameid= " and id = '"+viewnameid+"'";
//    	    	  resultlist= metadataviewMapper.getViewnamecnByViewname(viewnameid);
//    	      }
    	      List<JSONObject> resultlist = null;
    	      String sqlQuery="";
    			if(ToolUtil.isEmpty(viewnameid)) {
    				sqlQuery="  SELECT id num, viewnamecn   name 	FROM 	metadataview a 	where 1=1  ";
    			}else {
    				sqlQuery=" SELECT id num, viewnamecn   name 	FROM 	metadataview a 	where 1=1 and id = '"+viewnameid+"'";
    			}
    			resultlist = superService.selectSql(sqlQuery);
    			
    		return resultlist;
    	
    };
    /**
     * 根据前台参数获取对应到列的值
     */
    @RequestMapping(value = "/getTableIdListByTablename/{tablename}")
    @ResponseBody
    public List<ZTreeNode> getTableIdListByTablename(@PathVariable("tablename")  String tablename ) {
    
//        if (ToolUtil.isEmpty(providerid)) {
    	  if (ToolUtil.isEmpty(tablename)) {
    		  tablename = "1=1";
    	  }else {
    		  tablename = "m1.tablename = '"+tablename+"'";
    	  }
            List<ZTreeNode> tableids = metadataviewMapper.getTableidsByTablename(tablename); 
            
         return tableids;
    }
}
