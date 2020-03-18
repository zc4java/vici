package com.cobona.vici.modular.system.service;

/**
 * 部门服务
 *
 * @author cobona
 * @date 2017-04-27 17:00
 */
public interface IDeptService {

    /**
     * 删除部门
     *
     * @author cobona
     * @Date 2017/7/11 22:30
     */
   void deleteDept(Integer deptId);
   /**
    * 设置某个部门的权限
    *
    * @param deptId 部门id
    * @param ids    权限的id
    * @date 2017年2月13日 下午8:26:53
    */
	void saveAuthority(Integer deptId, String ids);

}
