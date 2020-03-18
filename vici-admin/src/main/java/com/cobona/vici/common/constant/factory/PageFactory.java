package com.cobona.vici.common.constant.factory;

import com.baomidou.mybatisplus.plugins.Page;
import com.cobona.vici.common.constant.state.Order;
import com.cobona.vici.core.support.HttpKit;
import com.cobona.vici.core.util.ToolUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author cobona
 * @date 2017-04-05 22:25
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
       
        int offset =0;
        int limit = Integer.valueOf(request.getParameter("limit"));     //每页多少条数据
        if(null!=request.getParameter("page")){
        	 int pageIndex= Integer.valueOf(request.getParameter("page"))-1;
        	 offset = pageIndex*limit;   //每页的偏移量(本页当前有多少条)
        }else{
        	offset = Integer.valueOf(request.getParameter("offset"));   //每页的偏移量(本页当前有多少条)
        }
        String sort = request.getParameter("sort");         //排序字段名称
        if(null!=sort&&sort.endsWith("_show")){
        	sort=sort.substring(0, sort.lastIndexOf("_show"));
        }
        String order = request.getParameter("order");       //asc或desc(升序或降序)
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }
}
