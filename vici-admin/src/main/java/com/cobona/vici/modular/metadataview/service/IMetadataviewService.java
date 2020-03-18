package com.cobona.vici.modular.metadataview.service;

import com.cobona.vici.common.persistence.model.Metadataview;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinchm123
 * @since 2018-03-13
 */
public interface IMetadataviewService extends IService<Metadataview> {

	List<Map<String, Object>> selectViewList(String condtion);

}
