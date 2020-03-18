package com.cobona.vici.modular.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cobona.vici.common.exception.BizExceptionEnum;
import com.cobona.vici.common.exception.BussinessException;
import com.cobona.vici.common.persistence.dao.DictMapper;
import com.cobona.vici.common.persistence.model.Dict;
import com.cobona.vici.modular.system.dao.DictDao;
import com.cobona.vici.modular.system.dao.SuperDao;
import com.cobona.vici.modular.system.service.IDictService;
import com.cobona.vici.modular.system.service.SuperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.cobona.vici.common.constant.factory.MutiStrFactory.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DictServiceImpl implements IDictService {

    @Resource
    DictDao dictDao;

    @Resource
    DictMapper dictMapper;
    @Resource
	SuperDao superDao;
    
    @Autowired
	private SuperService superService;

    @Override
    public void addDict(String dictName, String dictValues) {
        //判断有没有该字典
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("name", dictName).and().eq("pid", 0));
        if(dicts != null && dicts.size() > 0){
            throw new BussinessException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = parseKeyValue(dictValues);

        //添加字典
        Dict dict = new Dict();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MUTI_STR_KEY);
            String name = item.get(MUTI_STR_VALUE);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            }catch (NumberFormatException e){
                throw new BussinessException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    public void editDict(Integer dictId, String dictName, String dicts) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictName,dicts);
    }

    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }
    @Override
    public Map<String, String> getDicMapByName(String dictName){
    	Map<String, String> dict=new HashMap<>();
    	if (dictName.toLowerCase().startsWith("sql:")){
    		List<JSONObject> dictList1 = superService.selectSql(dictName.replace("sql:", ""));
			for (JSONObject jsonObject : dictList1) {
				dict.put(jsonObject.getString("num"), jsonObject.getString("name"));
			}
    	}else{
    		List<Map<String, Object>> fieldlist = dictMapper.getDictByname(dictName);
    		for (Map<String, Object> map : fieldlist) {
    			JSONObject object = new JSONObject(map);
    			dict.put(object.getString("num"), object.getString("name"));
    		}
    	}
    	
    	
		return dict;
    }
}
