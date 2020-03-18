package com.cobona.vici.modular.sqltable.service;

import com.cobona.vici.common.persistence.model.Sqltable;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinchm1123
 * @since 2018-09-20
 */
public interface ISqltableService extends IService<Sqltable> {

	List<Map<String, Object>> selectViewList(String condtion);

}
