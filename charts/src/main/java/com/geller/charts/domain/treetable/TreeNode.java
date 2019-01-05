package com.geller.charts.domain.treetable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {
	
	private Map<String,String> data = new HashMap<String,String>();
	private List<TreeNode> children;
	public Map<String, String> getData() {
		return data;
	}
	
	public void addData(String field, String value) {
		this.data.put(field, value);
	}
	
	public TreeNode createChild() {
		return new TreeNode();
	}
	
	public void addChild(TreeNode child) {
		this.children.add(child);
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	
	
	
	
	
	
	
}
