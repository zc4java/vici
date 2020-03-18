package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 服务商分类表
 * </p>
 *
 * @author jinchm123
 * @since 2018-01-21
 */
@TableName("sun_servicecategory")
public class SunServicecategory extends Model<SunServicecategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父编码
     */
    private Integer pid;
    /**
     * 当前分类的所有父分类编号
     */
    private String pids;
    private String name;
  
    private Integer num;
    /**
     * 有效标志
     */
    private Integer isvalid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

   

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    
    
  

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}
	

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
    public String toString() {
        return "SunServicecategory{" +
        "id=" + id +
        ", pid=" + pid +
        ", pids=" + pids +
        ", name=" + name +
        ", num=" + num +
        ", isvalid=" + isvalid +
        "}";
    }
}
