package com.cobona.vici.core.base.tips;

/**
 * 返回给前台的提示（最终转化为json形式）
 *
 * @author cobona
 * @Date 2017年1月11日 下午11:58:00
 */
public abstract class Tip {

    protected int code;
    protected String message;
    protected int id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
