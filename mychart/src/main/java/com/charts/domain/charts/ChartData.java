package com.charts.domain.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public abstract class ChartData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> labels = new ArrayList<>();

	
	@JsonProperty("datasets")
	protected List<ChartDataset> datasets = new ArrayList<>();
	
	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<ChartDataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<ChartDataset> datasets) {
		this.datasets = datasets;
	}
	
	public abstract Map<String, Object> getOptions();
	
	protected Map<String, Object> createParam(String name, Object value){

		Map<String, Object> map = new HashMap<>();
		map.put(name, value);
		return map;
	}
	public void addTitle(Map<String, Object> options, String title) {
		Map<String, Object> titleOption = createParam("display", true);
		titleOption.put("text", title);
		options.put("title",titleOption);
	}
	
}
