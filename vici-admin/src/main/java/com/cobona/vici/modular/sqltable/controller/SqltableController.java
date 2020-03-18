package com.cobona.vici.modular.sqltable.controller;

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
import com.cobona.vici.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cobona.vici.common.persistence.model.Metadata;
import com.cobona.vici.common.persistence.model.Sqltable;
import com.cobona.vici.modular.sqltable.service.ISqltableService;
import com.cobona.vici.modular.system.service.SuperService;
import com.cobona.vici.modular.system.warpper.MetadataWarpper;

/**
 * sqltable管理控制器
 *
 * @author cobona
 * @Date 2018-09-20 11:16:09
 */
@Controller
@RequestMapping("/sqltable")
public class SqltableController extends BaseController {

    private String PREFIX = "/sqltable/sqltable/";

    @Autowired
    private ISqltableService sqltableService;
    @Autowired
	private SuperService superService;
    /**
     * 跳转到sqltable管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sqltable.html";
    }

    /**
     * 跳转到添加sqltable管理
     */
    @RequestMapping("/sqltable_add")
    public String sqltableAdd() {
        return PREFIX + "sqltable_add.html";
    }

    /**
     * 跳转到修改sqltable管理
     */
    @RequestMapping("/sqltable_update/{sqltableId}")
    public String sqltableUpdate(@PathVariable Integer sqltableId, Model model) {
        Sqltable sqltable = sqltableService.selectById(sqltableId);
        model.addAttribute("item",sqltable);
        LogObjectHolder.me().set(sqltable);
        return PREFIX + "sqltable_edit.html";
    }

    /**
     * 获取sqltable管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String name,String id) {
     	
    	String condtion=null;
    	if(ToolUtil.isNotEmpty(id)) {
    		condtion="and id="+id;
    	}
    	
		if(ToolUtil.isNotEmpty(name)) {
 				if(ToolUtil.isNotEmpty(condtion)) {
 				condtion = condtion+ " and name like '%"+name+"%'";
 				}else {
 					condtion = "and name like '%"+name+"%'";
 				}
 			} 

     	
     	/*
			if(ToolUtil.isNotEmpty(viewname)) {
				condtion = "and viewname = '"+viewname+"'";
			} 
			*/
		//return sqltableService.selectList(null);
		return sqltableService.selectViewList(condtion);
    }

    /**
     * 新增sqltable管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Sqltable sqltable) {
    	sqltable.setSql(superService.changecode(sqltable.getSql()));
        sqltableService.insert(sqltable);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除sqltable管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer sqltableId) {
        sqltableService.deleteById(sqltableId);
        return SUCCESS_TIP;
    }

    /**
     * 修改sqltable管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Sqltable sqltable) {
    	sqltable.setSql(superService.changecode(sqltable.getSql()));
        sqltableService.updateById(sqltable);
        return super.SUCCESS_TIP;
    }

    /**
     * sqltable管理详情
     */
    @RequestMapping(value = "/detail/{sqltableId}")
    @ResponseBody
    public Object detail(@PathVariable("sqltableId") Integer sqltableId) {
        return sqltableService.selectById(sqltableId);
    }
}
