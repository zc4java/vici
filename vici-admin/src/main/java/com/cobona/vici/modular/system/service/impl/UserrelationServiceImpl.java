package com.cobona.vici.modular.system.service.impl;

import com.cobona.vici.common.persistence.model.Deptrelation;
import com.cobona.vici.common.persistence.model.Userrelation;
import com.cobona.vici.core.util.Convert;
import com.cobona.vici.modular.system.dao.DictDao;
import com.cobona.vici.modular.system.dao.UserMgrDao;
import com.cobona.vici.modular.system.service.IUserrelationService;
import com.cobona.vici.common.persistence.dao.UserrelationMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author jinchm123
 * @since 2018-08-30
 */
@Service
public class UserrelationServiceImpl extends ServiceImpl<UserrelationMapper, Userrelation> implements IUserrelationService {
	 @Resource
	 UserMgrDao userMgrDao;
	 @Resource
	 UserrelationMapper userrelationMapper;

	@Override
	public void saveAuthority(Integer userId, Integer addUid, String ids) {
		
		 // 删除该角色所有的权限
		userMgrDao.deleteUserrelationByUserIdAndAddUid(userId,addUid);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Userrelation relation = new Userrelation();
            relation.setUserid(userId);
            relation.setAdduid(addUid);
            relation.setMenuid(id);
            this.userrelationMapper.insert(relation);
        }
		
	}

}
