package com.cobona.vici.modular.system.warpper;

import com.cobona.vici.common.constant.factory.ConstantFactory;
import com.cobona.vici.core.base.warpper.BaseControllerWarpper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author jinchm
 * @date 2018年1月28日 下午10:47:03
 */
public class SunservicecategoryWarpper extends BaseControllerWarpper {

    public SunservicecategoryWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	//map.put("servicecategoryname", ConstantFactory.me().getGeneralName("服务类型枚举", (String)map.get("servicecategory")));
    	map.put("pidname", ConstantFactory.me().getCategoryName(map.get("pid").toString()));
    	 //map.put("pidname", ConstantFactory.me().getGeneralName("服务类型枚举", (String)map.get("pid")));
   
    	

    }

}
