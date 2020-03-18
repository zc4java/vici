package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cobona123
 * @since 2018-01-06
 */
public class Metadata extends Model<Metadata> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 数据库表名
     */
    private String tablename;
    /**
     * 数据库表中文含义
     */
    private String tablenamecn;
    /**
     * 数据库字段名
     */
    private String field;
    /**
     * 数据库字段类型
     */
    private String fieldtype;
    /**
     * 字段长度
     */
    private int fieldlength;
    /**
     * 小数点
     */
    private String decimalpoint;
    /**
     * 是否可以为空
     */
    private String isnullable;
    /**
     * 数据库字段注释
     */
    private String title;
    /**
     * 是否级联删除下级节点数据
     */
    private String isdelcascade;
    /**
     * 父节点
     */
    private String fathernode;
    /**
     * 是否是主键
     */
    private String ismajorkey;
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
    //////////////////////////////////
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
     * 校验规则
     */
    private String verifyrole;
    /**
     * 是否可查询
     */
    private Integer isselect;
    /**
     * 字典表pname或者查询sql
     */
    private String dict;
    /**
     * 输入类型
     */
    private String inputtype;
    /**
     *鼠标单击执行方法
     */
    private String onclick;
    /**
     *鼠标双击执行方法
     */
    private String ondblclick;
    /**
     *当元素获得焦点时执行方法
     */
    private String onfocus;
    /**
     *元素失去焦点时执行方法
     */
    private String onblur;
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
     * excel导出
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
     * 查询方式
     */
    private String selecttype;
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

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTablenamecn() {
        return tablenamecn;
    }

    public void setTablenamecn(String tablenamecn) {
        this.tablenamecn = tablenamecn;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public int getFieldlength() {
        return fieldlength;
    }

    public void setFieldlength(int fieldlength) {
        this.fieldlength = fieldlength;
    }

    public String getDecimalpoint() {
        return decimalpoint;
    }

    public void setDecimalpoint(String decimalpoint) {
        this.decimalpoint = decimalpoint;
    }

    public String getIsnullable() {
        return isnullable;
    }

    public void setIsnullable(String isnullable) {
        this.isnullable = isnullable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  

    public String getFathernode() {
        return fathernode;
    }

    public void setFathernode(String fathernode) {
        this.fathernode = fathernode;
    }

    public String getIsmajorkey() {
        return ismajorkey;
    }

    public void setIsmajorkey(String ismajorkey) {
        this.ismajorkey = ismajorkey;
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

	public String getVerifyrole() {
		return verifyrole;
	}

	public void setVerifyrole(String verifyrole) {
		this.verifyrole = verifyrole;
	}

	public Integer getIsselect() {
		return isselect;
	}

	public void setIsselect(Integer isselect) {
		this.isselect = isselect;
	}

	public String getDict() {
		return dict;
	}

	public void setDict(String dict) {
		this.dict = dict;
	}

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
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
	

	public Integer getReadonly() {
		return readonly;
	}

	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}

	
	public String getIsdelcascade() {
		return isdelcascade;
	}

	public void setIsdelcascade(String isdelcascade) {
		this.isdelcascade = isdelcascade;
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
	

	public String getSelecttype() {
		return selecttype;
	}

	public void setSelecttype(String selecttype) {
		this.selecttype = selecttype;
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
        return "Metadata{" +
        "id=" + id +
        ", tablename=" + tablename +
        ", tablenamecn=" + tablenamecn +
        ", field=" + field +
        ", fieldtype=" + fieldtype +
        ", fieldlength=" + fieldlength +
        ", decimalpoint=" + decimalpoint +
        ", isnullable=" + isnullable +
        ", title=" + title +
        ", isdelcascade=" + isdelcascade +
        ", fathernode=" + fathernode +
        ", ismajorkey=" + ismajorkey +
        ", isvalid=" + isvalid +
        ", updatetime=" + updatetime +
        ", operator=" + operator +
        ", operatnum=" + operatnum +
        ", operatnumbefore=" + operatnumbefore +
        ", display=" + display +
        ", update=" + update +
        ", add=" + add +
        ", verifyrole=" + verifyrole +
        ", isselect=" + isselect +
        ", dict=" +dict +
        ", inputtype=" + inputtype +
        ", onclick=" + onclick +
        ", ondblclick=" + ondblclick +
        ", onfocus=" + onfocus +
        ", onblur=" + onblur +
        ", fieldsort=" + fieldsort +
        ", issort=" + issort +
        ", imp=" + imp +
        ", exp=" +exp +
        ", readonly=" +readonly +
        ", clickevent=" +clickevent +
        ", templet=" +templet +
        ", templetscript=" +templetscript +
        ", minwidth=" +minwidth +
        ", selecttype=" +selecttype +
        ", defaultvalue=" +defaultvalue +
        "}";
    }
}
