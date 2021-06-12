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
	
	private boolean isStack = false;

	public boolean isStack() {
		return isStack;
	}



	public void setStack(boolean isStack) {
		this.isStack = isStack;
	}



	@JsonIgnore
	public Map<String, Object> getOptions(){
		
		Map<String, Object> beginAtZero = new HashMap<>();
		beginAtZero.put("beginAtZero", true);
		beginAtZero.put("min", 0);
		
		
		Map<String, Object> ticks = new HashMap<>();
		ticks.put("ticks", beginAtZero);
		if(isStack) {
			ticks.put("stacked", true);
		}
		
		Map<String, Object> xticks = new HashMap<>();
		xticks.put("offset", true);
		//xticks.put("stacked", true);
		xticks.put("ticks", beginAtZero);
		if(isStack) {
			xticks.put("stacked", true);
		}
		
		
		Map<String, List<Object>> yAxes = new HashMap<>();
		yAxes.put("yAxes", Arrays.asList(ticks));
		yAxes.put("xAxes", Arrays.asList(xticks));
		
		
		Map<String, Object> options = new HashMap<>();
		
		
		options.put("scales", yAxes);
		
		addToolTipOption(options);
		addPlugin(options);
		
		return options;
	}
	
	

	private void addPlugin(Map<String, Object> options) {
		Map<String, Object> paramsMap = createParam("anchor", "end");
		paramsMap.put("align", "top");
		//paramsMap.put("color", "white");
		paramsMap.put("labels", createParam("title", createParam("color", "blue")));
		options.put ("plugins", createParam("datalabels", paramsMap)); 
		
	}

	private void addToolTipOption(Map<String, Object> options) {
		options.put("tooltips", createParam("intersect", false));
		
	}
	
	
	
	
	
}
