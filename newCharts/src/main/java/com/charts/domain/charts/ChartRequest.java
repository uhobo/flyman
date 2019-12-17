package com.charts.domain.charts;

import java.io.Serializable;
import java.util.List;

import com.charts.domain.treemenu.TreeNode;

public class ChartRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileDataId;
	
	private Integer chartType;
	
	private Integer selectedSubject;
	
	private boolean includeAverage;
	
	private boolean includeMedian;
	
	
	private List<TreeNode> selectXnodes;
	
	private List<TreeNode> selectYnodes;
	
	public String getFileDataId() {
		return fileDataId;
	}

	public void setFileDataId(String fileDataId) {
		this.fileDataId = fileDataId;
	}

	public Integer getChartType() {
		return chartType;
	}

	public void setChartType(Integer chartType) {
		this.chartType = chartType;
	}


	public Integer getSelectedSubject() {
		return selectedSubject;
	}

	public void setSelectedSubject(Integer selectedSubject) {
		this.selectedSubject = selectedSubject;
	}

	
	public boolean isIncludeAverage() {
		return includeAverage;
	}

	public void setIncludeAverage(boolean includeAverage) {
		this.includeAverage = includeAverage;
	}

	public boolean isIncludeMedian() {
		return includeMedian;
	}

	public void setIncludeMedian(boolean includeMedian) {
		this.includeMedian = includeMedian;
	}

	public List<TreeNode> getSelectXnodes() {
		return selectXnodes;
	}

	public void setSelectXnodes(List<TreeNode> selectXnodes) {
		this.selectXnodes = selectXnodes;
	}

	public List<TreeNode> getSelectYnodes() {
		return selectYnodes;
	}

	public void setSelectYnodes(List<TreeNode> selectYnodes) {
		this.selectYnodes = selectYnodes;
	}

	@Override
	public String toString() {
		return "ChartRequest [fileDataId=" + fileDataId + ", chartType=" + chartType + ", selectedSubject="
				+ selectedSubject + ", includeAverage=" + includeAverage + ", includeMedian=" + includeMedian
				+ ", selectXnodes=[" + printSelectedNodes(selectXnodes) + "], selectYnodes=[" + printSelectedNodes(selectYnodes) + "]]";
	}

	private String printSelectedNodes(List<TreeNode> treeNodeList) {
		StringBuffer buff = new StringBuffer();
		for(TreeNode treeNode: treeNodeList) {
			buff.append("{").append(treeNode).append("}");
		}
		return buff.toString();
	}
	
	
	
	
}
