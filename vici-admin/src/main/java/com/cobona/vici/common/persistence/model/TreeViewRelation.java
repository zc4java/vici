package com.cobona.vici.common.persistence.model;

public class TreeViewRelation {

	private RelationEnum relationName;
	
	private String column;
	
    private String parentTableName;
    
	private String parentColumn = "UUID";
	
	private String ext_ref;

	
	public String getParentTableName() {
		return parentTableName;
	}

	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}

	public String getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(String parentColumn) {
		this.parentColumn = parentColumn;
	}

	public RelationEnum getRelationName() {
		return relationName;
	}

	public void setRelationName(RelationEnum relationName) {
		this.relationName = relationName;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getExt_ref() {
		return ext_ref;
	}

	public void setExt_ref(String ext_ref) {
		this.ext_ref = ext_ref;
	}
	
	
}
