package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinchm123
 * @since 2018-03-13
 */
public class Metadataview extends Model<Metadataview> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 视图名
     */
    private String viewname;
    /**
     * 视图名中文
     */
    private String viewnamecn;
    /**
     * 重写字段
     */
    private String replacecolumn;
    /**
     * 数据库表id
     */
    private String metadataid;
    /**
     * 是否在列表展示
     */
    private Integer display;
    /**
     * 是否在更新页面展示
     */
    private Integer update;
    /**
     * 是否在添加页面展示
     */
    private Integer add;
    /**
     * 是否可查询
     */
    private Integer isselect;
    /**
     * 鼠标单击执行方法
     */
    private String onclick;
    /**
     * 鼠标双击执行方法
     */
    private String ondblclick;
    /**
     * 当元素获得焦点时执行方法
     */
    private String onfocus;
    /**
     * 元素失去焦点时执行方法
     */
    private String onblur;
    /**
     * 有效标志
     */
    private String isvalid;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * 更新人
     */
    private String operator;
    /**
     * 操作号
     */
    private String operatnum;
    /**
     * 操作序号
     */
    private String operatnumbefore;
    /**
     * 字段页面显示顺序
     */
    private Integer fieldsort;
    /**
     * 开启列表排序
     */
    private Integer issort;
    /**
     * excel导入
     */
    private Integer imp;
    /**
     * excel导出
     */
    private Integer exp;
    
    /**
     * 是否作为view主键字段，每个view都必须有一个
     */
    private Integer viewid;
    /**
     * 是否只读
     */
    private Integer readonly;
    
    /**
     * 单元格点击事件
     */
    private String clickevent;
    /**
     * 自定义模板名称
     */
    private String templet;	
	/**
     * 自定义模板脚本
     */
    private String templetscript;
    /**
     * 单元格宽度
     */
    private Integer minwidth;
    /**
     * 默认值
     */
    private String defaultvalue;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getViewname() {
        return viewname;
    }

    public void setViewname(String viewname) {
        this.viewname = viewname;
    }

    public String getViewnamecn() {
        return viewnamecn;
    }

    public void setViewnamecn(String viewnamecn) {
        this.viewnamecn = viewnamecn;
    }

    public String getReplacecolumn() {
        return replacecolumn;
    }

    public void setReplacecolumn(String replacecolumn) {
        this.replacecolumn = replacecolumn;
    }

 

    public String getMetadataid() {
		return metadataid;
	}

	public void setMetadataid(String metadataid) {
		this.metadataid = metadataid;
	}

	public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public Integer getAdd() {
        return add;
    }

    public void setAdd(Integer add) {
        this.add = add;
    }

    public Integer getIsselect() {
        return isselect;
    }

    public void setIsselect(Integer isselect) {
        this.isselect = isselect;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOnfocus() {
        return onfocus;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnblur() {
        return onblur;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatnum() {
        return operatnum;
    }

    public void setOperatnum(String operatnum) {
        this.operatnum = operatnum;
    }

    public String getOperatnumbefore() {
        return operatnumbefore;
    }

    public void setOperatnumbefore(String operatnumbefore) {
        this.operatnumbefore = operatnumbefore;
    }

    public Integer getFieldsort() {
		return fieldsort;
	}

	public void setFieldsort(Integer fieldsort) {
		this.fieldsort = fieldsort;
	}

	public Integer getIssort() {
		return issort;
	}

	public void setIssort(Integer issort) {
		this.issort = issort;
	}

	public Integer getImp() {
		return imp;
	}

	public void setImp(Integer imp) {
		this.imp = imp;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}
	

	public Integer getViewid() {
		return viewid;
	}

	public void setViewid(Integer viewid) {
		this.viewid = viewid;
	}

	public Integer getReadonly() {
		return readonly;
	}

	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}
	public String getClickevent() {
		return clickevent;
	}

	public void setClickevent(String clickevent) {
		this.clickevent = clickevent;
	}

	public String getTemplet() {
		return templet;
	}

	public void setTemplet(String templet) {
		this.templet = templet;
	}

	public String getTempletscript() {
		return templetscript;
	}

	public void setTempletscript(String templetscript) {
		this.templetscript = templetscript;
	}

	public Integer getMinwidth() {
		return minwidth;
	}

	public void setMinwidth(Integer minwidth) {
		this.minwidth = minwidth;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
	
	

    @Override
    public String toString() {
        return "Metadataview{" +
        "id=" + id +
        ", viewname=" + viewname +
        ", viewnamecn=" + viewnamecn +
        ", replacecolumn=" + replacecolumn +
        ", metadataid=" + metadataid +
        ", display=" + display +
        ", update=" + update +
        ", add=" + add +
        ", isselect=" + isselect +
        ", onclick=" + onclick +
        ", ondblclick=" + ondblclick +
        ", onfocus=" + onfocus +
        ", onblur=" + onblur +
        ", isvalid=" + isvalid +
        ", updatetime=" + updatetime +
        ", operator=" + operator +
        ", operatnum=" + operatnum +
        ", operatnumbefore=" + operatnumbefore +
         ", fieldsort=" + fieldsort +
        ", issort=" + issort +
        ", imp=" + imp +
        ", exp=" +exp +
        ", viewid=" +viewid +
        ", readonly=" +readonly +
        ", clickevent=" +clickevent +
        ", templet=" +templet +
        ", templetscript=" +templetscript +
        ", minwidth=" +minwidth +
        ", defaultvalue=" +defaultvalue +
        "}";
    }
}
