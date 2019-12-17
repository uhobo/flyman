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
		
		
		return new HashMap<>();
	}
	
	
}
