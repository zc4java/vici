package com.cobona.vici.common.constant.dictmap;

import com.cobona.vici.common.constant.dictmap.base.AbstractDictMap;

/**
 * 通知的映射
 *
 * @author cobona
 * @date 2017-05-06 15:01
 */
public class NoticeMap extends AbstractDictMap {

    @Override
    public void init() {
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
    }
}
