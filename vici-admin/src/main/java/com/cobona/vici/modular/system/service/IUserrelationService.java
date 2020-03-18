package com.cobona.vici.modular.system.service;

import com.cobona.vici.common.persistence.model.Userrelation;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author jinchm123
 * @since 2018-08-30
 */
public interface IUserrelationService extends IService<Userrelation> {

	void saveAuthority(Integer userId, Integer addUid, String ids);

}
