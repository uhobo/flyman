package com.charts.domain.charts;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class PieChart extends ChartData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	@Override
	@JsonIgnore
	public Map<String, Object> getOptions() {
		Map<String, Object> options = new HashMap<>();
		Map<String, Object> params = createParam("labels", createParam("title", createParam("color", "blue")));
		params.put("font", createParam("size", "16"));
		options.put ("plugins", createParam("datalabels", params)); 
		return options;
	}
	
	
}
