package com.cobona.vici.modular.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cobona.vici.common.annotion.BussinessLog;
import com.cobona.vici.common.annotion.Permission;
import com.cobona.vici.common.constant.Const;
import com.cobona.vici.common.constant.dictmap.DeptDict;
import com.cobona.vici.common.constant.factory.ConstantFactory;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.DeptMapper;
import com.cobona.vici.common.persistence.model.Dept;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.base.tips.Tip;
import com.cobona.vici.core.log.LogObjectHolder;
import com.cobona.vici.core.node.ZTreeNode;
import com.cobona.vici.core.util.ToolUtil;
import com.cobona.vici.modular.system.dao.DeptDao;
import com.cobona.vici.modular.system.service.IDeptService;
import com.cobona.vici.modular.system.warpper.DeptWarpper;

/**
 * 部门控制器
 *
 * @author cobona
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Resource
    DeptDao deptDao;

    @Resource
    DeptMapper deptMapper;

    @Resource
    IDeptService deptService;

    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptMapper.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptDao.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        return this.deptMapper.insert(dept);
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.deptDao.list(condition);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptMapper.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
        deptMapper.updateById(dept);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptMapper.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
    
    /**
     * 跳转到权限分配
     */
    @Permission
    @RequestMapping(value = "/setAuthority/{deptId}")
    public String setAuthority(@PathVariable("deptId") Integer deptId, Model model) {
        if (ToolUtil.isEmpty(deptId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("deptID", deptId);
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(deptId));
        return PREFIX + "dept_set_authority.html";
    }
    
    /**
     * 配置权限
     */
    @RequestMapping("/saveAuthority")
    @BussinessLog(value = "配置权限", key = "deptId,ids", dict = DeptDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip saveAuthority(@RequestParam("deptId") Integer deptId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(deptId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.deptService.saveAuthority(deptId, ids);
        return SUCCESS_TIP;
    }
}
