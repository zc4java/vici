package com.cobona.vici.modular.metadataview.service.impl;

import com.cobona.vici.common.persistence.model.Metadataview;
import com.cobona.vici.common.persistence.dao.MetadataviewMapper;
import com.cobona.vici.common.persistence.dao.SunServiceproviderMapper;
import com.cobona.vici.modular.metadataview.service.IMetadataviewService;
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
 * @author jinchm123
 * @since 2018-03-13
 */
@Service
public class MetadataviewServiceImpl extends ServiceImpl<MetadataviewMapper, Metadataview> implements IMetadataviewService {

	@Autowired
	MetadataviewMapper metadataviewMapper;
	
	 @Override
	 public List<Map<String, Object>> selectViewList(String condtion) {
		 return metadataviewMapper.selectViewListBycondtion(condtion);
			// sunServiceproviderMapper.providerCateoryRelationAddByProviderId(providerid,categoryids);
			
	 };
}
