package com.geller.charts.domain.charts;

import java.util.ArrayList;
import java.util.List;

public class ChartData {
	private List<String> labels = new ArrayList<String>();
	private List<ChartDataSet> datasets = new ArrayList<ChartDataSet>();
	
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<ChartDataSet> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<ChartDataSet> datasets) {
		this.datasets = datasets;
	}
	
			
}
