package com.cobona.vici.common.persistence.model;

public class TreeViewChildModel {

	private TreeViewModel relationNode;
	
	private TreeViewRelation ref;

	public TreeViewModel getRelationNode() {
		return relationNode;
	}

	public void setRelationNode(TreeViewModel relationNode) {
		this.relationNode = relationNode;
	}

	public TreeViewRelation getRef() {
		return ref;
	}

	public void setRef(TreeViewRelation ref) {
		this.ref = ref;
	}
	
	
}
