package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 视图关系表
 * </p>
 *
 * @author jinchm123
 * @since 2018-03-13
 */
public class Metadataviewrelation extends Model<Metadataviewrelation> {

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
     * 视图中的表关联
     */
    private String relation;
    /**
     * 更新后执行语句
     */
    private String addafter;
    /**
     * 主表，唯一id所在到表
     */
    private String mastertable;
    /**
     * 删除操作表名
     */
    private String deletetables;
    /**
     * 添加页面
     */
    private String addview;
    /**
     * 修改页面
     */
    private String editview;
    /**
     * 列表页面
     */
    private String listview;
    /**
     * 排序
     */
    private String orderfield;
    /**
     * 打开单个页面
     */
    private String detailview;
    /**
     * 是否开启工具栏
     */
    private Integer toolbar;
    /**
     * 工具栏脚本
     */
    private String toolbarscript;
    /**
     * 表格行点击事件
     */
    private String rowclickevent;
    /**
     * 查询条件扩展字段
     */
    private String selectplus;
    /**
     * 单元格宽度
     */
    private Integer toolbarwidth;
    /**
     * excel更新标识
     */
    private String excelupdatebyfield;
    

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getAddafter() {
        return addafter;
    }

    public void setAddafter(String addafter) {
        this.addafter = addafter;
    }

    public String getMastertable() {
        return mastertable;
    }

    public void setMastertable(String mastertable) {
        this.mastertable = mastertable;
    }

    
    public String getDeletetables() {
		return deletetables;
	}

	public void setDeletetables(String deletetables) {
		this.deletetables = deletetables;
	}
	

	public String getAddview() {
		return addview;
	}

	public void setAddview(String addview) {
		this.addview = addview;
	}

	public String getEditview() {
		return editview;
	}

	public void setEditview(String editview) {
		this.editview = editview;
	}

	public String getListview() {
		return listview;
	}

	public void setListview(String listview) {
		this.listview = listview;
	}

	public String getOrderfield() {
		return orderfield;
	}

	public void setOrderfield(String orderfield) {
		this.orderfield = orderfield;
	}

	public String getDetailview() {
		return detailview;
	}

	public void setDetailview(String detailview) {
		this.detailview = detailview;
	}

	public Integer getToolbar() {
		return toolbar;
	}

	public void setToolbar(Integer toolbar) {
		this.toolbar = toolbar;
	}

	public String getToolbarscript() {
		return toolbarscript;
	}

	public void setToolbarscript(String toolbarscript) {
		this.toolbarscript = toolbarscript;
	}

	public String getRowclickevent() {
		return rowclickevent;
	}

	public void setRowclickevent(String rowclickevent) {
		this.rowclickevent = rowclickevent;
	}
	
    public String getSelectplus() {
		return selectplus;
	}

	public void setSelectplus(String selectplus) {
		this.selectplus = selectplus;
	}
	
	public Integer getToolbarwidth() {
		return toolbarwidth;
	}

	public void setToolbarwidth(Integer toolbarwidth) {
		this.toolbarwidth = toolbarwidth;
	}

	public String getExcelupdatebyfield() {
		return excelupdatebyfield;
	}

	public void setExcelupdatebyfield(String excelupdatebyfield) {
		this.excelupdatebyfield = excelupdatebyfield;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
	

    @Override
    public String toString() {
        return "Metadataviewrelation{" +
        "id=" + id +
        ", viewname=" + viewname +
        ", relation=" + relation +
        ", addafter=" + addafter +
        ", mastertable=" + mastertable +
        ", deletetables=" + deletetables +
        ", addview=" + addview +
        ", editview=" + editview +
        ", listview=" + listview +
        ", orderfield=" + orderfield +
        ", detailview=" + detailview +
        ", toolbar=" + toolbar +
        ", toolbarscript=" + toolbarscript +
        ", rowclickevent=" + rowclickevent +
        ", selectplus=" + selectplus +
        ", toolbarwidth=" + toolbarwidth +
        ", excelupdatebyfield=" + excelupdatebyfield +
        "}";
    }
}
