package com.cobona.vici.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import com.cobona.vici.core.node.MenuNode;
import com.cobona.vici.core.node.ZTreeNode;

import java.util.List;
import java.util.Map;

/**
 * 菜单相关的dao
 *
 * @author cobona
 * @date 2017年2月12日 下午8:43:52
 */
public interface MenuDao {

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(Integer menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     *        isadmin
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNode> getMenusByRoleIds(@Param("list")List<Integer> roleIds,@Param("isadmin") Integer isadmin);

    /**
     * 获取资源url通过部门id
     *
     * @param deptId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
	List<Integer> getMenuIdsByDeptId(Integer deptId);

	List<MenuNode> getMenusOfUser(@Param("list")List<Integer> roleList,@Param("deptid") Integer deptId, @Param("userid")Integer userid, @Param("isadmin") Integer isadmin);

	List<String> getResUrlsByDeptId(Integer deptId);

	List<String> getResUrlsByUserId(Integer userId);


	List<ZTreeNode> menuTreeListOfUser(@Param("addUid")Integer addUid, @Param("userid") Integer userId);



}
