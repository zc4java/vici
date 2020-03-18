package com.cobona.vici.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cobona.vici.common.annotion.BussinessLog;
import com.cobona.vici.common.annotion.Permission;
import com.cobona.vici.common.constant.Const;
import com.cobona.vici.common.constant.dictmap.DictMap;
import com.cobona.vici.common.constant.factory.ConstantFactory;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.DictMapper;
import com.cobona.vici.common.persistence.model.Dict;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.log.LogObjectHolder;
import com.cobona.vici.core.util.ToolUtil;
import com.cobona.vici.modular.system.dao.DictDao;
import com.cobona.vici.modular.system.service.IDictService;
import com.cobona.vici.modular.system.warpper.DictWarpper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 *
 * @author cobona
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	private String PREFIX = "/system/dict/";

	@Resource
	DictDao dictDao;

	@Resource
	DictMapper dictMapper;

	@Resource
	IDictService dictService;

	/**
	 * 跳转到字典管理首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "dict.html";
	}

	/**
	 * 跳转到添加字典
	 */
	@RequestMapping("/dict_add")
	public String deptAdd() {
		return PREFIX + "dict_add.html";
	}

	/**
	 * 跳转到修改字典
	 */
	@RequestMapping("/dict_edit/{dictId}")
	public String deptUpdate(@PathVariable Integer dictId, Model model) {
		Dict dict = dictMapper.selectById(dictId);
		model.addAttribute("dict", dict);
		List<Dict> subDicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("pid", dictId));
		model.addAttribute("subDicts", subDicts);
		LogObjectHolder.me().set(dict);
		return PREFIX + "dict_edit.html";
	}

	/**
	 * 跳转到修改字典
	 */
	@RequestMapping("/dict_edit_byname/{dictName}")
	public String deptUpdate(@PathVariable String dictName, Model model) {
		List<Dict> pDicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("name", dictName));
		if (null != pDicts && pDicts.size() > 0) {
			Dict dict = pDicts.get(0);
			model.addAttribute("dict", dict);
			List<Dict> subDicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("pid", dict.getId()));
			model.addAttribute("subDicts", subDicts);
			LogObjectHolder.me().set(dict);
			return PREFIX + "dict_edit.html";
		}else{
			return PREFIX + "error.html";
		}
		
	}

	/**
	 * 新增字典
	 *
	 * @param dictValues
	 *            格式例如 "1:启用;2:禁用;3:冻结"
	 */
	@BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = DictMap.class)
	@RequestMapping(value = "/add")
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Object add(String dictName, String dictValues) {
		if (ToolUtil.isOneEmpty(dictName, dictValues)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		this.dictService.addDict(dictName, dictValues);
		return SUCCESS_TIP;
	}

	/**
	 * 获取所有字典列表
	 */
	@RequestMapping(value = "/list")
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Object list(String condition) {
		List<Map<String, Object>> list = this.dictDao.list(condition);
		return super.warpObject(new DictWarpper(list));
	}

	/**
	 * 字典详情
	 */
	@RequestMapping(value = "/detail/{dictId}")
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Object detail(@PathVariable("dictId") Integer dictId) {
		return dictMapper.selectById(dictId);
	}

	/**
	 * 修改字典
	 */
	@BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = DictMap.class)
	@RequestMapping(value = "/update")
	//@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Object update(Integer dictId, String dictName, String dictValues) {
		if (ToolUtil.isOneEmpty(dictId, dictName, dictValues)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		dictService.editDict(dictId, dictName, dictValues);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除字典记录
	 */
	@BussinessLog(value = "删除字典记录", key = "dictId", dict = DictMap.class)
	@RequestMapping(value = "/delete")
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Object delete(@RequestParam Integer dictId) {

		// 缓存被删除的名称
		LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));

		this.dictService.delteDict(dictId);
		return SUCCESS_TIP;
	}

}
