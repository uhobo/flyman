package com.charts.domain.charts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class DiagramChart extends ChartData{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	public Map<String, Object> getOptions(){
		
		Map<String, Object> beginAtZero = new HashMap<>();
		beginAtZero.put("beginAtZero", true);
		beginAtZero.put("min", 0);
		
		
		Map<String, Object> ticks = new HashMap<>();
		ticks.put("ticks", beginAtZero);
		
		Map<String, Object> xticks = new HashMap<>();
		xticks.put("offset", true);
		//xticks.put("stacked", true);
		xticks.put("ticks", beginAtZero);
		Map<String, List<Object>> yAxes = new HashMap<>();
		yAxes.put("yAxes", Arrays.asList(ticks));
		yAxes.put("xAxes", Arrays.asList(xticks));
		Map<String, Object> options = new HashMap<>();
		options.put("scales", yAxes);
		return options;
	}
	
	
	
	
	
}
