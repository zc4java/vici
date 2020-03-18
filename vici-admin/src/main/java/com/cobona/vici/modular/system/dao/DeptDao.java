package com.cobona.vici.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import com.cobona.vici.core.node.ZTreeNode;

import java.util.List;
import java.util.Map;

/**
 * 部门dao
 *
 * @author cobona
 * @date 2017年2月17日20:28:58
 */
public interface DeptDao {

    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);
    
    /**
     * 删除某个部门的所有权限
     *
     * @param deptId 部门id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteDeptrelationByDeptId(@Param("deptId") Integer deptId);
}
