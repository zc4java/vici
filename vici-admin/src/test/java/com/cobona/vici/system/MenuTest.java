package com.cobona.vici.system;

import com.baomidou.mybatisplus.mapper.*;
import com.cobona.vici.common.persistence.dao.MenuMapper;
import com.cobona.vici.common.persistence.model.Menu;
import com.cobona.vici.base.BaseJunit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Stack;

/**
 * 菜单测试
 *
 * @author cobona
 * @date 2017-06-13 21:23
 */
public class MenuTest extends BaseJunit {

    @Autowired
    MenuMapper menuMapper;

    /**
     * 初始化pcodes
     *
     * @author cobona
     * @Date 2017/6/13 21:24
     */
    @Test
    public void generatePcodes() {
        List<Menu> menus = menuMapper.selectList(null);
        for (Menu menu : menus) {
            if ("0".equals(menu.getPcode()) || null == menu.getPcode()) {
                menu.setPcodes("[0],");
            } else {
                StringBuffer sb = new StringBuffer();
                Menu parentMenu = getParentMenu(menu.getCode());
                sb.append("[0],");
                Stack<String> pcodes = new Stack<>();
                while (null != parentMenu.getPcode()) {
                    pcodes.push(parentMenu.getCode());
                    parentMenu = getParentMenu(parentMenu.getPcode());
                }
                int pcodeSize = pcodes.size();
                for (int i = 0; i < pcodeSize; i++) {
                    String code = pcodes.pop();
                    if (code.equals(menu.getCode())) {
                        continue;
                    }
                    sb.append("[" + code + "],");
                }

                menu.setPcodes(sb.toString());
            }
            menu.updateById();
        }
    }

    private Menu getParentMenu(String code) {
        Wrapper<Menu> wrapper = new EntityWrapper<Menu>();
        wrapper = wrapper.eq("code", code);
        List<Menu> menus = menuMapper.selectList(wrapper);
        if (menus == null || menus.size() == 0) {
            return new Menu();
        } else {
            return menus.get(0);
        }
    }
}
