package com.charts.domain.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ChartDataset implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String label;
	private List<Double> data = new ArrayList<>();
	private boolean isStatistics;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public boolean isStatistics() {
		return isStatistics;
	}
	public void setStatistics(boolean isStatistics) {
		this.isStatistics = isStatistics;
	}
	 
	 

}
