package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 服务商表
 * </p>
 *
 * @author jinchm123
 * @since 2018-01-21
 */
@TableName("sun_serviceprovider")
public class SunServiceprovider extends Model<SunServiceprovider> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 服务商名称
     */
    private String serviceprovidername;
    /**
     * 商务模式
     */
    private String commercialmodel;
    /**
     * 星级
     */
    private String starlevel;
    /**
     * 组织类型
     */
    private String organizationtype;
    /**
     * 销售模式
     */
    private String salestype;
    /**
     * 洽谈进度
     */
    private String contactprogress;
    /**
     * 联系人
     */
    private String contactperson;
    /**
     * 职务
     */
    private String duty;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 固定电话
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系地址
     */
    private String contactaddress;
    /**
     * 服务内容
     */
    private String servicecontent;
    /**
     * 管理年费
     */
    private BigDecimal managementfee;
    /**
     * 展柜年费
     */
    private BigDecimal showcasefee;
    /**
     * 线上分成
     */
    private String onlineshare;
    /**
     * 线下分成
     */
    private String offlineshare;
    /**
     * 协议日期
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date agreementdate;
    /**
     * 签约日期
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractdate;
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

    public String getServiceprovidername() {
        return serviceprovidername;
    }

    public void setServiceprovidername(String serviceprovidername) {
        this.serviceprovidername = serviceprovidername;
    }

    public String getCommercialmodel() {
        return commercialmodel;
    }

    public void setCommercialmodel(String commercialmodel) {
        this.commercialmodel = commercialmodel;
    }

    public String getStarlevel() {
        return starlevel;
    }

    public void setStarlevel(String starlevel) {
        this.starlevel = starlevel;
    }

    public String getOrganizationtype() {
        return organizationtype;
    }

    public void setOrganizationtype(String organizationtype) {
        this.organizationtype = organizationtype;
    }

    public String getSalestype() {
        return salestype;
    }

    public void setSalestype(String salestype) {
        this.salestype = salestype;
    }

    public String getContactprogress() {
        return contactprogress;
    }

    public void setContactprogress(String contactprogress) {
        this.contactprogress = contactprogress;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactaddress() {
        return contactaddress;
    }

    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }

    public String getServicecontent() {
        return servicecontent;
    }

    public void setServicecontent(String servicecontent) {
        this.servicecontent = servicecontent;
    }

    public BigDecimal getManagementfee() {
        return managementfee;
    }

    public void setManagementfee(BigDecimal managementfee) {
        this.managementfee = managementfee;
    }

    public BigDecimal getShowcasefee() {
        return showcasefee;
    }

    public void setShowcasefee(BigDecimal showcasefee) {
        this.showcasefee = showcasefee;
    }

    public String getOnlineshare() {
        return onlineshare;
    }

    public void setOnlineshare(String onlineshare) {
        this.onlineshare = onlineshare;
    }

    public String getOfflineshare() {
        return offlineshare;
    }

    public void setOfflineshare(String offlineshare) {
        this.offlineshare = offlineshare;
    }

    public Date getAgreementdate() {
        return agreementdate;
    }

    public void setAgreementdate(Date agreementdate) {
        this.agreementdate = agreementdate;
    }

    public Date getContractdate() {
        return contractdate;
    }

    public void setContractdate(Date contractdate) {
        this.contractdate = contractdate;
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
    
    

    @Override
    public String toString() {
        return "SunServiceprovider{" +
        "id=" + id +
        ", serviceprovidername=" + serviceprovidername +
        ", commercialmodel=" + commercialmodel +
        ", starlevel=" + starlevel +
        ", organizationtype=" + organizationtype +
        ", salestype=" + salestype +
        ", contactprogress=" + contactprogress +
        ", contactperson=" + contactperson +
        ", duty=" + duty +
        ", mobile=" + mobile +
        ", telephone=" + telephone +
        ", email=" + email +
        ", contactaddress=" + contactaddress +
        ", servicecontent=" + servicecontent +
        ", managementfee=" + managementfee +
        ", showcasefee=" + showcasefee +
        ", onlineshare=" + onlineshare +
        ", offlineshare=" + offlineshare +
        ", agreementdate=" + agreementdate +
        ", contractdate=" + contractdate +
        ", isvalid=" + isvalid +
        "}";
    }
}
