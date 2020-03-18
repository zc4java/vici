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
public class SunProductWarpper extends BaseControllerWarpper {

    public SunProductWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	DateFormat dFormat3 = new SimpleDateFormat("yyyyMMdd");  
       // formatDate = dFormat3.format(dt);  
//        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
//        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
//        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
//        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
    	
    	map.put("providername", ConstantFactory.me().getProviderName((Integer) map.get("providerid")));
    	//map.put("servicecategoryname", ConstantFactory.me().getCategoryName((String) map.get("servicecategory")));
    	//map.put("servicecategoryname", ConstantFactory.me().getGeneralName("服务类型枚举", (String)map.get("servicecategory")));
    	map.put("servicecategoryname", ConstantFactory.me().getCategoryName((String)map.get("servicecategory")));
    	map.put("productcategoryname", ConstantFactory.me().getCategoryName((String) map.get("productcategory")));
    	map.put("producttypename", ConstantFactory.me().getCategoryName((String) map.get("producttype")));
    	//map.put("agreementdate",dFormat3.format(map.get("agreementdate")));
    }

}
