package com.cobona.vici.common.persistence.dao;

import com.cobona.vici.common.persistence.model.SunServicecategory;
import com.cobona.vici.core.node.ZTreeNode;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 服务商分类表 Mapper 接口
 * </p>
 *
 * @author jinchm123
 * @since 2018-01-21
 */
public interface SunServicecategoryMapper extends BaseMapper<SunServicecategory> {

	List<ZTreeNode> servicecategoryTreeList();
	List<ZTreeNode> servicecategoryTreeListByCategoryType(@Param("categoryType") String categoryType);
    List<ZTreeNode> servicecategoryTreeListByProviderId(@Param("providerid") String providerid);
    
    List<ZTreeNode> servicedeptTreeList();
    
    List<ZTreeNode> servicedeptTreeListByProviderId(@Param("providerid") String providerid);
    
    
//    获取产品类型接口
  //  public List<Map<String, Object>> getProductcategoryAll();
//  获取产品类型接口
 // public List<Map<String, Object>> getProductcategoryByServicecategory(@Param("servicecategory")String servicecategory);
  
//获取产品类别接口
//public List<Map<String, Object>> getProducttypeAll();
//获取产品类别接口
//public List<Map<String, Object>> getProducttypeByProductcategory(@Param("productcategory")String productcategory);
    //按照级别获取category 
    public List<Map<String, Object>> getProductcategoryByLevel(@Param("level")String level);
  //按照父类选项返回
    public List<Map<String, Object>> getProductcategoryByPids(@Param("pids")String pids);

}
