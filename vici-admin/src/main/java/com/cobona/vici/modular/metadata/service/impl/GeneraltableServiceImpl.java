package com.cobona.vici.modular.metadata.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cobona.vici.common.persistence.dao.GeneraltableMapper;
import com.cobona.vici.common.persistence.model.Generaltable;
import com.cobona.vici.modular.metadata.service.IGeneraltableService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinchm
 * @since 2018-01-07
 */
@Service
public class GeneraltableServiceImpl extends ServiceImpl<GeneraltableMapper, Generaltable> implements IGeneraltableService {

	@Autowired
	GeneraltableMapper generaltableMapper;
	
	 @Override
	public List<Map<String, Object>> getOneTableList(JSONObject jsonMap) {
	       ///System.out.println("已经调用这个测试类!!!");  
		 
		 //GeneraltableMapper generaltableMapper2=new  GeneraltableMapper();
		 String othercondition ="";
	     //   HashMap jsonMap = JSON.parseObject(generaltablereOne, HashMap.class);
	        //解析map方法。
	        String columns=jsonMap.get("columns").toString();
	        String tablename=jsonMap.get("tablename").toString();
	        String condition=jsonMap.get("condition").toString();
	        String order=jsonMap.get("order").toString();
	        String groupby=jsonMap.get("groupby").toString();
	        String limit=jsonMap.get("limit").toString();
	        String offset=jsonMap.get("offset").toString();

	        //如果没有group by 
	        if(StringUtils.isNotBlank(order)) {
	        	othercondition = " order by "+order;
	        }
	        if(StringUtils.isNotBlank(groupby)) {
	        	othercondition = " group  by "+groupby; 
	        }
	        if(StringUtils.isNotBlank(limit)&&StringUtils.isNotBlank(offset)) {
	        	othercondition = " limit "+ offset +","+limit ; 
	        }

	        return  generaltableMapper.getOneTableList(tablename,columns,condition,othercondition);
		
	 };
	 @Override
	 public void insertOne(String insertjosn) {
		 HashMap jsonMap = JSON.parseObject(insertjosn, HashMap.class);
	     //解析map方法。
		 String tablename=jsonMap.get("tablename").toString();
		 String insertset=jsonMap.get("insertset").toString();
		 Map insertsetmap = JSON.parseObject(insertset);  
		 String insertsetcon="";
	        for (Object obj : insertsetmap.keySet()){  
	        	if(StringUtils.isBlank(insertsetcon)) {
	        		insertsetcon = insertsetcon+ obj+"="+"'"+insertsetmap.get(obj)+"'";
	        	}else {
	        		insertsetcon = insertsetcon+","+obj+"="+"'"+insertsetmap.get(obj)+"'";
	        	}
	        }  
	        
	        generaltableMapper.insertOne(tablename,insertsetcon);
	 };
	 @Override
	 public void updateOne(String updatejosn) {
		 HashMap jsonMap = JSON.parseObject(updatejosn, HashMap.class);
	     //解析map方法。
		 String tablename=jsonMap.get("tablename").toString();
		 String updateset=jsonMap.get("updateset").toString();
		 String updatecondition=jsonMap.get("updatecondition").toString();
		 Map updatesetmap = JSON.parseObject(updateset);  
		 String updatesetmapcondition="";
	        for (Object obj : updatesetmap.keySet()){  
	        	if(StringUtils.isBlank(updatesetmapcondition)) {
	        		updatesetmapcondition = updatesetmapcondition+ obj+"="+"'"+updatesetmap.get(obj)+"'";
	        	}else {
	        		updatesetmapcondition = updatesetmapcondition+","+obj+"="+"'"+updatesetmap.get(obj)+"'";
	        	}
	        	
	        }  
	        
	        generaltableMapper.updateOne(tablename,updatesetmapcondition,updatecondition);
		};
	 @Override
	 public void deleteOne(String deletejosn) {
		 HashMap jsonMap = JSON.parseObject(deletejosn, HashMap.class);
	     //解析map方法。
		 String tablename=jsonMap.get("tablename").toString();
		 String delcondition=jsonMap.get("delcondition").toString();
		
	        
	        generaltableMapper.deleteOne(tablename,delcondition);
		};
}
