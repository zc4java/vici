package com.cobona.vici.modular.metadataviewrelation.controller;

import com.cobona.vici.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.cobona.vici.core.log.LogObjectHolder;
import com.cobona.vici.core.node.ZTreeNode;
import com.cobona.vici.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cobona.vici.common.persistence.dao.MetadataviewrelationMapper;
import com.cobona.vici.common.persistence.model.Metadata;
import com.cobona.vici.common.persistence.model.Metadataviewrelation;
import com.cobona.vici.modular.metadataviewrelation.service.IMetadataviewrelationService;
import com.cobona.vici.modular.system.service.SuperService;

/**
 * 视图关系管理控制器
 *
 * @author cobona
 * @Date 2018-03-13 23:21:45
 */
@Controller
@RequestMapping("/metadataviewrelation")
public class MetadataviewrelationController extends BaseController {

    private String PREFIX = "/metadataviewrelation/metadataviewrelation/";

    @Autowired
    private IMetadataviewrelationService metadataviewrelationService;
    @Autowired
    private  MetadataviewrelationMapper  metadataviewrelationMapper;
    @Autowired
  	private SuperService superService;
    /**
     * 跳转到视图关系管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "metadataviewrelation.html";
    }

    /**
     * 跳转到添加视图关系管理
     */
    @RequestMapping("/metadataviewrelation_add")
    public String metadataviewrelationAdd() {
        return PREFIX + "metadataviewrelation_add.html";
    }

    /**
     * 跳转到修改视图关系管理
     */
    @RequestMapping("/metadataviewrelation_update/{metadataviewrelationId}")
    public String metadataviewrelationUpdate(@PathVariable Integer metadataviewrelationId, Model model) {
        Metadataviewrelation metadataviewrelation = metadataviewrelationService.selectById(metadataviewrelationId);
        model.addAttribute("item",metadataviewrelation);
        LogObjectHolder.me().set(metadataviewrelation);
        return PREFIX + "metadataviewrelation_edit.html";
    }

    /**
     * 获取视图关系管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String viewname) {
    	EntityWrapper<Metadataviewrelation> metadataviewrelationEntityWapper=new EntityWrapper();
     	Wrapper metadataviewrelationCondtion= metadataviewrelationEntityWapper.like("viewname", viewname );
    
     	if(ToolUtil.isEmpty(viewname)) {
     		metadataviewrelationCondtion=null;
     	}
     	
        return metadataviewrelationService.selectList(metadataviewrelationCondtion);
    }

    /**
     * 新增视图关系管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Metadataviewrelation metadataviewrelation) {
        metadataviewrelationService.insert(metadataviewrelation);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除视图关系管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer metadataviewrelationId) {
        metadataviewrelationService.deleteById(metadataviewrelationId);
        return SUCCESS_TIP;
    }

    /**
     * 修改视图关系管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Metadataviewrelation metadataviewrelation) {
        metadataviewrelationService.updateById(metadataviewrelation);
        return super.SUCCESS_TIP;
    }

    /**
     * 视图关系管理详情
     */
    @RequestMapping(value = "/detail/{metadataviewrelationId}")
    @ResponseBody
    public Object detail(@PathVariable("metadataviewrelationId") Integer metadataviewrelationId) {
        return metadataviewrelationService.selectById(metadataviewrelationId);
    }
    /**
     * 根据viewname 获取table的值
     *    public List<Map<String, Object>> getTitleByid(@Param("id")String id);
     */
    @RequestMapping(value = "/tablenameByViewname/{viewname}")
    @ResponseBody
    public Object getTablenameByViewname(@PathVariable("viewname") String viewname) {

    List<JSONObject> resultlist = null;
    String sqlQuery="";
		if(ToolUtil.isEmpty(viewname)) {
			sqlQuery="   SELECT  a.tablename num, a.tablename   name FROM metadata a,metadataview b where 1=1 and a.id = b.metadataid GROUP BY a.tablename  ";
		}else {
			sqlQuery="  SELECT  a.tablename num, a.tablename   name FROM metadata a,metadataview b where 1=1 and a.id = b.metadataid and viewname = '"+viewname+"' GROUP BY a.tablename";
		}
		resultlist = superService.selectSql(sqlQuery);
    	    	return resultlist;
    	
    };
    
    /**
     * 根据前台参数获取对应到列的值
     */
    @RequestMapping(value = "/getTablenameTreeByViewname/{viewname}")
    @ResponseBody
    public List<ZTreeNode> getTablenameTreeByViewname(@PathVariable("viewname")  String viewname ) {
    
//        if (ToolUtil.isEmpty(providerid)) {
    	  if (ToolUtil.isEmpty(viewname)) {
    		  viewname = "1=1";
    	  }else {
    		  viewname = "viewname = '"+viewname+"'";
    	  }
            List<ZTreeNode> tableids = metadataviewrelationMapper.getTablenameTreeByViewname(viewname); 
         return tableids;
    }


}
