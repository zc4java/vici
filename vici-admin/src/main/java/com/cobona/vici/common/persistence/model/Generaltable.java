package com.cobona.vici.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinchm
 * @since 2018-01-07
 */
public class Generaltable extends Model<Generaltable> {

    private static final long serialVersionUID = 1L;

    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Generaltable{" +
        "id=" + id +
        "}";
    }
}
