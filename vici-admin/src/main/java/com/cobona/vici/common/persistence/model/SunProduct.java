package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;

import org.springframework.data.annotation.Transient;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 产品表
 * </p>
 *
 * @author jinchm123
 * @since 2018-01-28
 */
@TableName("sun_product")
public class SunProduct extends Model<SunProduct> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String productname;
    /**
     * 服务类型
     */
    private String servicecategory;
    /**
     * 产品分类
     */
    private String productcategory;
    /**
     * 产品类型
     */
    private String producttype;
    /**
     * 商品单位
     */
    private String productunit;
    /**
     * 市场价
     */
    private BigDecimal marketprice;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 售价
     */
    private BigDecimal saleprice;
    /**
     * 说明
     */
    private String remark;
    /**
     * 有效标志
     */
    private Integer isvalid;
    /**
     * 服务商id
     */
    private Integer providerid;

   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getServicecategory() {
        return servicecategory;
    }

    public void setServicecategory(String servicecategory) {
        this.servicecategory = servicecategory;
    }

    public String getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(String productcategory) {
        this.productcategory = productcategory;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getProductunit() {
        return productunit;
    }

    public void setProductunit(String productunit) {
        this.productunit = productunit;
    }

    public BigDecimal getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(BigDecimal marketprice) {
        this.marketprice = marketprice;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public BigDecimal getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(BigDecimal saleprice) {
        this.saleprice = saleprice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    

    public Integer getProviderid() {
		return providerid;
	}

	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}

	

	@Override
    public String toString() {
        return "SunProduct{" +
        "id=" + id +
        ", productname=" + productname +
        ", servicecategory=" + servicecategory +
        ", productcategory=" + productcategory +
        ", producttype=" + producttype +
        ", productunit=" + productunit +
        ", marketprice=" + marketprice +
        ", costprice=" + costprice +
        ", saleprice=" + saleprice +
        ", remark=" + remark +
        ", isvalid=" + isvalid +
        ", providerid=" + providerid +
        "}";
    }
}
