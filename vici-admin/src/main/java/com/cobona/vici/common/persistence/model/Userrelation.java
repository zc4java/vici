package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
public class Userrelation extends Model<Userrelation> {

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
     * 用户id
     */
    private Integer userid;
    /**
     * 添加人id
     */
    private Integer adduid;
    /**
     * 授权时间开始
     */
    private Date starttime;
    /**
     * 授权结束时间
     */
    private Date endtime;
    /**
     * 状态
     */
    private Integer status;


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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAdduid() {
        return adduid;
    }

    public void setAdduid(Integer adduid) {
        this.adduid = adduid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Userrelation{" +
        "id=" + id +
        ", menuid=" + menuid +
        ", userid=" + userid +
        ", adduid=" + adduid +
        ", starttime=" + starttime +
        ", endtime=" + endtime +
        ", status=" + status +
        "}";
    }
}
