package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

public class MetaDataTreeView extends Model<Metadata> {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 唯一id
     */
    private Integer id;
    
    /***
     * 视图名称
     */
    private String viewname;
    
    private String tablename;
    
    private String parenttablename;
    
    private String parentjoinfield;
    
    private String joinfield;
    
    private String joinwhere;
    
	
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


	public String getTablename() {
		return tablename;
	}


	public void setTablename(String tablename) {
		this.tablename = tablename;
	}


	public String getParenttablename() {
		return parenttablename;
	}


	public void setParenttablename(String parenttablename) {
		this.parenttablename = parenttablename;
	}


	public String getParentjoinfield() {
		return parentjoinfield;
	}


	public void setParentjoinfield(String parentjoinfield) {
		this.parentjoinfield = parentjoinfield;
	}


	public String getJoinfield() {
		return joinfield;
	}


	public void setJoinfield(String joinfield) {
		this.joinfield = joinfield;
	}


	public String getJoinwhere() {
		return joinwhere;
	}


	public void setJoinwhere(String joinwhere) {
		this.joinwhere = joinwhere;
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
