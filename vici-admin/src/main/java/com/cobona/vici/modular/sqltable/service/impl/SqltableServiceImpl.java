package com.cobona.vici.modular.sqltable.service.impl;

import com.cobona.vici.common.persistence.model.Sqltable;
import com.cobona.vici.common.persistence.dao.MetadataviewMapper;
import com.cobona.vici.common.persistence.dao.SqltableMapper;
import com.cobona.vici.modular.sqltable.service.ISqltableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinchm1123
 * @since 2018-09-20
 */
@Service
public class SqltableServiceImpl extends ServiceImpl<SqltableMapper, Sqltable> implements ISqltableService {

	@Autowired
	SqltableMapper SqltableMapper;
	
	@Override
	public List<Map<String, Object>> selectViewList(String condtion) {
		// TODO Auto-generated method stub
		return SqltableMapper.selectViewListBycondtion(condtion);
	}

}
