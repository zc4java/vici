package com.cobona.vici.modular.metadata.service;

import java.util.List;
import java.util.Map;

import org.apache.shiro.dao.DataAccessException;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.cobona.vici.common.persistence.model.Generaltable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinchm
 * @since 2018-01-07
 */
public interface IGeneraltableService extends IService<Generaltable> {
	
	  
	public List<Map<String, Object>> getOneTableList(JSONObject generaltablereOne);
	public void insertOne(String insertjosn);
	public void updateOne(String updatejosn);
	public void deleteOne(String deletejosn);
}
