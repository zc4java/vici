package com.cobona.vici.modular.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cobona.vici.common.persistence.dao.DeptMapper;
import com.cobona.vici.common.persistence.dao.DeptrelationMapper;
import com.cobona.vici.common.persistence.model.Dept;
import com.cobona.vici.common.persistence.model.Deptrelation;
import com.cobona.vici.core.util.Convert;
import com.cobona.vici.modular.system.dao.DeptDao;
import com.cobona.vici.modular.system.service.IDeptService;

@Service
@Transactional
public class DeptServiceImpl implements IDeptService {

    @Resource
    DeptMapper deptMapper;
    @Resource
    DeptDao deptDao;
    @Resource
    DeptrelationMapper deptrelationMapper;

    @Override
    public void deleteDept(Integer deptId) {

        Dept dept = deptMapper.selectById(deptId);

        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectList(wrapper);
        for (Dept temp : subDepts) {
            temp.deleteById();
        }

        dept.deleteById();
    }

	@Override
	public void saveAuthority(Integer deptId, String ids) {
		 // 删除该角色所有的权限
		deptDao.deleteDeptrelationByDeptId(deptId);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Deptrelation relation = new Deptrelation();
            relation.setDeptid(deptId);
            relation.setMenuid(id);
            this.deptrelationMapper.insert(relation);
        }
		
	}
}
