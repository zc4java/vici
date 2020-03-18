package com.cobona.vici.modular.system.controller;

import com.cobona.vici.common.annotion.BussinessLog;
import com.cobona.vici.common.annotion.Permission;
import com.cobona.vici.common.constant.Const;
import com.cobona.vici.common.constant.dictmap.MenuDict;
import com.cobona.vici.common.constant.factory.ConstantFactory;
import com.cobona.vici.common.constant.state.MenuStatus;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.MenuMapper;
import com.cobona.vici.common.persistence.model.Menu;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.base.tips.Tip;
import com.cobona.vici.core.log.LogObjectHolder;
import com.cobona.vici.core.node.ZTreeNode;
import com.cobona.vici.core.shiro.ShiroKit;
import com.cobona.vici.core.support.BeanKit;
import com.cobona.vici.core.util.ToolUtil;
import com.cobona.vici.modular.system.dao.MenuDao;
import com.cobona.vici.modular.system.service.IMenuService;
import com.cobona.vici.modular.system.warpper.MenuWarpper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author cobona
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/system/menu/";

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuDao menuDao;

    @Resource
    IMenuService menuService;

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }
    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_addall")
    public String menuAddall() {
        return PREFIX + "menu_addall.html";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/menu_edit/{menuId}")
    public String menuEdit(@PathVariable Integer menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuMapper.selectById(menuId);

        //获取父级菜单的id
        Menu temp = new Menu();
        temp.setCode(menu.getPcode());
        Menu pMenu = this.menuMapper.selectOne(temp);

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            //设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(temp.getCode()));
        model.addAttribute("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return PREFIX + "menu_edit.html";
    }

    /**
     * 修该菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改菜单", key = "name", dict = MenuDict.class)
    @ResponseBody
    public Tip edit(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //设置父级菜单编号
        menuSetPcode(menu);

        this.menuMapper.updateById(menu);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
        List<Map<String, Object>> menus = this.menuDao.selectMenus(menuName, level);
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add")
    @BussinessLog(value = "菜单新增", key = "name", dict = MenuDict.class)
    @ResponseBody
    public Tip add(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new BussinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        //设置父级菜单编号
        menuSetPcode(menu);

        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuMapper.insert(menu);
        return SUCCESS_TIP;
    }
    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/addall")
    @BussinessLog(value = "批量菜单新增", key = "name", dict = MenuDict.class)
    @ResponseBody
    public Tip addAll(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        String tableName=menu.getCode();
        menu.setCode("page_"+tableName);
        menu.setUrl("/generaltable/"+tableName);
        String[] menuNames=new String[]{"添加","修改","查询","删除","导出","导入","模版"}; 
        String[] menuCodes=new String[]{"add","update","list","delete","export","import","downtemp"}; 
        //判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new BussinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }
        //设置父级菜单编号
        menuSetPcode(menu);
        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuMapper.insert(menu);
        for (int i = 0; i < menuNames.length; i++) {
        	Menu childMenu=new Menu();
        	childMenu.setName(menuNames[i]);
        	childMenu.setCode(tableName+"_"+menuCodes[i]);
        	childMenu.setIsadmin(0);
        	childMenu.setIsopen(0);
        	childMenu.setLevels(menu.getLevels()+1);
        	childMenu.setStatus(MenuStatus.ENABLE.getCode());
        	childMenu.setIsmenu(0);
        	childMenu.setNum(i+1);
        	childMenu.setPcode(menu.getCode());
        	childMenu.setPcodes(menu.getPcodes()+"["+menu.getCode()+"],");
        	childMenu.setUrl("/generaltable/"+tableName+"/"+menuCodes[i]);
        	childMenu.setComponentname(menu.getComponentname());
        	childMenu.setObjectname(menu.getObjectname());
        	this.menuMapper.insert(childMenu);
		}
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除菜单", key = "menuId", dict = MenuDict.class)
    @ResponseBody
    public Tip remove(@RequestParam Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));

        this.menuService.delMenuContainSubMenus(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}")
    @ResponseBody
    public Tip view(@PathVariable Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.menuMapper.selectById(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuDao.menuTreeList();
        return roleTreeList;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuDao.menuTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Integer> menuIds = this.menuDao.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = this.menuDao.menuTreeList();
            return roleTreeList;
        } else {
            List<ZTreeNode> roleTreeListByUserId = this.menuDao.menuTreeListByMenuIds(menuIds);
            return roleTreeListByUserId;
        }
    }
 
    @RequestMapping(value = "/menuTreeListByDeptId/{deptId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByDeptId(@PathVariable Integer deptId) {
        List<Integer> menuIds = this.menuDao.getMenuIdsByDeptId(deptId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> deptTreeList = this.menuDao.menuTreeList();
            return deptTreeList;
        } else {
            List<ZTreeNode> deptTreeListByUserId = this.menuDao.menuTreeListByMenuIds(menuIds);
            return deptTreeListByUserId;
        }
    }

    @RequestMapping(value = "/menuTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByUserId(@PathVariable Integer userId) {
       // List<Integer> menuIds = this.menuDao.getMenuIdsByUserId(userId);
        Integer addUid = ShiroKit.getUser().getId();
        /*if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> deptTreeList = this.menuDao.menuTreeListOfUser(addUid,userId);
            return deptTreeList;
        } else {*/
            List<ZTreeNode> deptTreeListByUserId = this.menuDao.menuTreeListOfUser(addUid,userId);
            return deptTreeListByUserId;
        //}
    }
    
    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(@Valid Menu menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            int code = Integer.parseInt(menu.getPcode());
            Menu pMenu = menuMapper.selectById(code);
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new BussinessException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
    }

}
