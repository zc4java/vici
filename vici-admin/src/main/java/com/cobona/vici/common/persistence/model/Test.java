package com.cobona.vici.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cobona
 * @since 2017-07-11
 */
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String value;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Test{" +
			"id=" + id +
			", value=" + value +
			"}";
	}
}
