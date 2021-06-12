package com.charts.domain.charts;

import java.io.Serializable;
import java.util.List;

import com.charts.domain.treemenu.TreeNode;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
	
	private Boolean chartByGroup;
	
	private Boolean distrubution;
	
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
	

	
	public Boolean isChartByGroup() {
		return chartByGroup;
	}

	public void setChartByGroup(Boolean chartByGroup) {
		this.chartByGroup = chartByGroup;
	}
	
	

	public Boolean getDistrubution() {
		return distrubution;
	}

	public void setDistrubution(Boolean distrubution) {
		this.distrubution = distrubution;
	}

	public Boolean getChartByGroup() {
		return chartByGroup;
	}

	@Override
	public String toString() {
		
		ObjectMapper map = new ObjectMapper();
		try {
			map.setSerializationInclusion(Include.NON_NULL);
			return map.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
}
