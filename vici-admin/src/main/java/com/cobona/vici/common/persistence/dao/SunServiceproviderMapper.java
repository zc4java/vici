package com.cobona.vici.common.persistence.dao;

import com.cobona.vici.common.persistence.model.SunServiceprovider;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 服务商表 Mapper 接口
 * </p>
 *
 * @author jinchm123
 * @since 2018-01-21
 */
public interface SunServiceproviderMapper extends BaseMapper<SunServiceprovider> {
	
	public void providerCateoryRelationAddByProviderId(@Param("providerid")String providerid,@Param("categoryids")String categoryids);
	public void providerCateoryRelationDelByProviderId(@Param("providerid")String providerid);
	public void providerDeptRelationAddByProviderId(@Param("providerid")String providerid,@Param("deptids")String deptids);
	public void providerDeptRelationDelByProviderId(@Param("providerid")String providerid);
	public void providerDeptRelationInvalidByProviderId(@Param("providerid")String providerid);
	public  List<Map<String, Object>> providernameid();
}
