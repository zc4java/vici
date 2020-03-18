package com.cobona.vici.modular.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import com.cobona.vici.common.annotion.BussinessLog;
import com.cobona.vici.common.annotion.Permission;
import com.cobona.vici.common.constant.Const;
import com.cobona.vici.common.constant.factory.PageFactory;
import com.cobona.vici.common.constant.state.BizLogType;
import com.cobona.vici.common.persistence.dao.OperationLogMapper;
import com.cobona.vici.common.persistence.model.OperationLog;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.support.BeanKit;
import com.cobona.vici.modular.system.dao.LogDao;
import com.cobona.vici.modular.system.service.SuperService;
import com.cobona.vici.modular.system.warpper.LogWarpper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/super")
public class SuperController extends BaseController {

    private static String PREFIX = "/system/super/";

  

    @Resource
    private SuperService superService;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
        Page page = new PageFactory().defaultPage();
        List<JSONObject> list=superService.selectListPage("operation_log", "", page, null);
       // List<Map<String, Object>> result = logDao.getOperationLogs(page, beginTime, endTime, logName, BizLogType.valueOf(logType), page.getOrderByField(), page.isAsc());
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping("/detail/{id}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
    	JSONObject operationLog = superService.selectById(id, "operation_log", null);
       // Map<String, Object> stringObjectMap = BeanKit.beanToMap(operationLog);
        return super.warpObject(new LogWarpper(operationLog));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from operation_log");
        return super.SUCCESS_TIP;
    }
}
