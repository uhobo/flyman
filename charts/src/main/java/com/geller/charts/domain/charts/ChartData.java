package com.geller.charts.domain.charts;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChartData {
	private List<String> labels = new ArrayList<String>();
	@JsonProperty(value="datasets")
	private List<ChartDataSetInteface> datasets = new ArrayList<ChartDataSetInteface>();
	
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<ChartDataSetInteface> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<ChartDataSetInteface> datasets) {
		this.datasets = datasets;
	}
	
			
}
