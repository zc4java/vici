package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author jinchm123
 * @since 2018-08-30
 */
public class Deptrelation extends Model<Deptrelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单id
     */
    private Integer menuid;
    /**
     * 部门id
     */
    private Integer deptid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Deptrelation{" +
        "id=" + id +
        ", menuid=" + menuid +
        ", deptid=" + deptid +
        "}";
    }
}
