package com.geller.charts.domain.treetable;

import java.util.ArrayList;
import java.util.List;

public class TreeTableData {
	
	private List<TableHeader> tableHeaders = new ArrayList<TableHeader>();

	private List<TreeNode> data;
	
	public void addHeader(String fieldName, String header) {
		TableHeader tableHeader = new TableHeader();
		tableHeader.setField(fieldName);
		tableHeader.setHeader(header);
		this.tableHeaders.add(tableHeader);
	}
	
	public TreeNode createRowData(String field, String value) {
		return  new TreeNode();
	}
	
	public void addParentRow(TreeNode treeNode) {
		this.data.add(treeNode);
	}
	
}
